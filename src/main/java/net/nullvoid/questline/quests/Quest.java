package net.nullvoid.questline.quests;

import java.util.List;

public class Quest {
    private final String questId;
    private final String title;
    private final String description;
    private final List<String> objectives;
    private final QuestReward reward;
    private QuestStatus status;

    public Quest(String questId, String title, String description, List<String> objectives, QuestReward reward) {
        this.questId = questId;
        this.title = title;
        this.description = description;
        this.objectives = objectives;
        this.reward = reward;
        this.status = QuestStatus.NOT_STARTED;
    }

    public String getQuestId() {
        return questId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public QuestReward getReward() {
        return reward;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    public enum QuestStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }
}
