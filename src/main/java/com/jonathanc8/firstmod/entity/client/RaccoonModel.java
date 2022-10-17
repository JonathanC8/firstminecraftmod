package com.jonathanc8.firstmod.entity.client;

import com.jonathanc8.firstmod.entity.custom.RaccoonEntity;
import com.jonathanc8.firstmod.firstmod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RaccoonModel extends AnimatedGeoModel<RaccoonEntity> {

    @Override
    public ResourceLocation getModelLocation(RaccoonEntity object) {
        return new ResourceLocation(firstmod.MOD_ID, "geo/raccoon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RaccoonEntity object) {
        return new ResourceLocation(firstmod.MOD_ID, "textures/entity/raccoon/raccoon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RaccoonEntity animatable) {
        return new ResourceLocation(firstmod.MOD_ID, "animations/raccoon/raccoon.animation.json");
    }
}
