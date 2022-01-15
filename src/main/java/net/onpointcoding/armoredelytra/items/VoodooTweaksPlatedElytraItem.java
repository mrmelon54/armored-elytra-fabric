package net.onpointcoding.armoredelytra.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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
