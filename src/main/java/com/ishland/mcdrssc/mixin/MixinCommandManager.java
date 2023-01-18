package com.ishland.mcdrssc.mixin;

import com.ishland.mcdrssc.ducks.DuckServerPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ChatSuggestionsS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class MixinCommandManager {

    @Inject(method = "sendCommandTree", at = @At("RETURN"))
    private void postSendCommandTree(ServerPlayerEntity player, CallbackInfo ci) {
        ((DuckServerPlayNetworkHandler) player.networkHandler).mcdrssc$resendCommandTree();
    }

}
