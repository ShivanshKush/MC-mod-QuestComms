package net.nullvoid.questline.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nullvoid.questline.client.gui.DialogueScreen;

@Mod.EventBusSubscriber
public class DialogueEventHandler {

    @SubscribeEvent
    public static void onRightClickVillager(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Villager villager)) return;

        Player player = event.getEntity();

        if (event.getSide().isClient()) {
            // Your friend's code uses SHIFT + Right-Click to trigger dialogue
            if (player.isShiftKeyDown()) {
                villager.setTradingPlayer(player); // Freeze villager

                Minecraft.getInstance().setScreen(
                        new DialogueScreen("villager_root", villager) // Change this line
                );

                event.setCanceled(true); // Cancel vanilla trading GUI
            }
        }
    }
}