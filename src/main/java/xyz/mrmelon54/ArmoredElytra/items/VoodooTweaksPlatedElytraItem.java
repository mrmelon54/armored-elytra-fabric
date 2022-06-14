package xyz.mrmelon54.ArmoredElytra.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import xyz.mrmelon54.ArmoredElytra.ChestplateWithElytraItem;

public class VoodooTweaksPlatedElytraItem implements ChestplateWithElytraItem {
    public final ItemStack stack;
    public boolean isValid;
    public Item ChestplateType;

    public VoodooTweaksPlatedElytraItem(ItemStack stack) {
        this.stack = stack;
        this.isValid = isArmoredElytra();
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
        return getArmoredElytraData();
    }

    public NbtCompound getChestplate() {
        NbtCompound armelydata = getArmoredElytraData();
        if (armelydata != null) {
            String plate = armelydata.getCompound("tag").getString("Plate");
            NbtCompound chestplateStack;
            switch (plate) {
                case "netherite":
                    chestplateStack = (new ItemStack(Items.NETHERITE_CHESTPLATE)).writeNbt(new NbtCompound());
                    break;
                case "diamond":
                    chestplateStack = (new ItemStack(Items.DIAMOND_CHESTPLATE)).writeNbt(new NbtCompound());
                    break;
                case "golden":
                    chestplateStack = (new ItemStack(Items.GOLDEN_CHESTPLATE)).writeNbt(new NbtCompound());
                    break;
                case "iron":
                    chestplateStack = (new ItemStack(Items.IRON_CHESTPLATE)).writeNbt(new NbtCompound());
                    break;
                case "chainmail":
                    chestplateStack = (new ItemStack(Items.CHAINMAIL_CHESTPLATE)).writeNbt(new NbtCompound());
                    break;
                case "leather":
                    chestplateStack = (new ItemStack(Items.LEATHER_CHESTPLATE)).writeNbt(new NbtCompound());
                    break;
                default:
                    return null;
            }
            if (armelydata.getCompound("tag").contains("color")) {
                NbtCompound displaytag = chestplateStack.getCompound("tag").getCompound("display");
                displaytag.putInt("color", armelydata.getCompound("tag").getInt("color"));
                chestplateStack.getCompound("tag").put("display", displaytag);
            }
            return chestplateStack;
        }
        return null;
    }

    public NbtCompound getArmoredElytraData() {
        if (!stack.isEmpty() && stack.getItem() == Items.ELYTRA) return stack.writeNbt(new NbtCompound());
        return null;
    }

    public ItemStack getChestplateItemStack() {
        return ItemStack.fromNbt(getChestplate());
    }
}
