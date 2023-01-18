package com.ishland.mcdrssc.data;

import com.google.gson.stream.JsonReader;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CommandTreeReader {

    @Nullable
    public static List<String> read(Path path) {
        List<String> commands = null;

        try (final var reader = new JsonReader(Files.newBufferedReader(path))) {
            reader.beginObject();

            while (reader.hasNext()) {
                final var name = reader.nextName();
                if (name.equals("data")) {
                    commands = readRecursively(reader);
                } else {
                    System.err.println("Unknown field: " + reader.getPath());
                    reader.skipValue();
                }
            }

            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (commands == null) {
            System.err.println("Read command tree but got nothing?");
            return null;
        } else {
            return commands;
        }

    }

    private static List<String> readRecursively(JsonReader reader) throws IOException {
        // [{
        //     "name": "status",
        //     "type": "LITERAL",
        //     "children": []
        // }, ...]

        List<String> commands = new ObjectArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            String name = null;
            String type = null;
            List<String> children = null;

            reader.beginObject();
            while (reader.hasNext()) {
                final var key = reader.nextName();
                if (key.equals("name")) {
                    name = reader.nextString();
                } else if (key.equals("type")) {
                    type = reader.nextString();
                } else if (key.equals("children")) {
                    children = readRecursively(reader);
                } else {
                    System.err.println("Unknown field: " + reader.getPath());
                    reader.skipValue();
                }
            }

            if (name == null) {
                System.err.println("Missing field: " + reader.getPath() + ".name");
                continue;
            }
            if (type == null) {
                System.err.println("Missing field: " + reader.getPath() + ".type");
                continue;
            }

            if (type.equals("LITERAL")) {
                commands.add(name);
                if (children != null && !children.isEmpty()) {
                    String finalName = name;
                    commands.addAll(children.stream().map(s -> finalName + " " + s).toList());
                }
            }

            reader.endObject();
        }
        reader.endArray();

        return commands;
    }

}
