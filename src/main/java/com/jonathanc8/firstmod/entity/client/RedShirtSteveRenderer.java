package com.jonathanc8.firstmod.entity.client;

import com.jonathanc8.firstmod.entity.custom.RaccoonEntity;
import com.jonathanc8.firstmod.entity.custom.RedShirtSteveEntity;
import com.jonathanc8.firstmod.firstmod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.example.client.DefaultBipedBoneIdents;
import software.bernie.example.entity.ExtendedRendererEntity;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RedShirtSteveRenderer extends ExtendedGeoEntityRenderer<RedShirtSteveEntity> {

    public RedShirtSteveRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RedShirtSteveModel());
        this.shadowRadius = 0.3f;
    }


    @Override
    public ResourceLocation getTextureLocation(RedShirtSteveEntity entity) {
        return new ResourceLocation(firstmod.MOD_ID, "textures/entity/redshirtsteve/redshirtsteve.png");
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return bone.getName().startsWith("armor");
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, RedShirtSteveEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, RedShirtSteveEntity currentEntity) {
        switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT:
                return currentEntity.isLeftHanded() ? mainHand : offHand;
            case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT:
                return currentEntity.isLeftHanded() ? offHand : mainHand;
            case DefaultBipedBoneIdents.POTION_BONE_IDENT:
                break;
        }
        return null;
    }

    @Override
    protected ItemTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT:
                return ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
            case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT:
                return ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
            default:
                return ItemTransforms.TransformType.NONE;
        }
    }

    @Override
    protected ItemStack getArmorForBone(String boneName, RedShirtSteveEntity currentEntity) {
        switch (boneName) {
            case "armorBipedLeftFoot":
            case "armorBipedRightFoot":
                return boots;
            case "armorBipedLeftLeg":
            case "armorBipedRightLeg":
                return leggings;
            case "armorBipedBody":
            case "armorBipedRightArm":
            case "armorBipedLeftArm":
                return chestplate;
            case "armorBipedHead":
                return helmet;
            default:
                return null;
        }
    }

    @Override
    protected EquipmentSlot getEquipmentSlotForArmorBone(String boneName, RedShirtSteveEntity currentEntity) {
        switch (boneName) {
            case "armorBipedLeftFoot":
            case "armorBipedRightFoot":
                return EquipmentSlot.FEET;
            case "armorBipedLeftLeg":
            case "armorBipedRightLeg":
                return EquipmentSlot.LEGS;
            case "armorBipedRightArm":
                return !currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            case "armorBipedLeftArm":
                return currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            case "armorBipedBody":
                return EquipmentSlot.CHEST;
            case "armorBipedHead":
                return EquipmentSlot.HEAD;
            default:
                return null;
        }
    }

    @Override
    protected ModelPart getArmorPartForBone(String name, HumanoidModel<?> armorModel) {
        switch (name) {
            case "armorBipedLeftFoot":
            case "armorBipedLeftLeg":
                return armorModel.leftLeg;
            case "armorBipedRightFoot":
            case "armorBipedRightLeg":
                return armorModel.rightLeg;
            case "armorBipedRightArm":
                return armorModel.rightArm;
            case "armorBipedLeftArm":
                return armorModel.leftArm;
            case "armorBipedBody":
                return armorModel.body;
            case "armorBipedHead":
                return armorModel.head;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, RedShirtSteveEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String boneName, RedShirtSteveEntity currentEntity, IBone bone) {
        if (item == this.mainHand || item == this.offHand) {
            stack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            boolean shieldFlag = item.getItem() instanceof ShieldItem;
            if (item == this.mainHand) {
                if (shieldFlag) {
                    stack.translate(0.0, 0.125, -0.25);
                } else {

                }
            } else {
                if (shieldFlag) {
                    stack.translate(0, 0.125, 0.25);
                    stack.mulPose(Vector3f.YP.rotationDegrees(180));
                } else {

                }

            }
            // stack.mulPose(Vector3f.YP.rotationDegrees(180));

            // stack.scale(0.75F, 0.75F, 0.75F);
        }
    }

    @Override
    protected void preRenderBlock(PoseStack matrixStack, BlockState block, String boneName, RedShirtSteveEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(PoseStack matrixStack, ItemStack item, String boneName, RedShirtSteveEntity currentEntity, IBone bone) {

    }

    @Override
    protected void postRenderBlock(PoseStack matrixStack, BlockState block, String boneName, RedShirtSteveEntity currentEntity) {

    }

    @Override
    public RenderType getRenderType(RedShirtSteveEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.0F, 1.0F, 1.0F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
