package net.nullvoid.questline.quests;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class QuestManager {
    private final Map<UUID, List<Quest>> playerQuests = new HashMap<>();
    List<String> objectives = Arrays.asList(
            "Talk to the Guildmaster",
            "Learn about the quest system"
    );

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
