package net.nullvoid.questline.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.tags.StructureTags;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nullvoid.questline.Questline;
import net.nullvoid.questline.entity.GuildMasterEntity;
import net.nullvoid.questline.entity.ModEntities;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Questline.MOD_ID)
public class ModSpawnEvents {

    // Keep track of villages that already got a Guild Master
    private static final Set<ChunkPos> spawnedVillages = new HashSet<>();

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        ChunkAccess chunk = event.getChunk();

        // Grab the structure registry
        Registry<Structure> registry = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);

        // Loop through all structures tagged as "village"
        for (Holder<Structure> holder : registry.getTagOrEmpty(StructureTags.VILLAGE)) {
            Structure structure = holder.value();
            StructureStart start = chunk.getStartForStructure(structure);

            if (start != null && start.isValid()) {
                ChunkPos villageChunk = chunk.getPos();

                // Skip if we've already spawned a Guild Master here
                if (spawnedVillages.contains(villageChunk)) return;

                BlockPos center = new BlockPos(start.getBoundingBox().getCenter());

                GuildMasterEntity master = ModEntities.GUILD_MASTER.get().create(serverLevel);
                if (master != null) {
                    master.moveTo(center.getX() + 0.5, center.getY(), center.getZ() + 0.5, 0.0F, 0.0F);
                    serverLevel.addFreshEntity(master);
                    System.out.println("Spawned Guild Master in village at " + center);

                    // Mark this village as "handled"
                    spawnedVillages.add(villageChunk);
                }
                return;
            }
        }
    }
}
