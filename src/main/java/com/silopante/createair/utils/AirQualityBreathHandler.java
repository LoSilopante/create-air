package com.silopante.createair.utils;

import com.silopante.createair.Createair;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import fuzs.thinair.api.v1.AirQualityLevel;
import fuzs.thinair.helper.AirQualityHelperImpl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Createair.MOD_ID)
public class AirQualityBreathHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        if (!(event.getEntity() instanceof Player player))
            return;

        AirQualityLevel quality = AirQualityHelperImpl.INSTANCE
                .getAirQualityAtLocation(player.level(), player.getEyePosition());

        if (!DivingHelmetItem.isWornBy(entity)) return;

        List<ItemStack> backtanks = BacktankUtil.getAllWithAir(entity);
        if (backtanks.isEmpty()) return;

        Level level = entity.level();

        if (!level.isClientSide() && level.getGameTime() % 20L == 0L) {
            if ((quality == AirQualityLevel.RED || quality == AirQualityLevel.YELLOW) && !player.isInLava() || player.isInWater()){
                BacktankUtil.consumeAir(entity, backtanks.get(0), 1.0F);
                entity.setAirSupply(entity.getMaxAirSupply());
            }
        }

        if (level.isClientSide()) {
            entity.getPersistentData().remove("BacktankThinAir");
            if ((quality == AirQualityLevel.RED || quality == AirQualityLevel.YELLOW) && !player.isInLava() || player.isInWater()){
                float visualBacktankAir = 0;
                for (ItemStack tank : backtanks) {
                    visualBacktankAir += BacktankUtil.getAir(tank);
                }
                entity.getPersistentData().putBoolean("BacktankThinAir", true);
                entity.getPersistentData().putInt("VisualBacktankAir", Math.round(visualBacktankAir));
            }
        }
    }
}
