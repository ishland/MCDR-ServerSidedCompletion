package com.ishland.mcdrssc.mixin;

import com.ishland.mcdrssc.data.CommandTreeState;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

    @Inject(method = "onDataPacksReloaded", at = @At("RETURN"))
    private void onDataPacksReloaded(CallbackInfo ci) {
        CommandTreeState.reload();
    }

}
