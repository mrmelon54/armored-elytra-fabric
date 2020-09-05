package net.onpointcoding.armoredelytra.models;

import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.onpointcoding.armoredelytra.ArmoredElytraItem;
import net.onpointcoding.armoredelytra.InternalArrays;

public class ArmoredElytraModelProvider implements ModelPredicateProvider {
    @Override
    public float call(ItemStack stack, ClientWorld world, LivingEntity entity) {
        if (stack != null && stack.getItem() == Items.ELYTRA) {
            ArmoredElytraItem item = new ArmoredElytraItem(stack);
            if (item.isValid) {
                float armoredElytraId = InternalArrays.chestplateToArmoredElytraId(item.ChestplateType);
                if (armoredElytraId == -1) {
                    System.out.println("Chestplate type doesn't have a corresponding armored elytra type: returning missing model");
                    return 0;
                }

                return (armoredElytraId + 1) / 10;
            }
        }
        return 0;
    }
}
