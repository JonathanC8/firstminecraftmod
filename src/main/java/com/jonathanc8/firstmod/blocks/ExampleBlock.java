package com.jonathanc8.firstmod.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class ExampleBlock extends Block {
    public ExampleBlock(){
        super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops());
    }
}
