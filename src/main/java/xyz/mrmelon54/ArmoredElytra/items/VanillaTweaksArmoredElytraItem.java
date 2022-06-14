package xyz.mrmelon54.ArmoredElytra.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import xyz.mrmelon54.ArmoredElytra.ChestplateWithElytraItem;

public class VanillaTweaksArmoredElytraItem implements ChestplateWithElytraItem {
    public final ItemStack stack;
    public boolean isValid;
    public Item ChestplateType;

    public VanillaTweaksArmoredElytraItem(ItemStack stack) {
        this.stack = stack;
        this.isValid = isArmoredElytra();
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

    @Override
    public boolean hasEnchantmentGlint() {
        NbtList elytraEnch = stack.getEnchantments();
        NbtList chestEnch = getChestplateItemStack().getEnchantments();
        return elytraEnch.size() + chestEnch.size() > 0;
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
        if (!stack.isEmpty() && stack.getItem() == Items.ELYTRA) return stack.getSubNbt("armElyData");
        return null;
    }

    public ItemStack getChestplateItemStack() {
        return ItemStack.fromNbt(getChestplate());
    }
}
