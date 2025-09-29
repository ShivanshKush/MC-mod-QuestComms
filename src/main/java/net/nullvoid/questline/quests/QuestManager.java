package net.nullvoid.questline.quests;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import java.util.Collections;
import net.nullvoid.questline.network.NetworkHandler;
import net.nullvoid.questline.network.ExecuteDialogueActionPacket;

import java.util.*;

public class QuestManager {
    private final Map<UUID, List<Quest>> playerQuests = new HashMap<>();
    List<String> objectives = Arrays.asList(
            "Talk to the Guildmaster",
            "Learn about the quest system"
    );

    public void startQuest(Player player, String questId) {
        // For now, we'll just handle our one test quest.
        // Later, this will look up quest data from a file.
        if (questId.equals("first_quest")) {
            // Create a dummy quest object
            Quest newQuest = new Quest(
                    questId,
                    "A Simple Task",
                    "Gather 10 Cobblestone.",
                    Collections.singletonList("Collect 10 Cobblestone: 0/10"),
                    null // No reward for now
            );

            // Add the quest to the player
            addQuestToPlayer(player.getUUID(), newQuest);

            // Send a message to the player confirming the quest has started
            player.sendSystemMessage(Component.literal("New Quest Started: A Simple Task"));
        }
    }

    // Add quest to a player's quest list
    public void addQuestToPlayer(UUID playerId, Quest quest) {
        playerQuests.computeIfAbsent(playerId, k -> new ArrayList<>()).add(quest);
    }

    // Get quests for a player
    public List<Quest> getPlayerQuests(UUID playerId) {
        return playerQuests.getOrDefault(playerId, new ArrayList<>());
    }

    // Complete a specific quest
    public void completeQuest(UUID playerId, String questId) {
        List<Quest> quests = playerQuests.get(playerId);
        if (quests != null) {
            for (Quest quest : quests) {
                if (questId.equals(quest.getQuestId())) {
                    quest.setStatus(Quest.QuestStatus.COMPLETED);
                }
            }
        }
    }


    // ✅ Assign a test quest to the player
    public void assignTestQuest(Player player) {
        UUID playerId = player.getUUID();

        QuestReward reward = new QuestReward(new ItemStack(Items.DIAMOND, 3), 100);

        Quest testQuest = new Quest(
                "test_quest_1",
                "Welcome to Questline!",
                "Talk to the Guildmaster to begin your journey.",
                objectives,
                reward
        );

        addQuestToPlayer(playerId, testQuest);

        // Notify player in chat
        player.displayClientMessage(Component.literal("New Quest: " + testQuest.getTitle()), false);
 
    }
}
