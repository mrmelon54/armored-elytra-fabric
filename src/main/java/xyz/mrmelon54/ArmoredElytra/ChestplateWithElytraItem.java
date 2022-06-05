package xyz.mrmelon54.ArmoredElytra;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import xyz.mrmelon54.ArmoredElytra.items.Pim16aap2SpigotArmoredElytraItem;
import xyz.mrmelon54.ArmoredElytra.items.TheIllusiveC4ColytraItem;
import xyz.mrmelon54.ArmoredElytra.items.VanillaTweaksArmoredElytraItem;
import xyz.mrmelon54.ArmoredElytra.items.VoodooTweaksPlatedElytraItem;

public interface ChestplateWithElytraItem {
    void setDisplayChestplateTick(boolean v);

    boolean getDisplayChestplateTick();

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

    int getLeatherChestplateColor();

    NbtCompound getElytra();

    NbtCompound getChestplate();

    NbtCompound getArmoredElytraData();

    ItemStack getChestplateItemStack();
}
