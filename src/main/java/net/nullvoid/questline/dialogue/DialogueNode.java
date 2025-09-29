package net.nullvoid.questline.dialogue;

import net.minecraft.network.chat.Component;
import java.util.List;

public class DialogueNode {

    private final String npcText;
    private final List<PlayerResponse> playerResponses;

    // Constructor for the JSON parser
    public DialogueNode() { this(null, null); }

    public DialogueNode(String npcText, List<PlayerResponse> playerResponses) {
        this.npcText = npcText;
        this.playerResponses = playerResponses;
    }

    public Component getNpcText() {
        return Component.literal(npcText);
    }

    public List<PlayerResponse> getPlayerResponses() {
        return playerResponses;
    }

    /**
     * A static inner class to represent a single choice the player can make.
     */
    public static class PlayerResponse {
        private final String responseText;
        private final String nextNodeId;
        private final String action; // Action to perform (e.g., "startQuest:first_quest")

        // Constructor for the JSON parser
        public PlayerResponse() { this(null, null, null); }

        public PlayerResponse(String responseText, String nextNodeId, String action) {
            this.responseText = responseText;
            this.nextNodeId = nextNodeId;
            this.action = action;
        }

        public Component getResponseText() {
            return Component.literal(responseText);
        }

        public String getNextNodeId() {
            return nextNodeId;
        }

        public String getAction() {
            return action;
        }
    }
}