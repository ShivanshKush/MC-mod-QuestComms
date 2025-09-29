package net.nullvoid.questline.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.nullvoid.questline.Questline;
import net.nullvoid.questline.dialogue.DialogueNode;
import net.nullvoid.questline.network.ExecuteDialogueActionPacket;
import net.nullvoid.questline.network.NetworkHandler;

public class DialogueScreen extends Screen {

    private final Villager villager;
    private final DialogueNode currentNode;

    public DialogueScreen(String nodeId, Villager villager) {
        super(Component.literal("Dialogue"));
        this.villager = villager;
        // Get the dialogue data from our global manager
        this.currentNode = Questline.DIALOGUE_MANAGER.getNode(nodeId);
    }

    @Override
    protected void init() {
        super.init();
        int centerX = this.width / 2;
        int buttonY = this.height / 2;

        if (this.currentNode == null) {
            // Failsafe if the node ID is invalid
            this.onClose();
            return;
        }

        // Dynamically create a button for each player response in the current node
        for (DialogueNode.PlayerResponse response : this.currentNode.getPlayerResponses()) {
            Button button = Button.builder(response.getResponseText(), (btn) -> handleResponse(response))
                    .bounds(centerX - 100, buttonY, 200, 20)
                    .build();
            this.addRenderableWidget(button);
            buttonY += 25; // Move next button down
        }

        // Add a default "Goodbye" button if there are no other options
        if (this.currentNode.getPlayerResponses().isEmpty()) {
            Button button = Button.builder(Component.literal("Goodbye"), (btn) -> this.onClose())
                    .bounds(centerX - 100, buttonY, 200, 20)
                    .build();
            this.addRenderableWidget(button);
        }
    }

    private void handleResponse(DialogueNode.PlayerResponse response) {
        // 1. Process the action by sending a packet to the server
        if (response.getAction() != null && !response.getAction().isEmpty()) {
            NetworkHandler.sendToServer(new ExecuteDialogueActionPacket(response.getAction()));
        }

        // 2. Handle navigating to the next dialogue node
        String nextNodeId = response.getNextNodeId();
        if (nextNodeId != null && Questline.DIALOGUE_MANAGER.getNode(nextNodeId) != null) {
            this.minecraft.setScreen(new DialogueScreen(nextNodeId, this.villager));
        } else {
            this.onClose();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.currentNode == null) return; // Don't render if node is invalid

        int boxWidth = 250;
        int boxHeight = 240;
        int boxX = (this.width - boxWidth) / 2;
        int boxY = (this.height - boxHeight) / 2;

        guiGraphics.fill(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0xCC000000); // Made it slightly more opaque
        // Display the NPC text from the current node
        guiGraphics.drawWordWrap(this.font, this.currentNode.getNpcText(), boxX + 10, boxY + 10, boxWidth - 20, 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        super.onClose();
        if (villager != null) {
            villager.setTradingPlayer(null); // Unfreeze the villager
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}