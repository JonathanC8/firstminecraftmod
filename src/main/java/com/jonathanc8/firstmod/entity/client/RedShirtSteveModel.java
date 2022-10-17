package com.jonathanc8.firstmod.entity.client;


import com.jonathanc8.firstmod.entity.custom.RedShirtSteveEntity;
import com.jonathanc8.firstmod.firstmod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RedShirtSteveModel extends AnimatedGeoModel<RedShirtSteveEntity>{
    @Override
    public ResourceLocation getModelLocation(RedShirtSteveEntity object) {
        return new ResourceLocation(firstmod.MOD_ID, "geo/redshirtsteve.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RedShirtSteveEntity object) {
        return new ResourceLocation(firstmod.MOD_ID, "textures/entity/redshirtsteve/redshirtsteve.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RedShirtSteveEntity animatable) {
        return new ResourceLocation(firstmod.MOD_ID, "animations/redshirtsteve/redshirtsteve.animation.json");
    }
}
