package com.firemerald.additionalplacements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.firemerald.additionalplacements.block.interfaces.IStairBlock.IVanillaStairBlock;
import com.firemerald.additionalplacements.block.stairs.AdditionalStairBlock;
import com.firemerald.additionalplacements.block.stairs.StairConnectionsType;
import com.firemerald.additionalplacements.block.stairs.common.CommonStairShapeState;
import com.firemerald.additionalplacements.block.stairs.vanilla.VanillaStairShapeState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(StairBlock.class)
public abstract class MixinStairBlock implements IVanillaStairBlock {
	private AdditionalStairBlock stairs;
	@Final
	@Shadow
	private BlockState baseState;

	private StairBlock asStair() {
		return (StairBlock) (Object) this;
	}

	@Override
	public void setOtherBlock(AdditionalStairBlock stairs) {
		this.stairs = stairs;
	}

	@Override
	public AdditionalStairBlock getOtherBlock() {
		return stairs;
	}

	@Override
	public boolean hasAdditionalStates() {
		return stairs != null;
	}

	@Override
	public boolean isThis(BlockState blockState) {
		return blockState.is(asStair()) || blockState.is(stairs);
	}

	@Override
	public BlockState getDefaultVanillaState(BlockState currentState) {
		return currentState.is(asStair()) ? currentState : stairs.copyProperties(currentState, asStair().defaultBlockState());
	}

	@Override
	public BlockState getDefaultAdditionalState(BlockState currentState) {
		return currentState.is(stairs) ? currentState : stairs.copyProperties(currentState, stairs.defaultBlockState());
	}

	@ModifyReturnValue(method = BlockMethods.GET_STATE_FOR_PLACEMENT_NAME, at = @At("RETURN"))
	private BlockState getStateForPlacement(BlockState original, @Local(argsOnly = true) BlockPlaceContext context) {
		if (this.hasAdditionalStates() && enablePlacement(context.getClickedPos(), context.getLevel(), context.getClickedFace(), context.getPlayer())) return getStateForPlacementImpl(context, original);
		else return original;
	}

	@Inject(method = BlockMethods.ROTATE_NAME, at = @At("HEAD"), cancellable = true)
	private void rotate(BlockState blockState, Rotation rotation, CallbackInfoReturnable<BlockState> ci) {
		if (this.hasAdditionalStates()) ci.setReturnValue(rotateImpl(blockState, rotation));
	}

	@Inject(method = BlockMethods.MIRROR_NAME, at = @At("HEAD"), cancellable = true)
	private void mirror(BlockState blockState, Mirror mirror, CallbackInfoReturnable<BlockState> ci) {
		if (this.hasAdditionalStates()) ci.setReturnValue(mirrorImpl(blockState, mirror));
	}

	@Inject(method = BlockMethods.UPDATE_SHAPE_NAME, at = @At("HEAD"), cancellable = true)
	private void updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor level, BlockPos pos, BlockPos otherPos, CallbackInfoReturnable<BlockState> ci) {
		if (this.hasAdditionalStates()) ci.setReturnValue(updateShapeImpl(state, direction, otherState, level, pos, otherPos));
	}

	@Override
	public BlockState getModelStateImpl() {
		return baseState;
	}

	@Override
	public BlockState getBlockStateInternal(CommonStairShapeState shapeState, BlockState currentState) {
		return stairs.getBlockStateInternal(shapeState, currentState);
	}

	@Override
	public CommonStairShapeState getShapeState(BlockState blockState) {
		return VanillaStairShapeState.toCommon(
				blockState.getValue(StairBlock.FACING),
				blockState.getValue(StairBlock.HALF),
				blockState.getValue(StairBlock.SHAPE));
	}

    @Override
	public StairConnectionsType connectionsType() {
		return stairs.connectionsType();
	}
}