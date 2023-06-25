package com.silopante.createair;

import fuzs.thinair.helper.AirHelper;
import fuzs.thinair.helper.AirQualityLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;

@Mod(createair.MOD_ID)
public class createair {
	public static final String MOD_ID = "createair";
	
	public static boolean airQualityActivatesHelmet(LivingEntity entity) {
		final var air = AirHelper.getO2LevelFromLocation(entity.getEyePosition(), entity.getLevel()).getFirst();
		return air == AirQualityLevel.RED || air == AirQualityLevel.YELLOW;
	}
}
