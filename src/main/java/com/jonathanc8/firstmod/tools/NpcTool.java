package com.jonathanc8.firstmod.tools;

import com.jonathanc8.firstmod.firstmod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class NpcTool extends HoeItem {

    public BlockPos blockClicked;
    public CompoundTag compoundTag;
    public static Logger logger = LogManager.getLogger(firstmod.MOD_ID);

    public NpcTool(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);

    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(this.blockClicked != BlockPos.ZERO){
            this.blockClicked = pContext.getClickedPos();
        }
        CompoundTag tag = pContext.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).getTag();
        tag.putDouble("firstmod.blockX", blockClicked.getX());
        tag.putDouble("firstmod.blockY", blockClicked.getY());
        tag.putDouble("firstmod.blockZ", blockClicked.getZ());
        return InteractionResult.PASS;
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag compoundTag){
        this.compoundTag = compoundTag;
        compoundTag.putString("firstmod.tool_id", "NPC_TOOL");
    }

    public BlockPos getBlockClicked(){
        return this.blockClicked;
    }
}
