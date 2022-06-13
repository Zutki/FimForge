package net.zutki.fim.item.custom;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ModHoeItem extends HoeItem {
    private int harvestRange;
    private boolean skipCheck = false;
    private String targetCrop = "";

    public ModHoeItem(Tier tier, int attackDamageBaseline, float attackSpeed, Item.Properties itemProperties, int harvestRange, String targetCrop) {
        super(tier, attackDamageBaseline, attackSpeed, itemProperties);
        this.harvestRange = harvestRange;
        this.targetCrop = targetCrop;
    }
    public ModHoeItem(Tier tier, int attackDamageBaseline, float attackSpeed, Item.Properties itemProperties, int harvestRange) {
        super(tier, attackDamageBaseline, attackSpeed, itemProperties);
        this.harvestRange = harvestRange;
        this.skipCheck = true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
        if (hook != 0) return hook > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, net.minecraftforge.common.ToolActions.HOE_TILL, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));

        if (!level.isClientSide) {
            BlockPos harvestBlockPos = new BlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ()); // instance it because java be funky at times

            for (int z = -harvestRange; z < harvestRange + 1; z++) {
                for (int x = -harvestRange; x < harvestRange + 1; x++) {
                    BlockState block = level.getBlockState(harvestBlockPos.offset(x, 0, z));
                    if (block.getBlock() instanceof CropBlock cblock &&
                            (cblock.getName().getString().equals(targetCrop) || skipCheck) &&
                            cblock.isMaxAge(block)) {
                        level.destroyBlock(harvestBlockPos.offset(x, 0, z), true, context.getPlayer());
                        // I have no clue what to put for the tag :p
                        level.setBlock(harvestBlockPos.offset(x, 0, z), cblock.defaultBlockState(), 0);
                    }
                }
            }
        }

        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();
            if (predicate.test(context)) {
                Player player = context.getPlayer();
                level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    consumer.accept(context);
                    if (player != null) {
                        context.getItemInHand().hurtAndBreak(1, player, (p_150845_) -> {
                            p_150845_.broadcastBreakEvent(context.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }
}
