package com.silopante.createair.mixins;
import com.silopante.createair.Createair;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import fuzs.thinair.api.v1.AirQualityLevel;
import fuzs.thinair.helper.AirQualityHelperImpl;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DivingHelmetItem.class)
public class BreatheUnderwaterEventMixin
{
    @Redirect(
            method = "breatheUnderwater",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;canDrownInFluidType(Lnet/minecraftforge/fluids/FluidType;)Z"
            ),
            remap = false
    )
    private static boolean redirectCanDrownInFluidType(LivingEntity player, FluidType fluidType) {
        if (player.canDrownInFluidType(fluidType)) return true;
        return Createair.airQualityActivatesHelmet(player);
    }
}
