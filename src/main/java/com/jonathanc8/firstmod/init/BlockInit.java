package com.jonathanc8.firstmod.init;
import com.jonathanc8.firstmod.blocks.GemCutter;
import com.jonathanc8.firstmod.firstmod;
import com.jonathanc8.firstmod.blocks.ExampleBlock;
import com.jonathanc8.firstmod.blocks.MobSlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, firstmod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;

    public static final RegistryObject<Block> SMILE_BLOCK = BLOCKS.register("smile_block",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).requiresCorrectToolForDrops().lightLevel((state) -> 15)));
    public static final RegistryObject<Block> SAD_BLOCK = BLOCKS.register("sad_block",
            () -> new SadBlock(Block.Properties.copy(Blocks.SAND)));

    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block",
            ()-> new ExampleBlock());
    public static final RegistryObject<Block> GEM_CUTTER = BLOCKS.register("gem_cutter",
            ()-> new GemCutter(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> MOB_SLAYER = BLOCKS.register("mob_slayer",
            () -> new MobSlayer(Block.Properties.copy(Blocks.IRON_BLOCK)));

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();

        BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) ->{
            final Item.Properties properties = new Item.Properties().tab(firstmod.FIRST_TAB);
            final BlockItem blockItem= new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }

}
