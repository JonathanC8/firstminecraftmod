package com.jonathanc8.firstmod.event;


import com.jonathanc8.firstmod.entity.ModEntityTypes;
import com.jonathanc8.firstmod.entity.custom.RaccoonEntity;
import com.jonathanc8.firstmod.entity.custom.RedShirtSteveEntity;
import com.jonathanc8.firstmod.firstmod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = firstmod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.RACCOON.get(), RaccoonEntity.setAttributes());
        event.put(ModEntityTypes.RED_SHIRT_STEVE.get(), RedShirtSteveEntity.setAttributes());
    }


}