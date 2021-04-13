package net.onpointcoding.armoredelytra;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Arrays;

public class InternalArrays {
    public static final Item[] CHESTPLATES = new Item[]{
            Items.NETHERITE_CHESTPLATE,
            Items.DIAMOND_CHESTPLATE,
            Items.GOLDEN_CHESTPLATE,
            Items.IRON_CHESTPLATE,
            Items.CHAINMAIL_CHESTPLATE,
            Items.LEATHER_CHESTPLATE
    };

    public static float chestplateToArmoredElytraId(Item chestplate) {
        return Arrays.asList(CHESTPLATES).indexOf(chestplate);
    }

    public static boolean isItemChestplate(Item chestplate) {
        return Arrays.asList(CHESTPLATES).contains(chestplate);
    }
}
