package com.firemerald.additionalplacements.client.block.highlight;

import com.firemerald.additionalplacements.block.interfaces.IPlacementBlock;
import com.firemerald.additionalplacements.config.APConfigs;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public interface IBlockHighlight<T extends IPlacementBlock<?>> {
    float SQRT_2_INV = 0.70710678118654752440084436210485f;

    Quaternionf[] DIRECTION_TRANSFORMS = new Quaternionf[] {
            new Quaternionf(SQRT_2_INV, 0, 0, SQRT_2_INV), //DOWN
            new Quaternionf(-SQRT_2_INV, 0, 0, SQRT_2_INV), //UP
            new Quaternionf(0, 1, 0, 0), //NORTH
            new Quaternionf(0, 0, 0, 1), //SOUTH
            new Quaternionf(0, -SQRT_2_INV, 0, SQRT_2_INV), //WEST
            new Quaternionf(0, SQRT_2_INV, 0, SQRT_2_INV), //EAST
    };

    default void additionalplacements$renderHighlight(T block, PoseStack pose, MultiBufferSource bufferSource, Player player, BlockHitResult result, LevelRenderState renderState, DeltaTracker delta) {
        BlockPos hit = result.getBlockPos();
        if (block.additionalplacements$enablePlacement(hit, player.level(), result.getDirection(), player)) {
            pose.pushPose();
            double hitX = hit.getX();
            double hitY = hit.getY();
            double hitZ = hit.getZ();
            switch (result.getDirection()) {
                case WEST:
                    hitX = result.getLocation().x - 1.005;
                    break;
                case EAST:
                    hitX = result.getLocation().x + .005;
                    break;
                case DOWN:
                    hitY = result.getLocation().y - 1.005;
                    break;
                case UP:
                    hitY = result.getLocation().y + .005;
                    break;
                case NORTH:
                    hitZ = result.getLocation().z - 1.005;
                    break;
                case SOUTH:
                    hitZ = result.getLocation().z + .005;
                    break;
                default:
            }
            Vec3 pos = renderState.cameraRenderState.pos;
            pose.translate(hitX - pos.x + .5, hitY - pos.y + .5, hitZ - pos.z + .5);

            boolean highContrast = renderState.blockOutlineRenderState.highContrast();
            float lineWidth = Minecraft.getInstance().getWindow().getAppropriateLineWidth();
            float[] previewColor;
            if (highContrast) {
                float[] backgroundColor = APConfigs.client().previewColorHCB();
                if (backgroundColor[3] > 0) additionalplacements$renderPlacementPreview(block, pose, bufferSource.getBuffer(RenderTypes.secondaryBlockOutline()), player, result, delta, backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3], 7f);
                previewColor = APConfigs.client().previewColorHC();
            } else previewColor = APConfigs.client().previewColor();
            if (previewColor[3] > 0) additionalplacements$renderPlacementPreview(block, pose, bufferSource.getBuffer(RenderTypes.lines()), player, result, delta, previewColor[0], previewColor[1], previewColor[2], previewColor[3], lineWidth);
            pose.mulPose(DIRECTION_TRANSFORMS[result.getDirection().ordinal()]);
            float[] gridColor;
            if (highContrast) {
                float[] backgroundColor = APConfigs.client().gridColorHCB();
                if (backgroundColor[3] > 0) additionalplacements$renderPlacementHighlight(block, pose, bufferSource.getBuffer(RenderTypes.secondaryBlockOutline()), player, result, delta, backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3], 7f);
                gridColor = APConfigs.client().gridColorHC();
            } else gridColor = APConfigs.client().gridColor();
            if (gridColor[3] > 0) additionalplacements$renderPlacementHighlight(block, pose, bufferSource.getBuffer(RenderTypes.lines()), player, result, delta, gridColor[0], gridColor[1], gridColor[2], highContrast ? 0.4f : gridColor[3], lineWidth);
            pose.popPose();
        }
    }

    default void additionalplacements$renderPlacementPreview(T block, PoseStack pose, VertexConsumer vertexConsumer, Player player, BlockHitResult result, DeltaTracker delta, float r, float g, float b, float a, float width) {}

    void additionalplacements$renderPlacementHighlight(T block, PoseStack pose, VertexConsumer vertexConsumer, Player player, BlockHitResult result, DeltaTracker delta, float r, float g, float b, float a, float width);
}
