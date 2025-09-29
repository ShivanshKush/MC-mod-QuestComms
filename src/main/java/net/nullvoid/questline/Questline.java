package net.nullvoid.questline;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.nullvoid.questline.dialogue.DialogueManager;
import net.nullvoid.questline.entity.ModEntities;
import net.nullvoid.questline.event.DialogueEventHandler;
import net.nullvoid.questline.network.NetworkHandler;
import net.nullvoid.questline.quests.QuestManager;
import net.nullvoid.questline.registry.ModItems;
import org.slf4j.Logger;
import net.nullvoid.questline.entity.GuildMasterRenderer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.levelgen.Heightmap;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.nullvoid.questline.villager.ModVillagers;


@Mod(Questline.MOD_ID)
public class Questline {
    public static final String MOD_ID = "questline_comms_mod";
    private static final Logger LOGGER = LogUtils.getLogger();


    public static QuestManager QUEST_MANAGER;
    public static final DialogueManager DIALOGUE_MANAGER = new DialogueManager();

    public Questline(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        IEventBus bus = context.getModEventBus();

        ModEntities.ENTITIES.register(bus);
        ModItems.ITEMS.register(bus);


        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::entityAttributeEvent);
        modEventBus.addListener(this::addCreative);

        //Dialogue
        MinecraftForge.EVENT_BUS.register(DialogueEventHandler.class);

        // Initialize Quest System
        QUEST_MANAGER = new QuestManager();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NetworkHandler.register();
        SpawnPlacements.register(ModEntities.GUILD_MASTER.get(),
                SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.GUILD_MASTER_SPAWN_EGG);
        }

    }

    private void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GUILD_MASTER.get(), Villager.createAttributes().build());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
        LOGGER.info("Server is starting! Questline system ready.");


    }

    @SubscribeEvent
    public void onPlayerLogin(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player) {
            QUEST_MANAGER.assignTestQuest(player);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Questline.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // client-only setup (keybinds, screens, etc.)
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.GUILD_MASTER.get(), GuildMasterRenderer::new);
        }
    }



}
