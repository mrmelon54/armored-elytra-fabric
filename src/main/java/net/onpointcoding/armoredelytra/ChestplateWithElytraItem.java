package net.onpointcoding.armoredelytra;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.onpointcoding.armoredelytra.items.VanillaTweaksArmoredElytraItem;
import net.onpointcoding.armoredelytra.items.VoodooTweaksPlatedElytraItem;

public interface ChestplateWithElytraItem {
    void setDisplayChestplateTick(boolean v);

    boolean getDisplayChestplateTick();

    ItemStack getItemStack();

    boolean getStatus();

    Item getChestplateType();

    static ChestplateWithElytraItem fromItemStack(ItemStack stack) {
        VanillaTweaksArmoredElytraItem vtae = VanillaTweaksArmoredElytraItem.fromItemStack(stack);
        if (vtae != null)
            if (vtae.isValid) return vtae;
        VoodooTweaksPlatedElytraItem vtpe = VoodooTweaksPlatedElytraItem.fromItemStack(stack);
        if (vtpe != null)
            if (vtpe.isValid) return vtpe;
        return null;
    }

    boolean equals(ChestplateWithElytraItem b);

    boolean isArmoredElytra();

    int getLeatherChestplateColor();

    CompoundTag getElytra();

    CompoundTag getChestplate();

    CompoundTag getArmoredElytraData();

    ItemStack getChestplateItemStack();
}
