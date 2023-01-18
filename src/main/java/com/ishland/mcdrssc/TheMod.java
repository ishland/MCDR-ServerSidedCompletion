package com.ishland.mcdrssc;

import com.ishland.mcdrssc.data.CommandTreeState;
import net.fabricmc.api.ModInitializer;

public class TheMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandTreeState.reload();
    }
}
