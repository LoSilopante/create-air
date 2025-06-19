package com.silopante.createair;

import fuzs.thinair.api.v1.AirQualityLevel;
import fuzs.thinair.helper.AirQualityHelperImpl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;

@Mod(value="createair")
public class createair {
    public static final String MOD_ID = "createair";

    public static boolean airQualityActivatesHelmet(LivingEntity entity) {
        AirQualityLevel air = AirQualityHelperImpl.INSTANCE.getAirQualityAtLocation(entity.level(), entity.getEyePosition());
        return air == AirQualityLevel.RED || air == AirQualityLevel.YELLOW;
    }
}

