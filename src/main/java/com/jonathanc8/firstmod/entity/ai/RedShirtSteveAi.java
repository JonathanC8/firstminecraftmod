package com.jonathanc8.firstmod.entity.ai;

import com.jonathanc8.firstmod.entity.custom.RedShirtSteveEntity;
import com.jonathanc8.firstmod.firstmod;
import com.jonathanc8.firstmod.tools.NpcTool;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.level.LevelReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.CallbackI;

public class RedShirtSteveAi extends Goal {

    private BlockPos blockPos;
    private CompoundTag tag;
    private boolean reachedTarget;
    private PathfinderMob mob;
    public final double speedModifier;
    private final int searchRange;
    private boolean  newOrders= false;
    private boolean holdFire = false;
    private boolean isReachedTarget = false;

    public RedShirtSteveAi(PathfinderMob pMob, double pSpeedModifier, int pSearchRange, CompoundTag tag) {
        this.speedModifier = pSpeedModifier;
        this.searchRange = pSearchRange;
        this.tag = tag;
        this.mob = pMob;
        this.blockPos = BlockPos.ZERO;
    }
    public static Logger logger = LogManager.getLogger(firstmod.MOD_ID);

    @Override
    public boolean canUse() {
        if(holdFire){
            return true;
        }else{
            if(tag != null) {
                BlockPos tempPos = new BlockPos(tag.getDouble("blockX"), tag.getDouble("blockY"), tag.getDouble("blockZ")).above();
                if((blockPos.getX() != tempPos.getX() || blockPos.getY() != tempPos.getY() || blockPos.getZ() != tempPos.getZ())){
                    this.isReachedTarget = false;
                    return true;
                } else {
                    if (this.mob.getTarget() != null) {
                        if (this.mob.getTarget().isAlive() && isReachedTarget) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            } else{
                return false;
            }
        }
    }

    private double acceptedDistance() {
        return 1.0D;
    }

    @Override
    public boolean canContinueToUse() {
        if(this.mob.getTarget() != null){
            if(this.mob.getTarget().isAlive() && isReachedTarget){
                return false;
            } else{
                return true;
            }
        }else{
            return true;
        }
    }

    @Override
    public void start(){
        if(tag != null){
            this.blockPos = new BlockPos(tag.getDouble("blockX"), tag.getDouble("blockY"), tag.getDouble("blockZ")).above();
            this.moveMobToBlock();
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick(){
        if(tag != null){
            BlockPos tempPos = new BlockPos(tag.getDouble("blockX"), tag.getDouble("blockY"), tag.getDouble("blockZ")).above();
            if (blockPos.getX() != tempPos.getX() || blockPos.getY() != tempPos.getY() || blockPos.getZ() != tempPos.getZ()) {
                logger.info("New Position: "+tempPos);
                logger.info("Old Position"+ blockPos);
                this.blockPos = tempPos;
                this.moveMobToBlock();
            }
        }
        if(!blockPos.closerToCenterThan(this.mob.position(), this.acceptedDistance())){
            this.mob.getNavigation().moveTo((double)((float)blockPos.getX()) + 0.5D, (double)blockPos.getY(), (double)((float)blockPos.getZ()) + 0.5D, this.speedModifier);
        } else{
            this.isReachedTarget = true;
        }

    }

    protected void moveMobToBlock() {
        this.mob.getNavigation().moveTo(((float)this.blockPos.getX()) , (this.blockPos.getY()), ((float)this.blockPos.getZ()), this.speedModifier);
    }

}
