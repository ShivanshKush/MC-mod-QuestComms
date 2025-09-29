package net.nullvoid.questline.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.nullvoid.questline.Questline;

import java.util.function.Supplier;

public class ExecuteDialogueActionPacket {
    private final String action;

    public ExecuteDialogueActionPacket(String action) {
        this.action = action;
    }

    public ExecuteDialogueActionPacket(FriendlyByteBuf buf) {
        this.action = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.action);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // This code is now running on the SERVER
            ServerPlayer player = context.getSender();
            if (player != null) {
                String[] parts = this.action.split(":");
                if (parts.length == 2 && parts[0].equals("startQuest")) {
                    String questId = parts[1];
                    Questline.QUEST_MANAGER.startQuest(player, questId);
                }
            }
        });
        return true;
    }
}