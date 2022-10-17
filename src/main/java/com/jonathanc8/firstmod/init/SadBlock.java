package com.jonathanc8.firstmod.init;

import com.jonathanc8.firstmod.firstmod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Random;

public class SadBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public SadBlock(BlockBehaviour.Properties properties){
        super (properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        ItemStack held = player.getItemInHand(hand);

        if(!world.isClientSide && held.getItem() == Items.GUNPOWDER){
            world.explode(player, pos.getX(), pos.getY(), pos.getZ(), 4.0f, true, Explosion.BlockInteraction.DESTROY);
            held.shrink(1);
            return InteractionResult.CONSUME;
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion explosion){
        world.explode(null, pos.getX(), pos.getY(), pos.getZ(), 4.0f, true, Explosion.BlockInteraction.DESTROY);
        super.wasExploded(world, pos, explosion);
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState blockState, BlockEntity blockEntity, ItemStack itemStack){
        if(!world.isClientSide){
            world.explode(player, pos.getX(), pos.getY(), pos.getZ(), 4.0f, true, Explosion.BlockInteraction.DESTROY);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state){
        return true;
    }


    // it can sustain cactus lol
    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable){
        Block plant = plantable.getPlant(world,pos.relative(facing)).getBlock();
        if(plant == Blocks.CACTUS){
            return true;
        }else{
            return super.canSustainPlant(state, world, pos, facing, plantable);
        }
    }

    public static Logger logger = LogManager.getLogger(firstmod.MOD_ID);
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand){
            BlockState north = world.getBlockState(pos.north());
            BlockState south = world.getBlockState(pos.south());
            BlockState east = world.getBlockState(pos.east());
            BlockState west = world.getBlockState(pos.west());
            BlockState down = world.getBlockState(pos.below());
            BlockState up = world.getBlockState(pos.above());
            if(!north.isAir()){
                world.setBlockAndUpdate(pos.north(), Blocks.DIRT.defaultBlockState());
            }
            if(!east.isAir()){
                world.setBlockAndUpdate(pos.east(), Blocks.DIRT.defaultBlockState());
            }
            if(!west.isAir()){
                world.setBlockAndUpdate(pos.west(), Blocks.DIRT.defaultBlockState());
            }
            if(!south.isAir()){
                world.setBlockAndUpdate(pos.south(), Blocks.DIRT.defaultBlockState());
            }
            if(!down.isAir()){
                world.setBlockAndUpdate(pos.below(), Blocks.DIRT.defaultBlockState());
            }
            if(!up.isAir()){
                world.setBlockAndUpdate(pos.above(), Blocks.DIRT.defaultBlockState());
            }

            world.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
    }

}
