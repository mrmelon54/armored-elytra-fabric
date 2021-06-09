package net.onpointcoding.armoredelytra.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.onpointcoding.armoredelytra.ArmoredElytra;
import net.onpointcoding.armoredelytra.ChestplateWithElytraItem;

public class VanillaTweaksArmoredElytraItem implements ChestplateWithElytraItem {
    public final ItemStack stack;
    public boolean isValid;
    public Item ChestplateType;
    public boolean displayChestplateTick = false;

    public VanillaTweaksArmoredElytraItem(ItemStack stack) {
        this.stack = stack;
        this.isValid = isArmoredElytra();
    }

    public void setDisplayChestplateTick(boolean v) {
        displayChestplateTick = v;
    }

    public boolean getDisplayChestplateTick() {
        return displayChestplateTick;
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

    public static VanillaTweaksArmoredElytraItem fromItemStack(ItemStack stack) {
        VanillaTweaksArmoredElytraItem item = new VanillaTweaksArmoredElytraItem(stack);
        return item.isValid ? item : null;
    }

    public boolean equals(ChestplateWithElytraItem b) {
        if (b == null) return false;
        if (b instanceof VanillaTweaksArmoredElytraItem) return stack == ((VanillaTweaksArmoredElytraItem) b).stack;
        return false;
    }

    public boolean isArmoredElytra() {
        if (!stack.isEmpty()) {
            if (stack.getItem() == Items.ELYTRA) {
                NbtCompound chestplate = getChestplate();
                NbtCompound elytra = getElytra();
                if (chestplate != null && elytra != null) {
                    ItemStack chestplateStack = ItemStack.fromNbt(chestplate);
                    ChestplateType = chestplateStack.getItem();
                    return ChestplateType != Items.AIR;
                }
            }
        }
        return false;
    }

    public int getLeatherChestplateColor() {
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

    public NbtCompound getElytra() {
        NbtCompound armelydata = getArmoredElytraData();
        if (armelydata != null) {
            return armelydata.getCompound("elytra");
        }
        return null;
    }

    public NbtCompound getChestplate() {
        NbtCompound armelydata = getArmoredElytraData();
        if (armelydata != null) {
            return armelydata.getCompound("chestplate");
        }
        return null;
    }

    public NbtCompound getArmoredElytraData() {
        if (!stack.isEmpty() && stack.getItem() == Items.ELYTRA) return stack.getSubTag("armElyData");
        return null;
    }

    public ItemStack getChestplateItemStack() {
        return ItemStack.fromNbt(getChestplate());
    }
}
