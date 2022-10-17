package com.jonathanc8.firstmod;

import com.jonathanc8.firstmod.entity.ModEntityTypes;
import com.jonathanc8.firstmod.entity.client.RaccoonRenderer;
import com.jonathanc8.firstmod.entity.client.RedShirtSteveRenderer;
import com.jonathanc8.firstmod.init.BlockInit;
import com.jonathanc8.firstmod.init.ItemInit;
import com.jonathanc8.firstmod.init.TileEntityInit;
import com.jonathanc8.firstmod.screens.GemCuttingScreen;
import com.jonathanc8.firstmod.screens.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod("firstmod")
public class firstmod {
    public static final String MOD_ID = "firstmod";

    public static final CreativeModeTab FIRST_TAB = new CreativeModeTab(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.EXAMPLE_ITEM.get());
        }
    };

    public firstmod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        TileEntityInit.TILE_ENTITY_TYPES.register(bus);
        ModMenuTypes.MENUS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);

        ModEntityTypes.register(bus);

        GeckoLib.initialize();

        bus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event){
        MenuScreens.register(ModMenuTypes.GEM_CUTTER_MENU.get(), GemCuttingScreen::new);

        EntityRenderers.register(ModEntityTypes.RACCOON.get(), RaccoonRenderer::new);
        EntityRenderers.register(ModEntityTypes.RED_SHIRT_STEVE.get(), RedShirtSteveRenderer::new);
    }
}
