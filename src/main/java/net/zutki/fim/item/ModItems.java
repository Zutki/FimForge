package net.zutki.fim.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zutki.fim.Fim;
import net.zutki.fim.item.custom.InfiniteWaterBucket;
import net.zutki.fim.item.custom.ModHoeItem;

public class ModItems {

    // WHEAT
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Fim.MOD_ID);

    public static final RegistryObject<Item>[] hoes = new RegistryObject[12];

    public static final RegistryObject<Item> omnihoe = ITEMS.register("omnihoe",
            () -> new ModHoeItem(Tiers.NETHERITE, 1, 1,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).rarity(Rarity.EPIC), 5));

    public static final RegistryObject<Item> INFINITE_WATER_BUCKET = ITEMS.register("infinite_water_bucket",
            () -> new InfiniteWaterBucket(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus) {
        String[] targetCrops = {
                "Wheat Crops",
                "Beetroots",
                "Carrots",
                "Potatoes"
        };
        int[] ranges = {1, 3, 5};
        int i = 0;
        for (String target : targetCrops) {
            for (int range : ranges) {
                String hoeName = "";
                switch (target) {
                    case "Wheat Crops":
                        hoeName = "wheat_hoe";
                        break;
                    case "Beetroots":
                        hoeName = "beetroot_hoe";
                        break;
                    case "Carrots":
                        hoeName = "carrot_hoe";
                        break;
                    case "Potatoes":
                        hoeName = "potato_hoe";
                        break;
                }

                switch (range) {
                    case 1:
                         hoes[i] = ITEMS.register(hoeName,
                                 () -> new ModHoeItem(Tiers.DIAMOND, 1, 1,
                                         new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).rarity(Rarity.UNCOMMON), 1, target));
                         break;
                    case 3:
                        hoeName = "super_"+hoeName;
                        hoes[i] = ITEMS.register(hoeName,
                                () -> new ModHoeItem(Tiers.DIAMOND, 1, 1,
                                        new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).rarity(Rarity.RARE), 3, target));
                        break;
                    case 5:
                        hoeName = "supreme_"+hoeName;
                        hoes[i] = ITEMS.register(hoeName,
                                () -> new ModHoeItem(Tiers.DIAMOND, 1, 1,
                                        new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).rarity(Rarity.EPIC), 5, target));
                        break;
                }
                i++;
            }
        }

        ITEMS.register(eventBus);
    }
}
