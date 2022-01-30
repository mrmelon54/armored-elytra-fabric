package xyz.mrmelon54.armoredelytra;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class InternalArrays {
    public static List<Item> CHESTPLATES = new ArrayList<>(List.of(
            Items.NETHERITE_CHESTPLATE,
            Items.DIAMOND_CHESTPLATE,
            Items.GOLDEN_CHESTPLATE,
            Items.IRON_CHESTPLATE,
            Items.CHAINMAIL_CHESTPLATE,
            Items.LEATHER_CHESTPLATE
    ));

    public static float chestplateToArmoredElytraId(Item chestplate) {
        return CHESTPLATES.indexOf(chestplate);
    }

    public static boolean isItemChestplate(Item chestplate) {
        return CHESTPLATES.contains(chestplate);
    }
}
