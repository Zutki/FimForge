package net.zutki.fim.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ModFarmlandBlock extends FarmBlock {
    public ModFarmlandBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float float1) {
        entity.causeFallDamage(float1, 1.0F, DamageSource.FALL);
    }
}
