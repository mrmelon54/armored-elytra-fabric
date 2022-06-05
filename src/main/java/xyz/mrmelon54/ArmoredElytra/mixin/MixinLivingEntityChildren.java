package xyz.mrmelon54.ArmoredElytra.mixin;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.mrmelon54.ArmoredElytra.ChestplateWithElytraItem;
import xyz.mrmelon54.ArmoredElytra.duckinterfaces.ArmoredElytraWearingEntity;
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
            if (this instanceof ArmoredElytraWearingEntity armoredElytraWearingEntity) {
                ChestplateWithElytraItem armoredElytraItem = armoredElytraWearingEntity.getArmoredElytra();
                if (armoredElytraItem != null && armoredElytraItem.getDisplayChestplateTick()) {
                    ItemStack chestplateItemStack = armoredElytraItem.getChestplateItemStack();
                    if (chestplateItemStack != null) {
                        if (armoredElytraItem.hasEnchantmentGlint())
                            chestplateItemStack.addEnchantment(Enchantments.MENDING, 1);
                        callbackInfoReturnable.setReturnValue(chestplateItemStack);
                        callbackInfoReturnable.cancel();
                    }
                }
            }
        }
    }
}
