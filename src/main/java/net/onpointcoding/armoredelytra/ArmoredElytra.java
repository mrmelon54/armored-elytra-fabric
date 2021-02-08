package net.onpointcoding.armoredelytra;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.onpointcoding.armoredelytra.duckinterfaces.ArmoredElytraWearingEntity;
import net.onpointcoding.armoredelytra.models.ArmoredElytraModelProvider;

public class ArmoredElytra implements ModInitializer, ClientModInitializer {
    public static final String MODID = "armoredelytra";
    private CompoundTag chestplateOldTag;

    @Override
    public void onInitialize() {
        System.out.println("Loading armored elytra");
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
        ArmoredElytraModelProvider armoredElytraModelProvider = new ArmoredElytraModelProvider();
        FabricModelPredicateProviderRegistry.register(Items.ELYTRA, new Identifier("armored_elytra_type"), armoredElytraModelProvider);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (stack == null) return -1;
            ArmoredElytraItem item = ArmoredElytraItem.fromItemStack(stack);
            if (item == null || !item.isValid) return -1;
            return tintIndex > 0 ? -1 : item.getLeatherChestplateColor();
        }, Items.ELYTRA);
    }

    @Override
    public void onInitializeClient() {
    }

    public void tick(MinecraftClient mc) {
        if (mc.world != null) {
            for (Entity entity : mc.world.getEntities()) {
                if (entity == null) continue;
                if (entity instanceof ArmoredElytraWearingEntity) {
                    ((ArmoredElytraWearingEntity) entity).updateWearingArmoredElytra();
                }
            }
        }
    }
}
