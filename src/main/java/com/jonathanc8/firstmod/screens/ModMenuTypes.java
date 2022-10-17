package com.jonathanc8.firstmod.screens;

import com.jonathanc8.firstmod.firstmod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, firstmod.MOD_ID);

    public static final RegistryObject<MenuType<GemCutterMenu>> GEM_CUTTER_MENU = registerMenuType(GemCutterMenu::new, "gem_cutter_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENUS.register(name, ()-> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus ){
        MENUS.register(eventBus);
    }
}
