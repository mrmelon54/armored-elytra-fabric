package net.onpointcoding.armoredelytra.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.onpointcoding.armoredelytra.ChestplateWithElytraItem;
import net.onpointcoding.armoredelytra.duckinterfaces.ArmoredElytraWearingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MobEntity.class, PlayerEntity.class, ArmorStandEntity.class})
public abstract class MixinLivingEntityChildren extends LivingEntity {
    protected MixinLivingEntityChildren(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getEquippedStack", at = @At("HEAD"), cancellable = true)
    public void hackGetEquippedStack(EquipmentSlot equipmentSlot, CallbackInfoReturnable<ItemStack> callbackInfoReturnable) {
        if (equipmentSlot == EquipmentSlot.CHEST) {
            if (this instanceof ArmoredElytraWearingEntity) {
                ArmoredElytraWearingEntity armoredElytraWearingEntity = (ArmoredElytraWearingEntity) this;
                ChestplateWithElytraItem armoredElytraItem = armoredElytraWearingEntity.getArmoredElytra();
                if (armoredElytraItem != null && armoredElytraItem.getDisplayChestplateTick()) {
                    ItemStack chestplateItemStack = armoredElytraItem.getChestplateItemStack();
                    if (chestplateItemStack != null) {
                        callbackInfoReturnable.setReturnValue(chestplateItemStack);
                        callbackInfoReturnable.cancel();
                    }
                }
            }
        }
    }
}
