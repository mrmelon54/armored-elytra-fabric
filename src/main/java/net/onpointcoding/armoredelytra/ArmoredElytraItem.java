package net.onpointcoding.armoredelytra;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;

public class ArmoredElytraItem {
    public final ItemStack stack;
    public boolean isValid;
    public Item ChestplateType;

    public ArmoredElytraItem(ItemStack stack) {
        this.stack = stack;
        this.isValid = isArmoredElytra();
    }

    public static ArmoredElytraItem fromItemStack(ItemStack stack) {
        ArmoredElytraItem item = new ArmoredElytraItem(stack);
        return item.isValid ? item : null;
    }

    public boolean equals(ArmoredElytraItem b) {
        if (b == null) return false;
        return stack == b.stack;
    }

    boolean isArmoredElytra() {
        if (!stack.isEmpty()) {
            if (stack.getItem() == Items.ELYTRA) {
                CompoundTag chestplate = getChestplate();
                CompoundTag elytra = getElytra();
                if (chestplate != null && elytra != null) {
                    ItemStack chestplateStack = ItemStack.fromTag(chestplate);
                    ChestplateType = chestplateStack.getItem();
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
        if (tagdata == null) return -1;
        CompoundTag displaydata = tagdata.getCompound("display");
        if (displaydata == null) return -1;
        if (!displaydata.contains("color")) return -1;
        return displaydata.getInt("color");
    }

    CompoundTag getElytra() {
        CompoundTag armelydata = getArmoredElytraData();
        if (armelydata != null) {
            return armelydata.getCompound("elytra");
        }
        return null;
    }

    CompoundTag getChestplate() {
        CompoundTag armelydata = getArmoredElytraData();
        if (armelydata != null) {
            return armelydata.getCompound("chestplate");
        }
        return null;
    }

    CompoundTag getArmoredElytraData() {
        if (!stack.isEmpty() && stack.getItem() == Items.ELYTRA) return stack.getSubTag("armElyData");
        return null;
    }
}
