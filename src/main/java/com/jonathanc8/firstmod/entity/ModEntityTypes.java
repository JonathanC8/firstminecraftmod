package com.jonathanc8.firstmod.entity;

import com.jonathanc8.firstmod.entity.custom.RaccoonEntity;
import com.jonathanc8.firstmod.entity.custom.RedShirtSteveEntity;
import com.jonathanc8.firstmod.firstmod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, firstmod.MOD_ID);

    public static final RegistryObject<EntityType<RaccoonEntity>> RACCOON =
            ENTITY_TYPES.register("raccoon",
                    () -> EntityType.Builder.of(RaccoonEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.6f)
                            .build(new ResourceLocation(firstmod.MOD_ID, "raccoon").toString()));

    public static final RegistryObject<EntityType<RedShirtSteveEntity>> RED_SHIRT_STEVE =
            ENTITY_TYPES.register("red_shirt_steve",
                    () -> EntityType.Builder.of(RedShirtSteveEntity::new, MobCategory.AMBIENT)
                            .sized(1.0f, 1.0f)
                            .build(new ResourceLocation(firstmod.MOD_ID, "red_shirt_steve").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
