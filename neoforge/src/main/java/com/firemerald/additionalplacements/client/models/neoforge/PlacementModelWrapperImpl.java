package com.firemerald.additionalplacements.client.models.neoforge;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface PlacementModelWrapperImpl extends PlacementModelWrapper {
    @Override
    List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType);

    @Override
    default TriState useAmbientOcclusion(BlockState state, ModelData data, RenderType renderType) {
        return getVisualModel().useAmbientOcclusion(BlockModelUtils.getModeledState(state), data, renderType);
    }

    @Override
    default BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        getWrappedModel().applyTransform(transformType, poseStack, applyLeftHandTransform);
        return this;
    }

    @Override
    default ModelData getModelData(BlockAndTintGetter level, BlockPos pos, BlockState state, ModelData modelData) {
        return getVisualModel().getModelData(level, pos, BlockModelUtils.getModeledState(state), modelData);
    }

    @Override
    default TextureAtlasSprite getParticleIcon(ModelData data) {
        return getVisualModel().getParticleIcon(data);
    }

    @Override
    default ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        return getVisualModel().getRenderTypes(BlockModelUtils.getModeledState(state), rand, data);
    }

    @Override
    default List<RenderType> getRenderTypes(ItemStack itemStack) {
        return getVisualModel().getRenderTypes(itemStack);
    }

    @Override
    default List<BakedModel> getRenderPasses(ItemStack itemStack) {
        return getVisualModel().getRenderPasses(itemStack);
    }
}
