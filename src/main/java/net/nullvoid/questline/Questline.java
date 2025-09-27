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
import net.nullvoid.questline.entity.ModEntities;
import net.nullvoid.questline.quests.QuestManager;
import net.nullvoid.questline.registry.ModItems;
import net.nullvoid.questline.villager.ModVillagers;
import org.slf4j.Logger;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.nullvoid.questline.entity.GuildMasterRenderer;
import net.minecraft.world.item.CreativeModeTabs;



// The value here should match an entry in the META-INF/mods.toml file
@Mod(Questline.MOD_ID)
public class Questline {
    public static final String MOD_ID = "questline_comms_mod";
    private static final Logger LOGGER = LogUtils.getLogger();


    public static QuestManager QUEST_MANAGER;

    public Questline(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        IEventBus bus = context.getModEventBus();
        ModEntities.ENTITIES.register(bus);
        ModItems.ITEMS.register(bus);


        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        // Initialize Quest System
        QUEST_MANAGER = new QuestManager();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            // If so, add our spawn egg to the list
            event.accept(ModItems.GUILD_MASTER_SPAWN_EGG);
        }

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
