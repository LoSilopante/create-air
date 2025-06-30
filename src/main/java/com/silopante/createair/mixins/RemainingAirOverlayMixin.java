
package com.silopante.createair.mixins;

import com.silopante.createair.Createair;
import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import fuzs.thinair.api.v1.AirQualityLevel;
import fuzs.thinair.helper.AirQualityHelperImpl;
import net.minecraft.client.player.LocalPlayer;
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
        AirQualityLevel quality = AirQualityHelperImpl.INSTANCE.getAirQualityAtLocation(player.level(), player.getEyePosition());
        boolean badAir = quality == AirQualityLevel.RED || quality == AirQualityLevel.YELLOW;
        Createair.LOGGER.info("RemainingAirOverlayMixin redirectCanDrownInFluidType called");
        return player.canDrownInFluidType(fluidType) || badAir;
    }
}

