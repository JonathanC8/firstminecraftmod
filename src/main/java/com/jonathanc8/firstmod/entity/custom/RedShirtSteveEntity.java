package com.jonathanc8.firstmod.entity.custom;

import com.jonathanc8.firstmod.entity.ai.RedShirtSteveAi;
import com.jonathanc8.firstmod.firstmod;
import com.jonathanc8.firstmod.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.C;
import org.jline.utils.DiffHelper;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RedShirtSteveEntity extends PathfinderMob implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private final ItemStackHandler steveInventory= new ItemStackHandler(1);
    private static final ItemStack defaultHeldItem = new ItemStack(Items.AIR, 1);
    public CompoundTag tag;

    public static BlockPos toolPos = BlockPos.ZERO;
    public RedShirtSteveEntity(EntityType<? extends PathfinderMob> entityType, Level level) {

        super(entityType, level);
        steveInventory.insertItem(0,defaultHeldItem, false);
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
    }



    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    protected void registerGoals() {

        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        if(this.tag != null){
            this.goalSelector.addGoal(0, new RedShirtSteveAi(this, 1.0D,10, tag));
        }

        //this.goalSelector.addGoal(1, new FloatGoal(this));
        //this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
        //this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        //this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        //this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        //this.targetSelector.addGoal(7, (new HurtByTargetGoal(this)).setAlertOthers());
        //this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));

    }

    protected void registerTargets(){
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> {
            return p_28879_ instanceof Enemy;
        }));
    }

    public static Logger logger = LogManager.getLogger(firstmod.MOD_ID);
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand){
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        Item item = itemStack.getItem();
        if(item == ItemInit.NPC_TOOL.get() && itemStack.hasTag()){
            if(!itemStack.getTag().getString("firstmod.tool_id").equals("NPC_TOOL")){
                itemStack.setTag(new CompoundTag());
            }
            this.tag = itemStack.getTag();
            tag.putString("firstmod.tool_id", "NPC_TOOL");
            logger.info(tag.getDouble("blockX"));
            logger.info(tag.getDouble("blockY"));
            logger.info(tag.getDouble("blockZ"));
            registerGoals();
            registerTargets();
        } else if(this.level.isClientSide && item != Items.AIR){
            steveInventory.insertItem(0,itemStack , false);
            itemStack.shrink(1);
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            logger.info("Steve has: "+ steveInventory.getStackInSlot(0));
            logger.info(this.getMainHandItem());
            return InteractionResult.PASS;
        }
        return super.mobInteract(pPlayer, pHand);
    }



    public CompoundTag newTag(){
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.redshirtsteve.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.redshirtsteve.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller2",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_VILLAGER_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }


}
