package net.nullvoid.questline.entity;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.nullvoid.questline.Questline;

public class GuildMasterRenderer extends MobRenderer<GuildMasterEntity, VillagerModel<GuildMasterEntity>> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Questline.MOD_ID, "textures/entity/guildmaster.png");

    public GuildMasterRenderer(EntityRendererProvider.Context context) {
        super(context, new VillagerModel<>(context.bakeLayer(ModelLayers.VILLAGER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GuildMasterEntity entity) {
        return TEXTURE;
    }
}
