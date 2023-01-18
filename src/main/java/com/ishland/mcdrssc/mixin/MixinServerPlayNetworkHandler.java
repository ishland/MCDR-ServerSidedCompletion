package com.ishland.mcdrssc.mixin;

import com.ishland.mcdrssc.data.CommandTreeState;
import com.ishland.mcdrssc.ducks.DuckServerPlayNetworkHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.ChatSuggestionsS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler implements DuckServerPlayNetworkHandler {

    @Shadow public abstract void sendPacket(Packet<?> packet);

    @Unique
    private List<String> mcdrssc$lastSentCommands = new ObjectArrayList<>();


    @Unique
    @Override
    public void mcdrssc$resendCommandTree() {
        synchronized (this) {
            final List<String> currentCommands = CommandTreeState.getCommands();
            if (mcdrssc$lastSentCommands.equals(currentCommands)) return;
            this.sendPacket(new ChatSuggestionsS2CPacket(ChatSuggestionsS2CPacket.Action.REMOVE, mcdrssc$lastSentCommands));
            this.sendPacket(new ChatSuggestionsS2CPacket(ChatSuggestionsS2CPacket.Action.ADD, currentCommands));
            this.mcdrssc$lastSentCommands = currentCommands;
        }
    }

}
