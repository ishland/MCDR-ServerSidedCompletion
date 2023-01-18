package com.ishland.mcdrssc.mixin;

import com.ishland.mcdrssc.data.CommandTreeState;
import com.ishland.mcdrssc.ducks.DuckServerPlayNetworkHandler;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

    @Shadow @Final private List<ServerPlayerEntity> players;

    @Inject(method = "onDataPacksReloaded", at = @At("RETURN"))
    private void onDataPacksReloaded(CallbackInfo ci) {
        CommandTreeState.reload();
        for (ServerPlayerEntity player : this.players) {
            ((DuckServerPlayNetworkHandler) player.networkHandler).mcdrssc$resendCommandTree();
        }
    }

}
