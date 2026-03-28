package com.firemerald.additionalplacements.client.models.forge;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PlacementModelWrapperImpl extends PlacementModelWrapper {
    @Override
    @NotNull
    List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType);

    @Override
    default boolean useAmbientOcclusion(@NotNull BlockState state) {
        return getWrappedModel().useAmbientOcclusion(BlockModelUtils.getModeledState(state));
    }

    @Override
    default boolean useAmbientOcclusion(@NotNull BlockState state, @NotNull RenderType renderType) {
        return getWrappedModel().useAmbientOcclusion(BlockModelUtils.getModeledState(state), renderType);
    }

    @Override
    default @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform) {
        getWrappedModel().applyTransform(transformType, poseStack, applyLeftHandTransform);
        return this;
    }

    @Override
    default @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        return getWrappedModel().getModelData(level, pos, BlockModelUtils.getModeledState(state), modelData);
    }

    @Override
    default @NotNull TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
        return getParticleModel().getParticleIcon(data);
    }

    @Override
    default @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return getWrappedModel().getRenderTypes(BlockModelUtils.getModeledState(state), rand, data);
    }

    @Override
    default @NotNull List<RenderType> getRenderTypes(@NotNull ItemStack itemStack, boolean fabulous) {
        return getWrappedModel().getRenderTypes(itemStack, fabulous);
    }

    @Override
    default @NotNull List<BakedModel> getRenderPasses(@NotNull ItemStack itemStack, boolean fabulous) {
        return getWrappedModel().getRenderPasses(itemStack, fabulous);
    }
}
