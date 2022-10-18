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
    private CompoundTag entityTag;
    private boolean reachedTarget;
    private PathfinderMob mob;
    public final double speedModifier;
    private final int searchRange;
    private boolean  newOrders= false;
    private boolean holdFire = false;
    private boolean isReachedTarget = false;
    private double accuracy;
    private double x;
    private double y;
    private double z;

    public RedShirtSteveAi(PathfinderMob pMob, double pSpeedModifier, int pSearchRange, CompoundTag entityTag) {
        this.speedModifier = pSpeedModifier;
        this.searchRange = pSearchRange;
        this.entityTag = entityTag;
        this.mob = pMob;
        this.blockPos = BlockPos.ZERO;
        this.accuracy = 0.75;
    }
    public static Logger logger = LogManager.getLogger(firstmod.MOD_ID);

    @Override
    public boolean canUse() {
        if(entityTag != null){
            this.tag = entityTag.getCompound("firstmod.playerTool");
        }
        if(holdFire){
            return true;
        }else{
            if(tag != null) {
                BlockPos tempPos = new BlockPos(tag.getDouble("firstmod.blockX"), tag.getDouble("firstmod.blockY"), tag.getDouble("firstmod.blockZ")).above();
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
        return 0.75D;
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
            this.blockPos = new BlockPos(tag.getDouble("firstmod.blockX"), tag.getDouble("firstmod.blockY"), tag.getDouble("firstmod.blockZ")).above();
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
            BlockPos tempPos = new BlockPos(tag.getDouble("firstmod.blockX"), tag.getDouble("firstmod.blockY"), tag.getDouble("firstmod.blockZ")).above();
            if (blockPos.getX() != tempPos.getX() || blockPos.getY() != tempPos.getY() || blockPos.getZ() != tempPos.getZ()) {
                logger.info("New Position: "+tempPos);
                logger.info("Old Position"+ blockPos);
                this.blockPos = tempPos;
                this.moveMobToBlock();
            }
            x = this.blockPos.getX();
            y = this.blockPos.getY();
            z = this.blockPos.getZ();
        }
        if(!blockPos.closerToCenterThan(this.mob.position(), this.acceptedDistance()) && !this.mob.isPathFinding()){
            logger.info("Distance not good enough!");
            logger.info("PX: "+x+"PZ: "+z);
            logger.info("MX: "+this.mob.position().x+"MZ"+this.mob.position().z);
            x +=  accuracy*(blockPos.getX() - this.mob.position().x);
            z +=  accuracy*(blockPos.getZ() -this.mob.position().z);
            logger.info("X: "+x+"\n Z: "+z);
            this.mob.getNavigation().moveTo(x, blockPos.getY(), z, this.speedModifier);
        } else{
            this.isReachedTarget = true;
        }

    }

    protected void moveMobToBlock() {
        this.mob.getNavigation().moveTo(((float)this.blockPos.getX()) , (this.blockPos.getY()), ((float)this.blockPos.getZ()), this.speedModifier);
    }

}
