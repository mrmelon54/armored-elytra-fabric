package net.onpointcoding.armoredelytra.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.onpointcoding.armoredelytra.ArmoredElytra;
import net.onpointcoding.armoredelytra.ChestplateWithElytraItem;
import net.onpointcoding.armoredelytra.InternalArrays;

public class TheIllusiveC4ColytraItem implements ChestplateWithElytraItem {
    public final ItemStack stack;
    public boolean isValid;
    public Item ChestplateType;

    public TheIllusiveC4ColytraItem(ItemStack stack) {
        this.stack = stack;
        this.isValid = isArmoredElytra();
    }

    public void setDisplayChestplateTick(boolean v) {
        // We don't actually need this
    }

    public boolean getDisplayChestplateTick() {
        // This isn't needed so just return false
        return false;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public boolean getStatus() {
        return isValid;
    }

    @Override
    public Item getChestplateType() {
        return ChestplateType;
    }

    public static TheIllusiveC4ColytraItem fromItemStack(ItemStack stack) {
        TheIllusiveC4ColytraItem item = new TheIllusiveC4ColytraItem(stack);
        return item.isValid ? item : null;
    }

    public boolean equals(ChestplateWithElytraItem b) {
        if (b == null) return false;
        if (b instanceof VanillaTweaksArmoredElytraItem) return stack == ((VanillaTweaksArmoredElytraItem) b).stack;
        return false;
    }

    public boolean isArmoredElytra() {
        if (!stack.isEmpty()) {
            if (InternalArrays.isItemChestplate(stack.getItem())) {
                CompoundTag elytra = getElytra();
                if (elytra != null) {
                    ChestplateType = stack.getItem();
                    return ChestplateType != Items.AIR;
                }
            }
        }
        return false;
    }

    public int getLeatherChestplateColor() {
        CompoundTag leatherChestplate = getChestplate();
        if (ItemStack.fromTag(leatherChestplate).getItem() != Items.LEATHER_CHESTPLATE) return -1;
        if (leatherChestplate == null) return -1;
        CompoundTag tagdata = leatherChestplate.getCompound("tag");
        if (tagdata == null) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        CompoundTag displaydata = tagdata.getCompound("display");
        if (displaydata == null) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        if (!displaydata.contains("color")) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        return displaydata.getInt("color");
    }

    public CompoundTag getElytra() {
        return stack.getSubTag("colytra:ElytraUpgrade");
    }

    public CompoundTag getArmoredElytraData() {
        if (!stack.isEmpty()) return stack.toTag(new CompoundTag());
        return null;
    }

    public CompoundTag getChestplate() {
        return stack.toTag(new CompoundTag());
    }

    public ItemStack getChestplateItemStack() {
        return stack;
    }
}
