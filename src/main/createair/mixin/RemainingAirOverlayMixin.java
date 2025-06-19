package com.silopante.createair.mixin;

import com.silopante.createair.createair;
import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RemainingAirOverlay.class})
public class RemainingAirOverlayMixin {
    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean render(LocalPlayer instance, TagKey tagKey) {
        return instance.isEyeInFluid(FluidTags.WATER) || createair.airQualityActivatesHelmet((LivingEntity)instance);
    }
}

