package com.silopante.createair.mixins;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DivingHelmetItem.class)
public class BreatheUnderwaterEventMixin
{
    @Inject(method = "breatheUnderwater", at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void breatheUnderwaterOverride(LivingBreatheEvent event, CallbackInfo ci)
    {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (!level.isClientSide) return;
        if (entity.getPersistentData().contains("BacktankThinAir"))
        {
            ci.cancel();
        }
    }
}
