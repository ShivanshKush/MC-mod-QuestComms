package net.nullvoid.questline.dialogue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.nullvoid.questline.Questline;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogueManager {

    private final Map<String, DialogueNode> dialogueNodes = new HashMap<>();

    public DialogueManager() {
        loadDialoguesFromFile();
    }

    public DialogueNode getNode(String id) {
        return dialogueNodes.get(id);
    }

    private void loadDialoguesFromFile() {
        // We use Google's Gson library, which is built into Minecraft, to parse the JSON.
        Gson gson = new Gson();

        // This defines the structure of our JSON file for the parser.
        Type dialogueMapType = new TypeToken<Map<String, DialogueNode>>() {}.getType();

        // Find our dialogues.json file within the mod's assets.
        String path = "/assets/" + Questline.MOD_ID + "/dialogue/dialogues.json";
        InputStream inputStream = DialogueManager.class.getResourceAsStream(path);

        if (inputStream == null) {
            System.err.println("Could not find dialogues.json file!");
            return;
        }

        // Read the file and parse it into our map of DialogueNodes.
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        Map<String, DialogueNode> loadedNodes = gson.fromJson(reader, dialogueMapType);

        // Clear the old nodes and add all the newly loaded ones.
        this.dialogueNodes.clear();
        this.dialogueNodes.putAll(loadedNodes);

        System.out.println("Loaded " + this.dialogueNodes.size() + " dialogue nodes.");
    }
}