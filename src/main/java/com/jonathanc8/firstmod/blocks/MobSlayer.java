package com.jonathanc8.firstmod.blocks;

import com.jonathanc8.firstmod.firstmod;
import com.jonathanc8.firstmod.blocks.entity.MobSlayerTile;
import com.jonathanc8.firstmod.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public class MobSlayer extends Block implements EntityBlock {

    public static Logger logger = LogManager.getLogger(firstmod.MOD_ID);
    public MobSlayer(BlockBehaviour.Properties props){
        super(props);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
        return TileEntityInit.MOB_SLAYER.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity>BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type){
        return type == TileEntityInit.MOB_SLAYER.get() ? MobSlayerTile::tick:null;
    }



    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        BlockEntity tile = world.getBlockEntity(pos);
        if(!world.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            if (tile instanceof MobSlayerTile) {
                ((MobSlayerTile) tile).toggle();
                ((MobSlayerTile) tile).clicker();
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

}
