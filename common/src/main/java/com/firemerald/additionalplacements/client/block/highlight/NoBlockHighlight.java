package com.firemerald.additionalplacements.client.block.highlight;

import com.firemerald.additionalplacements.block.interfaces.IPlacementBlock;
import com.firemerald.additionalplacements.util.PlatformUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class NoBlockHighlight implements IBlockHighlight<IPlacementBlock<?>> {
    static {
        PlatformUtils.checkIsClient();
    }
    public static final NoBlockHighlight INSTANCE = new NoBlockHighlight();

    private NoBlockHighlight() {}

    @Override
    public void additionalplacements$renderHighlight(IPlacementBlock<?> block, PoseStack pose, MultiBufferSource bufferSource, Player player, BlockHitResult result, LevelRenderState renderState, DeltaTracker delta) {}

    @Override
    public void additionalplacements$renderPlacementHighlight(IPlacementBlock<?> block, PoseStack pose, VertexConsumer vertexConsumer, Player player, BlockHitResult result, DeltaTracker delta, float r, float g, float b, float a, float width) {}
}
