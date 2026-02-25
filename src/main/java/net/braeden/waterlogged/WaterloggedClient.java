package net.braeden.waterlogged;

import net.braeden.waterlogged.block.entity.RoeBlockEntity;
import net.braeden.waterlogged.block.entity.SeaSlugEggsBlockEntity;
import net.braeden.waterlogged.entity.util.SeaSlugColor;
import net.braeden.waterlogged.particle.AlgaeParticle;
import net.braeden.waterlogged.particle.WormParticle;
import net.braeden.waterlogged.entity.client.*;
import net.braeden.waterlogged.entity.client.AnemoneBlockEntityRenderer;
import net.braeden.waterlogged.entity.client.StarfishBlockEntityRenderer;
import net.braeden.waterlogged.entity.client.UrchinBlockEntityRenderer;
import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.entity.WaterloggedEntities;
import net.braeden.waterlogged.particle.WaterloggedParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class WaterloggedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Block render layers
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.ROE, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.DUCKWEED, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.OYSTERS, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.CLAM, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.SEA_SLUG_EGGS, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.PAPYRUS, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.SARGASSUM, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.ALGAE, ChunkSectionLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(WaterloggedBlocks.AQUARIUM_GLASS, ChunkSectionLayer.CUTOUT);

        // Entity renderers
        EntityRenderers.register(WaterloggedEntities.FRY,          FryRenderer::new);
        EntityRenderers.register(WaterloggedEntities.SUNFISH,       SunfishRenderer::new);
        EntityRenderers.register(WaterloggedEntities.PELICAN,       PelicanRenderer::new);
        EntityRenderers.register(WaterloggedEntities.SEA_SLUG,      SeaSlugRenderer::new);
        EntityRenderers.register(WaterloggedEntities.CRAB,          CrabRenderer::new);
        EntityRenderers.register(WaterloggedEntities.DONGFISH,      DongfishRenderer::new);
        EntityRenderers.register(WaterloggedEntities.CATFISH,       CatfishRenderer::new);
        EntityRenderers.register(WaterloggedEntities.SEAHORSE,      SeahorseRenderer::new);
        EntityRenderers.register(WaterloggedEntities.BUBBLE_EYE,    BubbleEyeRenderer::new);
        EntityRenderers.register(WaterloggedEntities.ANOMALOCARIS,  AnomalocarisRenderer::new);
        EntityRenderers.register(WaterloggedEntities.ANGLERFISH,    AnglerfishRenderer::new);
        EntityRenderers.register(WaterloggedEntities.MAHI_MAHI,     MahiMahiRenderer::new);
        EntityRenderers.register(WaterloggedEntities.ORCA,           OrcaRenderer::new);
        EntityRenderers.register(WaterloggedEntities.RIGHT_WHALE,    RightWhaleRenderer::new);

        // Model layer definitions
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.STARFISH,       StarfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.ANEMONE,       AnemoneModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.URCHIN,        UrchinModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.FRY,          FryModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.SUNFISH,       SunfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.PELICAN,       PelicanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.SEA_SLUG,      SeaSlugModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.CRAB,          CrabModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.DONGFISH,      DongfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.CATFISH,       CatfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.SEAHORSE,      SeahorseModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.BUBBLE_EYE,    BubbleEyeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.ANOMALOCARIS,  AnomalocarisModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.ANGLERFISH,    AnglerfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.MAHI_MAHI,     MahiMahiModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.ORCA,           OrcaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WaterloggedEntityModelLayers.RIGHT_WHALE,    RightWhaleModel::getTexturedModelData);

        // Block entity renderers
        BlockEntityRenderers.register(WaterloggedEntities.STARFISH, StarfishBlockEntityRenderer::new);
        BlockEntityRenderers.register(WaterloggedEntities.ANEMONE, AnemoneBlockEntityRenderer::new);
        BlockEntityRenderers.register(WaterloggedEntities.URCHIN, UrchinBlockEntityRenderer::new);

        // Particles
        ParticleFactoryRegistry.getInstance().register(WaterloggedParticles.ALGAE, AlgaeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WaterloggedParticles.WORM, WormParticle.Factory::new);

        // Block color providers — tint roe and sea-slug-egg block models with parent colours
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) return -1;
            if (world.getBlockEntity(pos) instanceof RoeBlockEntity roe) {
                int argb = roe.getTintArgb();
                if (argb != -1) return argb;
            }
            return -1;
        }, WaterloggedBlocks.ROE);

        // Tint each egg cluster with the color of the parent that laid it.
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) return -1;
            if (world.getBlockEntity(pos) instanceof SeaSlugEggsBlockEntity eggs) {
                return SeaSlugColor.byId(eggs.getLayingParentColorId()).getArgb();
            }
            return -1;
        }, WaterloggedBlocks.SEA_SLUG_EGGS);

    }
}
