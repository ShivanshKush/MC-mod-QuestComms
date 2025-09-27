package net.nullvoid.questline.quests;

import net.minecraft.world.item.ItemStack;

public class QuestReward {
    private final ItemStack rewardItem;
    private final int experience;

    public QuestReward(ItemStack rewardItem, int experience) {
        this.rewardItem = rewardItem;
        this.experience = experience;
    }

    public ItemStack getRewardItem() {
        return rewardItem;
    }

    public int getExperience() {
        return experience;
    }
}
