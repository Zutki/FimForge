package net.zutki.fim.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zutki.fim.Fim;
import net.zutki.fim.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Fim.MOD_ID);
    // IDC that this is NOT how you should do this, but, I hate working with Forge and this has been a pain in the backside
    public static final DeferredRegister<Block> MC_BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    /*public static final RegistryObject<Block> farmland = registerMCBlock("farmland",
            () -> new ModFarmlandBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(0.6f).sound(SoundType.GRAVEL)), CreativeModeTab.TAB_BUILDING_BLOCKS);*/

    public static final RegistryObject<Block> FARMLAND = MC_BLOCKS.register("farmland",
            () -> new ModFarmlandBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(0.6f).sound(SoundType.GRAVEL)));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    private static <T extends Block>RegistryObject<T> registerMCBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        return MC_BLOCKS.register(name, block);
    }

    public static void register(IEventBus eventBus) {
        registerBlock("compressed_hay", () -> new HayBlock(BlockBehaviour.Properties.of(Material.GRASS).strength(0.5f).sound(SoundType.GRASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
        String[] barrelTypes = {
                "carrots",
                "potatoes",
                "beetroots"
        };

        for (String type : barrelTypes) {
            for (int c = 0; c < 2; c++) {
                String name = "barrel_of_"+type;
                name = c == 0 ? name : "compressed_"+name;
                registerBlock(name, () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(0.5f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
            }
        }

        BLOCKS.register(eventBus);
        MC_BLOCKS.register(eventBus);
    }
}
