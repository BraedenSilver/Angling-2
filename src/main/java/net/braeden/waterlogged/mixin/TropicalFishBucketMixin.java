package net.braeden.waterlogged.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Adds CustomModelData color tints to tropical fish bucket items so the
 * angling mod's tropical_fish_bucket item model can tint overlay textures.
 */
@Mixin(TropicalFish.class)
public class TropicalFishBucketMixin {

    @Inject(method = "saveToBucketTag", at = @At("RETURN"))
    private void angling_addCustomModelData(ItemStack stack, CallbackInfo ci) {
        DyeColor baseColor = stack.get(DataComponents.TROPICAL_FISH_BASE_COLOR);
        DyeColor patternColor = stack.get(DataComponents.TROPICAL_FISH_PATTERN_COLOR);
        if (baseColor != null && patternColor != null) {
            int c0 = 0xFF000000 | baseColor.getTextColor();
            int c1 = 0xFF000000 | patternColor.getTextColor();
            stack.set(DataComponents.CUSTOM_MODEL_DATA,
                    new CustomModelData(java.util.List.of(), java.util.List.of(), java.util.List.of(), java.util.List.of(c0, c1)));
        }
    }
}
