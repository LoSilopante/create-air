package com.silopante.createair;

import com.mojang.logging.LogUtils;
import fuzs.thinair.api.v1.AirQualityLevel;
import fuzs.thinair.helper.AirQualityHelperImpl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(value="createair")
public class Createair {
    public static final String MOD_ID = "createair";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static boolean airQualityActivatesHelmet(LivingEntity entity) {
        AirQualityLevel air = AirQualityHelperImpl.INSTANCE.getAirQualityAtLocation(entity.level(), entity.getEyePosition());
        return air == AirQualityLevel.RED || air == AirQualityLevel.YELLOW;
    }
}

