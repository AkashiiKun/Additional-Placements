package com.firemerald.additionalplacements.client.block.highlight;

import com.firemerald.additionalplacements.block.interfaces.ISlabBlock;
import com.firemerald.additionalplacements.client.BlockHighlightHelper;
import com.firemerald.additionalplacements.util.PlatformUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class SlabBlockHighlight implements IBlockHighlight<ISlabBlock<?>> {
    static {
        PlatformUtils.checkIsClient();
    }
    public static final SlabBlockHighlight INSTANCE = new SlabBlockHighlight();

    private SlabBlockHighlight() {}

    public static final float OUTER_EDGE = .5f;
    public static final float INNER_EDGE = .25f;

    @Override
    public void additionalplacements$renderPlacementHighlight(ISlabBlock<?> block, PoseStack pose, VertexConsumer vertexConsumer, Player player, BlockHitResult result, DeltaTracker delta, float r, float g, float b, float a) {
        PoseStack.Pose lastPose = pose.last();

        //outer box
        BlockHighlightHelper.lineCenteredSquare(vertexConsumer, lastPose, -OUTER_EDGE, r, g, b, a,
                OUTER_EDGE);

        //inner box
        BlockHighlightHelper.lineCenteredSquare(vertexConsumer, lastPose, -OUTER_EDGE, r, g, b, a,
                INNER_EDGE);

        //diagonals
        BlockHighlightHelper.lineAxisDiagonal(vertexConsumer, lastPose, -OUTER_EDGE, r, g, b, a,
                INNER_EDGE, OUTER_EDGE);
    }
}
