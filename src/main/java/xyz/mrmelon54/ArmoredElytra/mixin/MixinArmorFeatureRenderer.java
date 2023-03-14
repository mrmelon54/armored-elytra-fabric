package xyz.mrmelon54.ArmoredElytra.mixin;

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
import org.jetbrains.annotations.Nullable;
import xyz.mrmelon54.ArmoredElytra.ChestplateWithElytraItem;
import xyz.mrmelon54.ArmoredElytra.duckinterfaces.ArmoredElytraWearingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Shadow
    public abstract void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l);

    @Shadow
    protected abstract A getModel(EquipmentSlot slot);

    @Shadow
    protected abstract void setVisible(A bipedModel, EquipmentSlot slot);

    @Shadow
    protected abstract boolean usesInnerModel(EquipmentSlot slot);

    @Shadow
    protected abstract void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean usesSecondLayer, A model, boolean legs, float red, float green, float blue, @Nullable String overlay);

    @Inject(at = @At("HEAD"), method = "render*")
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo info) {
        renderChestplateForArmouredElytra(matrixStack, vertexConsumerProvider, livingEntity, i, this.getModel(EquipmentSlot.CHEST));
    }

    private void renderChestplateForArmouredElytra(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, int light, A model) {
        if (entity instanceof ArmoredElytraWearingEntity armoredElytraWearingEntity) {
            ChestplateWithElytraItem armoredElytra = armoredElytraWearingEntity.getArmoredElytra();
            if (armoredElytra != null) {
                ItemStack chestplateItemStack = armoredElytra.getChestplateItemStack();
                if (chestplateItemStack.getItem() instanceof ArmorItem armorItem) {
                    this.getContextModel().copyBipedStateTo(model);
                    this.setVisible(model, EquipmentSlot.CHEST);
                    boolean bl = this.usesInnerModel(EquipmentSlot.CHEST);
                    boolean bl2 = armoredElytra.hasEnchantmentGlint();
                    int i = armoredElytra.getLeatherChestplateColor();
                    if (i != -1) {
                        float f = (float) (i >> 16 & 255) / 255f;
                        float g = (float) (i >> 8 & 255) / 255f;
                        float h = (float) (i & 255) / 255f;
                        this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, bl, f, g, h, null);
                        this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, bl, 1f, 1f, 1f, "overlay");
                    } else {
                        this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, bl, 1f, 1f, 1f, null);
                    }
                }
            }
        }
    }
}
