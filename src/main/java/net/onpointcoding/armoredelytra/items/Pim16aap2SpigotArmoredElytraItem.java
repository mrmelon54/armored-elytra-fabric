package net.onpointcoding.armoredelytra.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.onpointcoding.armoredelytra.ArmoredElytra;
import net.onpointcoding.armoredelytra.ChestplateWithElytraItem;

public class Pim16aap2SpigotArmoredElytraItem implements ChestplateWithElytraItem {
    public final ItemStack stack;
    public boolean isValid;
    public Item ChestplateType;
    public boolean displayChestplateTick = false;
    public int color = ArmoredElytra.DEFAULT_LEATHER_COLOR;

    public Pim16aap2SpigotArmoredElytraItem(ItemStack stack) {
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

    public static Pim16aap2SpigotArmoredElytraItem fromItemStack(ItemStack stack) {
        Pim16aap2SpigotArmoredElytraItem item = new Pim16aap2SpigotArmoredElytraItem(stack);
        return item.isValid ? item : null;
    }

    public boolean equals(ChestplateWithElytraItem b) {
        if (b == null) return false;
        if (b instanceof Pim16aap2SpigotArmoredElytraItem) return stack == ((Pim16aap2SpigotArmoredElytraItem) b).stack;
        return false;
    }

    public boolean isArmoredElytra() {
        if (!stack.isEmpty()) {
            NbtCompound elytra = getElytra();
            if (elytra != null) {
                switch (elytra.getInt("armoredelytra:armor_tier_level")) {
                    case 1:
                        ChestplateType = Items.LEATHER_CHESTPLATE;
                        if (elytra.getKeys().contains("armoredelytra:armored_elytra_color"))
                            color = elytra.getInt("armoredelytra:armored_elytra_color");
                        return true;
                    case 2:
                        ChestplateType = Items.GOLDEN_CHESTPLATE;
                        return true;
                    case 3:
                        ChestplateType = Items.CHAINMAIL_CHESTPLATE;
                        return true;
                    case 4:
                        ChestplateType = Items.IRON_CHESTPLATE;
                        return true;
                    case 5:
                        ChestplateType = Items.DIAMOND_CHESTPLATE;
                        return true;
                    case 6:
                        ChestplateType = Items.NETHERITE_CHESTPLATE;
                        return true;
                    default:
                        ChestplateType = Items.AIR;
                        return false;
                }
            }
        }
        return false;
    }

    public int getLeatherChestplateColor() {
        if (ChestplateType != Items.LEATHER_CHESTPLATE) return -1;
        return color;
    }

    public NbtCompound getElytra() {
        return stack.getSubNbt("PublicBukkitValues");
    }

    public NbtCompound getArmoredElytraData() {
        if (!stack.isEmpty()) return stack.writeNbt(new NbtCompound());
        return null;
    }

    public NbtCompound getChestplate() {
        ItemStack chestplate = new ItemStack(ChestplateType);
        return chestplate.getOrCreateNbt();
    }

    public ItemStack getChestplateItemStack() {
        ItemStack chestplate = new ItemStack(ChestplateType);
        if (ChestplateType == Items.LEATHER_CHESTPLATE) {
            NbtCompound subtag = new NbtCompound();
            subtag.putInt("color", color);
            chestplate.setSubNbt("display", subtag);
        }
        NbtCompound tag = chestplate.getOrCreateNbt();
        tag.put("Enchantments", stack.getEnchantments());
        chestplate.setNbt(tag);
        return chestplate;
    }
}
