package net.nullvoid.questline.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GuildMasterEntity extends Villager {
    public GuildMasterEntity(EntityType<? extends Villager> type, Level level) {
        super(type, level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide) {
            player.displayClientMessage(Component.literal("💬 Talk to the Guild Master"), true);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }
}
