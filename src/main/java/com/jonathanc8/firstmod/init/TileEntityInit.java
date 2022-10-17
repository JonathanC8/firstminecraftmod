package com.jonathanc8.firstmod.init;

import com.jonathanc8.firstmod.blocks.entity.MobSlayerTile;
import com.jonathanc8.firstmod.firstmod;
import com.jonathanc8.firstmod.blocks.entity.GemCutterEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, firstmod.MOD_ID);

    public static final RegistryObject<BlockEntityType<MobSlayerTile>> MOB_SLAYER = TILE_ENTITY_TYPES.register("mob_slayer",
            ()-> BlockEntityType.Builder.of(MobSlayerTile::new, BlockInit.MOB_SLAYER.get()).build(null));

    public static final RegistryObject<BlockEntityType<GemCutterEntity>> GEM_CUTTER = TILE_ENTITY_TYPES.register("gem_cutter",
            ()-> BlockEntityType.Builder.of(GemCutterEntity::new, BlockInit.GEM_CUTTER.get()).build(null));

}
