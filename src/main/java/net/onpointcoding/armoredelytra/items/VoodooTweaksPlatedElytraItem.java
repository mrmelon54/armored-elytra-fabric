package net.onpointcoding.armoredelytra.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.onpointcoding.armoredelytra.ArmoredElytra;
import net.onpointcoding.armoredelytra.ChestplateWithElytraItem;

public class VoodooTweaksPlatedElytraItem implements ChestplateWithElytraItem {
    public final ItemStack stack;
    public boolean isValid;
    public Item ChestplateType;
    public boolean displayChestplateTick = false;

    public VoodooTweaksPlatedElytraItem(ItemStack stack) {
        this.stack = stack;
        this.isValid = isArmoredElytra();
    }

    public void setDisplayChestplateTick(boolean v) {
        displayChestplateTick = v;
    }

    @Override
    public boolean getDisplayChestplateTick() {
        return displayChestplateTick;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    @Override
    public boolean getStatus() {
        return isValid;
    }

    @Override
    public Item getChestplateType() {
        return ChestplateType;
    }

    public static VoodooTweaksPlatedElytraItem fromItemStack(ItemStack stack) {
        VoodooTweaksPlatedElytraItem item = new VoodooTweaksPlatedElytraItem(stack);
        return item.isValid ? item : null;
    }

    public boolean equals(ChestplateWithElytraItem b) {
        if (b == null) return false;
        if (b instanceof VoodooTweaksPlatedElytraItem) return stack == ((VoodooTweaksPlatedElytraItem) b).stack;
        return false;
    }

    public boolean isArmoredElytra() {
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
        if (tagdata == null) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        CompoundTag displaydata = tagdata.getCompound("display");
        if (displaydata == null) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        if (!displaydata.contains("color")) return ArmoredElytra.DEFAULT_LEATHER_COLOR;
        return displaydata.getInt("color");
    }

    public CompoundTag getElytra() {
        return getArmoredElytraData();
    }

    public CompoundTag getChestplate() {
        CompoundTag armelydata = getArmoredElytraData();
        if (armelydata != null) {
            String plate = armelydata.getCompound("tag").getString("Plate");
            switch (plate) {
                case "netherite":
                    return (new ItemStack(Items.NETHERITE_CHESTPLATE)).toTag(new CompoundTag());
                case "diamond":
                    return (new ItemStack(Items.DIAMOND_CHESTPLATE)).toTag(new CompoundTag());
                case "golden":
                    return (new ItemStack(Items.GOLDEN_CHESTPLATE)).toTag(new CompoundTag());
                case "iron":
                    return (new ItemStack(Items.IRON_CHESTPLATE)).toTag(new CompoundTag());
                case "chainmail":
                    return (new ItemStack(Items.CHAINMAIL_CHESTPLATE)).toTag(new CompoundTag());
                case "leather":
                    return (new ItemStack(Items.LEATHER_CHESTPLATE)).toTag(new CompoundTag());
            }
        }
        return null;
    }

    public CompoundTag getArmoredElytraData() {
        if (!stack.isEmpty() && stack.getItem() == Items.ELYTRA) return stack.toTag(new CompoundTag());
        return null;
    }

    public ItemStack getChestplateItemStack() {
        return ItemStack.fromTag(getChestplate());
    }
}
