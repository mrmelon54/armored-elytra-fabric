package net.onpointcoding.armoredelytra.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.onpointcoding.armoredelytra.ArmoredElytraItem;
import net.onpointcoding.armoredelytra.duckinterfaces.ArmoredElytraWearingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    T livingEntity;

    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Shadow
    protected abstract void renderArmorParts(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ArmorItem armorItem, boolean bl, A bipedEntityModel, boolean bl2, float f, float g, float h, String string);

    @Shadow
    protected abstract void setVisible(A bipedModel, EquipmentSlot slot);

    @Shadow
    protected abstract boolean usesSecondLayer(EquipmentSlot slot);

    @Shadow
    public abstract void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l);

    @Inject(at = @At("HEAD"), method = "render")
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo info) {
        this.livingEntity = livingEntity;
    }

    @Inject(at = @At("HEAD"), method = "renderArmor", cancellable = true)
    private void fixRenderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T livingEntity, EquipmentSlot equipmentSlot, int i, A bipedEntityModel, CallbackInfo info) {
        ItemStack itemStack = livingEntity.getEquippedStack(equipmentSlot);
        if (equipmentSlot == EquipmentSlot.CHEST && itemStack.getItem() == Items.ELYTRA) {
            if (livingEntity instanceof ArmoredElytraWearingEntity) {
                ArmoredElytraWearingEntity elytraWearing = (ArmoredElytraWearingEntity) livingEntity;
                ArmoredElytraItem item = elytraWearing.getArmoredElytra();

                if (item != null && item.isValid) {
                    ArmorItem armorItem = (ArmorItem) item.ChestplateType;

                    // Vanilla code for rendering armor
                    this.getContextModel().setAttributes(bipedEntityModel);
                    setVisible(bipedEntityModel, equipmentSlot);
                    boolean bl = usesSecondLayer(equipmentSlot);
                    boolean bl2 = itemStack.hasGlint();

                    // Updated to work with the Armored Elytra
                    int j = item.getLeatherChestplateColor();
                    float f = (float) (j >> 16 & 255) / 255.0F;
                    float g = (float) (j >> 8 & 255) / 255.0F;
                    float h = (float) (j & 255) / 255.0F;
                    this.renderArmorParts(matrices, vertexConsumers, i, armorItem, bl2, bipedEntityModel, bl, f, g, h, null);
                }
            }
        }
    }
}
