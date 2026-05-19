package com.silopante.createair.mixins;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import fuzs.thinair.helper.AirQualityHelperImpl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AirQualityHelperImpl.class)
public abstract class CreateAirQualityFixMixin {

    @Inject(method = "isSensitiveToAirQuality", at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void overrideAirSensitivity(LivingEntity ent, CallbackInfoReturnable<Boolean> cir) {
        if (!(ent instanceof Player player) || player.isCreative() || player.isSpectator()) return;
        if (DivingHelmetItem.isWornBy(player)) {
            List<ItemStack> backtanks = BacktankUtil.getAllWithAir(player);

            if (!backtanks.isEmpty() && DivingHelmetItem.isWornBy(player)) {
                cir.setReturnValue(false);
            }
        }
    }
}