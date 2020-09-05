package net.onpointcoding.armoredelytra.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.onpointcoding.armoredelytra.ArmoredElytraItem;
import net.onpointcoding.armoredelytra.duckinterfaces.ArmoredElytraWearingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity implements ArmoredElytraWearingEntity {
    private ArmoredElytraItem armoredElytraItem;

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Override
    public ArmoredElytraItem getArmoredElytra() {
        return armoredElytraItem;
    }

    @Override
    public void setArmoredElytra(ArmoredElytraItem value) {
        if (armoredElytraItem != null && armoredElytraItem.equals(value)) return;
        armoredElytraItem = value;
    }

    public void updateWearingArmoredElytra() {
        ItemStack stack = getEquippedStack(EquipmentSlot.CHEST);
        if (armoredElytraItem != null && stack == armoredElytraItem.stack) return;
        setArmoredElytra(ArmoredElytraItem.fromItemStack(stack));
    }
}
