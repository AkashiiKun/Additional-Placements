package com.firemerald.additionalplacements.client.block.highlight;

import com.firemerald.additionalplacements.block.interfaces.IStairBlock;
import com.firemerald.additionalplacements.client.BlockHighlightHelper;
import com.firemerald.additionalplacements.util.ComplexFacing;
import com.firemerald.additionalplacements.util.PlatformUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Matrix4f;

public class StairsBlockHighlight implements IBlockHighlight<IStairBlock<?>> {
    static {
        PlatformUtils.checkIsClient();
    }
    public static final StairsBlockHighlight INSTANCE = new StairsBlockHighlight();

    private StairsBlockHighlight() {}

    public static final float ARROW_OFFSET = -0.4375f;
    public static final float ARROW_OUTER = 0.375f;
    public static final float ARROW_INNER = 0.125f;

    @Override
    public void additionalplacements$renderPlacementPreview(IStairBlock<?> block, PoseStack pose, VertexConsumer vertexConsumer, Player player, BlockHitResult result, DeltaTracker delta, float r, float g, float b, float a, float width) {
        if (!block.additionalplacements$connectionsType().allowFlipped) return;
        ComplexFacing facing = block.additionalplacements$getFacing(result.getDirection(),
                (float) (result.getLocation().x - result.getBlockPos().getX() - .5),
                (float) (result.getLocation().y - result.getBlockPos().getY() - .5),
                (float) (result.getLocation().z - result.getBlockPos().getZ() - .5));
        //z is up
        //y is forward
        //x is right
        pose.pushPose();
        pose.mulPose(new Matrix4f(
                facing.right  .getStepX(), facing.right  .getStepY(), facing.right  .getStepZ(), 0,
                facing.forward.getStepX(), facing.forward.getStepY(), facing.forward.getStepZ(), 0,
                facing.up     .getStepX(), facing.up     .getStepY(), facing.up     .getStepZ(), 0,
                0, 0, 0, 1
        ));
        PoseStack.Pose lastPose = pose.last();
        BlockHighlightHelper.lineLoop(vertexConsumer, lastPose, ARROW_OFFSET, r, g, b, a, width,
                0          ,  ARROW_OUTER,
                ARROW_OUTER,  0          ,
                ARROW_INNER,  0          ,
                ARROW_INNER, -ARROW_OUTER,
                -ARROW_INNER, -ARROW_OUTER,
                -ARROW_INNER,  0          ,
                -ARROW_OUTER,  0          );
        pose.popPose();
    }

    public static float OUTER_EDGE = .5f;
    public static float INNER_EDGE = .25f;

    @Override
    public void additionalplacements$renderPlacementHighlight(IStairBlock<?> block, PoseStack pose, VertexConsumer vertexConsumer, Player player, BlockHitResult result, DeltaTracker delta, float r, float g, float b, float a, float width) {
        PoseStack.Pose lastPose = pose.last();

        //outer box
        BlockHighlightHelper.lineCenteredSquare(vertexConsumer, lastPose, -OUTER_EDGE, r, g, b, a,
                OUTER_EDGE,
                width);

        if (block.additionalplacements$connectionsType().allowFlipped) {
            //inner edges
            BlockHighlightHelper.lineCenteredGrid(vertexConsumer, lastPose, -OUTER_EDGE, r, g, b, a,
                    INNER_EDGE, OUTER_EDGE,
                    width);

            //middle cross
            BlockHighlightHelper.lineCenteredCross(vertexConsumer, lastPose, -OUTER_EDGE, r, g, b, a,
                    OUTER_EDGE,
                    width);
        } else {
            //corners
            BlockHighlightHelper.lineOctal(vertexConsumer, lastPose, -OUTER_EDGE, r, g, b, a,
                    INNER_EDGE, INNER_EDGE,
                    OUTER_EDGE, INNER_EDGE,
                    width);

            //middle cross
            BlockHighlightHelper.lineCenteredCross(vertexConsumer, lastPose, -OUTER_EDGE, r, g, b, a,
                    INNER_EDGE,
                    width);
        }
    }
}
