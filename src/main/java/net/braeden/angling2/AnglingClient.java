package net.braeden.angling2;

import net.braeden.angling2.particle.AlgaeParticle;
import net.braeden.angling2.particle.WormParticle;
import net.braeden.angling2.entity.client.*;
import net.braeden.angling2.entity.client.AnemoneBlockEntityRenderer;
import net.braeden.angling2.entity.client.StarfishBlockEntityRenderer;
import net.braeden.angling2.entity.client.UrchinBlockEntityRenderer;
import net.braeden.angling2.block.AnglingBlocks;
import net.braeden.angling2.entity.AnglingEntities;
import net.braeden.angling2.particle.AnglingParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class AnglingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Block render layers
        BlockRenderLayerMap.putBlock(AnglingBlocks.ROE, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(AnglingBlocks.DUCKWEED, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(AnglingBlocks.OYSTERS, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(AnglingBlocks.CLAM, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(AnglingBlocks.SEA_SLUG_EGGS, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(AnglingBlocks.PAPYRUS, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(AnglingBlocks.SARGASSUM, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(AnglingBlocks.ALGAE, ChunkSectionLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(AnglingBlocks.AQUARIUM_GLASS, ChunkSectionLayer.CUTOUT);

        // Entity renderers
        EntityRenderers.register(AnglingEntities.FRY,          FryRenderer::new);
        EntityRenderers.register(AnglingEntities.SUNFISH,       SunfishRenderer::new);
        EntityRenderers.register(AnglingEntities.PELICAN,       PelicanRenderer::new);
        EntityRenderers.register(AnglingEntities.SEA_SLUG,      SeaSlugRenderer::new);
        EntityRenderers.register(AnglingEntities.CRAB,          CrabRenderer::new);
        EntityRenderers.register(AnglingEntities.DONGFISH,      DongfishRenderer::new);
        EntityRenderers.register(AnglingEntities.CATFISH,       CatfishRenderer::new);
        EntityRenderers.register(AnglingEntities.SEAHORSE,      SeahorseRenderer::new);
        EntityRenderers.register(AnglingEntities.BUBBLE_EYE,    BubbleEyeRenderer::new);
        EntityRenderers.register(AnglingEntities.ANOMALOCARIS,  AnomalocarisRenderer::new);
        EntityRenderers.register(AnglingEntities.ANGLERFISH,    AnglerfishRenderer::new);
        EntityRenderers.register(AnglingEntities.MAHI_MAHI,     MahiMahiRenderer::new);

        // Model layer definitions
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.STARFISH,       StarfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.ANEMONE,       AnemoneModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.FRY,          FryModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.SUNFISH,       SunfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.PELICAN,       PelicanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.SEA_SLUG,      SeaSlugModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.CRAB,          CrabModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.DONGFISH,      DongfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.CATFISH,       CatfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.SEAHORSE,      SeahorseModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.BUBBLE_EYE,    BubbleEyeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.ANOMALOCARIS,  AnomalocarisModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.ANGLERFISH,    AnglerfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AnglingEntityModelLayers.MAHI_MAHI,     MahiMahiModel::getTexturedModelData);

        // Block entity renderers
        BlockEntityRenderers.register(AnglingEntities.STARFISH, StarfishBlockEntityRenderer::new);
        BlockEntityRenderers.register(AnglingEntities.ANEMONE, AnemoneBlockEntityRenderer::new);
        BlockEntityRenderers.register(AnglingEntities.URCHIN, UrchinBlockEntityRenderer::new);

        // Particles
        ParticleFactoryRegistry.getInstance().register(AnglingParticles.ALGAE, AlgaeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AnglingParticles.WORM, WormParticle.Factory::new);

    }
}
