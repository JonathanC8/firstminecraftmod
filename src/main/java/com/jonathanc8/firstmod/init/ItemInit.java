package com.jonathanc8.firstmod.init;

import com.google.common.base.Supplier;

import com.jonathanc8.firstmod.entity.ModEntityTypes;
import com.jonathanc8.firstmod.firstmod;
import com.jonathanc8.firstmod.tools.NpcTool;
import com.jonathanc8.firstmod.utils.ModItemTier;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, firstmod.MOD_ID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = register("example_item", () -> new Item(new Item.Properties().tab(firstmod.FIRST_TAB)));
    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item){
        return ITEMS.register(name, item);
    }

    public static final RegistryObject<Item> RACCOON_SPAWN_EGG = ITEMS.register("raccoon_spawn_egg",
           () -> new ForgeSpawnEggItem(ModEntityTypes.RACCOON,0x948e8d, 0x3b3635,
                    new Item.Properties().tab(firstmod.FIRST_TAB)));


    public static final RegistryObject<Item> RED_SHIRT_STEVE_EGG = ITEMS.register("red_shirt_steve_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.RED_SHIRT_STEVE,0x948e8d, 0x3b3635,
                    new Item.Properties().tab(firstmod.FIRST_TAB)));

    public static final RegistryObject<Item> NPC_TOOL = ITEMS.register("npc_tool",
            () -> new NpcTool(ModItemTier.PINK,0, 1,
                    new Item.Properties().tab(firstmod.FIRST_TAB).stacksTo(1)));
}