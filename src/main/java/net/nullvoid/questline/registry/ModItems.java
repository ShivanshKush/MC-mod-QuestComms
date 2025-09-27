package net.nullvoid.questline.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem; // Import the correct class
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullvoid.questline.Questline;
import net.nullvoid.questline.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Questline.MOD_ID);

    public static final RegistryObject<Item> GUILD_MASTER_SPAWN_EGG =
            ITEMS.register("guild_master_spawn_egg",
                    () -> new ForgeSpawnEggItem( // Use ForgeSpawnEggItem
                            ModEntities.GUILD_MASTER, // Pass the RegistryObject directly, NO .get()
                            0xFFD700, // primary color (gold)
                            0x8B0000, // secondary color (dark red)
                            new Item.Properties()
                    ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}