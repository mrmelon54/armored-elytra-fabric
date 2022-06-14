package xyz.mrmelon54.ArmoredElytra;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import xyz.mrmelon54.ArmoredElytra.items.Pim16aap2SpigotArmoredElytraItem;
import xyz.mrmelon54.ArmoredElytra.items.TheIllusiveC4ColytraItem;
import xyz.mrmelon54.ArmoredElytra.items.VanillaTweaksArmoredElytraItem;
import xyz.mrmelon54.ArmoredElytra.items.VoodooTweaksPlatedElytraItem;

public interface ChestplateWithElytraItem {
    ItemStack getItemStack();

    boolean getStatus();

    Item getChestplateType();

    static ChestplateWithElytraItem fromItemStack(ItemStack stack) {
        VanillaTweaksArmoredElytraItem vtae = VanillaTweaksArmoredElytraItem.fromItemStack(stack);
        if (vtae != null && vtae.isValid) return vtae;
        VoodooTweaksPlatedElytraItem vtpe = VoodooTweaksPlatedElytraItem.fromItemStack(stack);
        if (vtpe != null && vtpe.isValid) return vtpe;
        TheIllusiveC4ColytraItem ticc = TheIllusiveC4ColytraItem.fromItemStack(stack);
        if (ticc != null && ticc.isValid) return ticc;
        Pim16aap2SpigotArmoredElytraItem psae = Pim16aap2SpigotArmoredElytraItem.fromItemStack(stack);
        if (psae != null && psae.isValid) return psae;
        return null;
    }

    boolean equals(ChestplateWithElytraItem b);

    boolean hasEnchantmentGlint();

    boolean isArmoredElytra();

    default int getLeatherChestplateColor() {
        NbtCompound leatherChestplate = getChestplate();
        if (ItemStack.fromNbt(leatherChestplate).getItem() != Items.LEATHER_CHESTPLATE) return -1;
        if (leatherChestplate == null) return -1;
        NbtCompound tagdata = leatherChestplate.getCompound("tag");
        if (tagdata == null) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        NbtCompound displaydata = tagdata.getCompound("display");
        if (displaydata == null) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        if (!displaydata.contains("color")) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        return displaydata.getInt("color");
    }

    NbtCompound getElytra();

    NbtCompound getChestplate();

    NbtCompound getArmoredElytraData();

    ItemStack getChestplateItemStack();
}
