package xyz.mrmelon54.armoredelytra;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import xyz.mrmelon54.armoredelytra.duckinterfaces.ArmoredElytraWearingEntity;
import xyz.mrmelon54.armoredelytra.models.ArmoredElytraModelProvider;

public class ArmoredElytra implements ModInitializer, ClientModInitializer {
    public static final int DEFAULT_LEATHER_COLOR = 10511680;

    @Override
    public void onInitialize() {
        System.out.println("Loading armored elytra");

        // Listen for the end of every tick
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);

        // Create a new model provider
        ArmoredElytraModelProvider armoredElytraModelProvider = new ArmoredElytraModelProvider();

        // Setup the model provider and color provider for the elytra item
        FabricModelPredicateProviderRegistry.register(Items.ELYTRA, new Identifier("armored_elytra_type"), armoredElytraModelProvider);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (stack == null) return -1;
            ChestplateWithElytraItem item = ChestplateWithElytraItem.fromItemStack(stack);
            if (item == null || !item.getStatus()) return -1;
            return tintIndex > 0 ? -1 : item.getLeatherChestplateColor();
        }, Items.ELYTRA);

        // Setup the model provider for all chestplate items
        for (Item chestplateType : InternalArrays.CHESTPLATES) {
            FabricModelPredicateProviderRegistry.register(chestplateType, new Identifier("armored_elytra_type"), armoredElytraModelProvider);
        }
    }

    @Override
    public void onInitializeClient() {
    }

    public void tick(MinecraftClient mc) {
        if (mc.world != null) {
            // rip fps
            // kinda surprised this doesn't kill the fps
            for (Entity entity : mc.world.getEntities()) {
                if (entity == null) continue;
                if (entity instanceof ArmoredElytraWearingEntity) {
                    ((ArmoredElytraWearingEntity) entity).updateWearingArmoredElytra();
                }
            }
        }
    }
}
