package net.nullvoid.questline.dialogue;

import java.util.HashMap;
import java.util.Map;

public class DialogueNode {
    private final String text;
    private final Map<String, DialogueNode> responses;

    public DialogueNode(String text) {
        this.text = text;
        this.responses = new HashMap<>();
    }

    public void addResponse(String playerResponse, DialogueNode nextNode) {
        responses.put(playerResponse, nextNode);
    }

    public Map<String, DialogueNode> getResponses() {
        return responses;
    }

    public String getText() {
        return text;
    }
}
