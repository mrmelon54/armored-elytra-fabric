package net.onpointcoding.armoredelytra;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.onpointcoding.armoredelytra.items.Pim16aap2SpigotArmoredElytraItem;
import net.onpointcoding.armoredelytra.items.TheIllusiveC4ColytraItem;
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
        TheIllusiveC4ColytraItem ticc = TheIllusiveC4ColytraItem.fromItemStack(stack);
        if (ticc != null)
            if (ticc.isValid) return ticc;
        Pim16aap2SpigotArmoredElytraItem psae = Pim16aap2SpigotArmoredElytraItem.fromItemStack(stack);
        if (psae != null)
            if (psae.isValid) return psae;
        return null;
    }

    boolean equals(ChestplateWithElytraItem b);

    boolean isArmoredElytra();

    int getLeatherChestplateColor();

    NbtCompound getElytra();

    NbtCompound getChestplate();

    NbtCompound getArmoredElytraData();

    ItemStack getChestplateItemStack();
}
