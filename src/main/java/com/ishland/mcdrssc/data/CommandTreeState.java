package com.ishland.mcdrssc.data;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;

import java.nio.file.Path;
import java.util.List;

public class CommandTreeState {

    private static final Path PATH = FabricLoader.getInstance().getConfigDir()
            .resolve("mcdr_command_tree.json");
    private static List<String> commands = new ObjectArrayList<>();

    public static List<String> getCommands() {
        return commands;
    }

    public static void reload() {
        final List<String> read = CommandTreeReader.read(PATH);
        if (read != null) commands = read;
    }

}
