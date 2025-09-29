package net.nullvoid.questline.event; // The package now matches its location

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nullvoid.questline.Questline; // Import your main class

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

// Use your mod's actual MOD_ID to link the events correctly
@Mod.EventBusSubscriber(modid = Questline.MOD_ID)
public class VillagerReactionHandler {

    // Messages to show when a villager is hurt by a player
    private static final String[] HURT_MESSAGES = new String[] {
            "Why are you beating me?",
            "Leave me alone!",
            "What have I done?",
            "wtf....??",
            "That hurts!",
    };

    // Store villagers currently "talking" with a timer
    private static final Map<UUID, Integer> talkingVillagers = new HashMap<>();

    // When villager is hurt
    @SubscribeEvent
    public static void onVillagerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            // Only show messages if the attacker is a player
            if (event.getSource().getEntity() instanceof Player) {
                // Pick a random message
                int idx = ThreadLocalRandom.current().nextInt(HURT_MESSAGES.length);
                String message = HURT_MESSAGES[idx];

                // Set custom text above head
                villager.setCustomName(Component.literal(message));
                villager.setCustomNameVisible(true);

                // Add villager to tracking map with 100 ticks (5 seconds)
                talkingVillagers.put(villager.getUUID(), 100);
            }
        }
    }

    // Tick handler - counts down timers every tick
    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        // Run at end phase, server-side only
        if (event.phase != TickEvent.Phase.END || event.level.isClientSide()) {
            return;
        }

        ServerLevel level = (ServerLevel) event.level;

        Iterator<Map.Entry<UUID, Integer>> iterator = talkingVillagers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Integer> entry = iterator.next();
            UUID villagerId = entry.getKey();
            int timeLeft = entry.getValue() - 1;

            if (timeLeft <= 0) {
                Entity entity = level.getEntity(villagerId);
                if (entity instanceof Villager v) {
                    v.setCustomName(null);
                    v.setCustomNameVisible(false);
                }
                iterator.remove();
            } else {
                entry.setValue(timeLeft);
            }
        }
    }
}