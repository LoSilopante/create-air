package com.silopante.createair.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.silopante.createair.createair;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fluids.FluidType;

@Mixin(DivingHelmetItem.class)
public abstract class DivingHelmetItemMixin {
	/** 
	 * Activate helmet "if in water or lava" -> "if in water or bad air or lava" 
	 */
	@Redirect(method = "breatheUnderwater(Lnet/minecraftforge/event/entity/living/LivingEvent$LivingTickEvent;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canDrownInFluidType(Lnet/minecraftforge/fluids/FluidType;)Z"))
	private static boolean redirectCanDrownInFluidType(LivingEntity entity, FluidType fluidtype) {
		final var res = entity.isInFluidType();
		if (fluidtype == (entity.getEyeInFluidType())) 
		{
			return res || createair.airQualityActivatesHelmet(entity);
		}
		return res;
	}
}