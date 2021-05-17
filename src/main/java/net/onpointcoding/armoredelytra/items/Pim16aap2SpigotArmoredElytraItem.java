package net.onpointcoding.armoredelytra.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.onpointcoding.armoredelytra.ChestplateWithElytraItem;

import static net.onpointcoding.armoredelytra.ArmoredElytra.DEFAULT_LEATHER_COLOR;

public class Pim16aap2SpigotArmoredElytraItem implements ChestplateWithElytraItem {
    public final ItemStack stack;
    public boolean isValid;
    public Item ChestplateType;
    public boolean displayChestplateTick = false;

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
        if (b instanceof VanillaTweaksArmoredElytraItem) return stack == ((VanillaTweaksArmoredElytraItem) b).stack;
        return false;
    }

    public boolean isArmoredElytra() {
        if (!stack.isEmpty()) {
            CompoundTag elytra = getElytra();
            if (elytra != null) {
                switch (elytra.getInt("armoredelytra:armor_tier_level")) {
                    case 1:
                        ChestplateType = Items.LEATHER_CHESTPLATE;
                        return true;
                    case 2:
                        ChestplateType = Items.CHAINMAIL_CHESTPLATE;
                        return true;
                    case 3:
                        ChestplateType = Items.GOLDEN_CHESTPLATE;
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
        // Pim's Armored Elytra plans to store this data in future, for now just returning default leather color
        return DEFAULT_LEATHER_COLOR;
    }

    public CompoundTag getElytra() {
        return stack.getSubTag("PublicBukkitValues");
    }

    public CompoundTag getArmoredElytraData() {
        if (!stack.isEmpty()) return stack.toTag(new CompoundTag());
        return null;
    }

    public CompoundTag getChestplate() {
        return stack.toTag(new CompoundTag());
    }

    public ItemStack getChestplateItemStack() {
        return new ItemStack(ChestplateType);
    }
}
