package net.nullvoid.questline.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullvoid.questline.Questline;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Questline.MOD_ID);

    public static final RegistryObject<EntityType<GuildMasterEntity>> GUILD_MASTER =
            ENTITIES.register("guild_master",
                    () -> EntityType.Builder.<GuildMasterEntity>of(
                                    (type, level) -> new GuildMasterEntity((EntityType<? extends Villager>) type, level),
                                    MobCategory.MISC)
                            .sized(0.6F, 1.95F) // villager size
                            .build("guild_master"));
}
