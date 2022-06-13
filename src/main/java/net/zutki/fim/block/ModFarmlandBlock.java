package net.zutki.fim.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ModFarmlandBlock extends FarmBlock {
    public ModFarmlandBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public void fallOn(Level p_153227_, BlockState p_153228_, BlockPos p_153229_, Entity p_153230_, float p_153231_) {
        super.fallOn(p_153227_, p_153228_, p_153229_, p_153230_, p_153231_);
    }
}
