
package com.silopante.createair.mixins;

import com.silopante.createair.Createair;
import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import fuzs.thinair.api.v1.AirQualityLevel;
import fuzs.thinair.helper.AirQualityHelperImpl;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RemainingAirOverlay.class)
public class RemainingAirOverlayMixin {

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;canDrownInFluidType(Lnet/minecraftforge/fluids/FluidType;)Z"
            ),
            remap = false
    )

    private boolean redirectCanDrownInFluidType(LocalPlayer player, FluidType fluidType) {
        if (player.canDrownInFluidType(fluidType)) return true;
        return Createair.airQualityActivatesHelmet(player);
    }

    // newer versions of create do additional calculations for detecting "air", these calculations are ignored if the player is lava diving.
    // so as a form of future proofing, we also patch the isInLava check.
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/player/LocalPlayer.isInLava ()Z",
                    remap = true
            ),
            remap = false
    )
    private boolean redirectIsInLava(LocalPlayer player) {
        if (player.isInLava()) return true;
        return Createair.airQualityActivatesHelmet(player);
    }
}

