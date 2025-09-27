package net.nullvoid.questline.quests;

import java.util.ArrayList;
import java.util.List;

public class PlayerQuestData {
    private final List<String> completedQuests = new ArrayList<>();

    public void completeQuest(String questId) {
        completedQuests.add(questId);
    }

    public boolean hasCompleted(String questId) {
        return completedQuests.contains(questId);
    }
}
