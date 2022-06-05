package xyz.mrmelon54.ArmoredElytra.models;

import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import xyz.mrmelon54.ArmoredElytra.ChestplateWithElytraItem;
import xyz.mrmelon54.ArmoredElytra.InternalArrays;
import org.jetbrains.annotations.Nullable;

public class ArmoredElytraModelProvider implements UnclampedModelPredicateProvider {
    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
        if (stack != null && !stack.isEmpty()) {
            ChestplateWithElytraItem item = ChestplateWithElytraItem.fromItemStack(stack);
            if (item != null && item.getStatus()) {
                float armoredElytraId = InternalArrays.chestplateToArmoredElytraId(item.getChestplateType());
                if (armoredElytraId == -1) {
                    System.out.println("Chestplate type doesn't have a corresponding armored elytra type: returning missing model");
                    return 0;
                }

                return (armoredElytraId + 1) / 100;
            }
        }
        return 0;
    }
}
