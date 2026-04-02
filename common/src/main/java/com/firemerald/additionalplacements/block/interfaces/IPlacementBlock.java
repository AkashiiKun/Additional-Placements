package com.firemerald.additionalplacements.block.interfaces;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.firemerald.additionalplacements.client.block.highlight.IBlockHighlight;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.Nullable;

import com.firemerald.additionalplacements.common.IAPPlayer;
import com.firemerald.additionalplacements.config.APConfigs;
import com.firemerald.additionalplacements.generation.GenerationType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

public interface IPlacementBlock<T extends Block> extends ItemLike, IGenerationControl {
	T additionalplacements$getOtherBlock();

	default BlockState additionalplacements$rotateImpl(BlockState blockState, Rotation rotation) {
		return additionalplacements$transform(blockState, rotation::rotate);
	}

	default BlockState additionalplacements$mirrorImpl(BlockState blockState, Mirror mirror) {
		return additionalplacements$transform(blockState, mirror::mirror);
	}

	BlockState additionalplacements$transform(BlockState blockState, Function<Direction, Direction> transform);

	BlockState additionalplacements$getStateForPlacementImpl(BlockPlaceContext context, BlockState currentState);

	BlockState additionalplacements$updateShapeImpl(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos otherPos, BlockState otherState, RandomSource rand);

	default void additionalplacements$appendHoverTextImpl(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		if (APConfigs.common().showTooltip.get() && additionalplacements$getGenerationType().placementEnabled()) additionalplacements$addPlacementTooltip(stack, context, tooltip, flag);
	}

	void additionalplacements$addPlacementTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag);

	boolean additionalplacements$hasAdditionalStates();

	BlockState additionalplacements$getDefaultAdditionalState(BlockState currentState);

	BlockState additionalplacements$getDefaultVanillaState(BlockState currentState);

	boolean additionalplacements$isThis(BlockState blockState);

	Supplier<? extends IBlockHighlight<?>> getBlockHighlight();

	default boolean additionalplacements$enablePlacement(@Nullable Player player) {
		return additionalplacements$getGenerationType().placementEnabled() && (!(player instanceof IAPPlayer apPlayer) || apPlayer.additionalplacements$isPlacementEnabled());
	}

	default boolean additionalplacements$enablePlacement(BlockPos hit, Level level, Direction direction, Player player) {
		return additionalplacements$enablePlacement(player);
	}

	GenerationType<?, ?> additionalplacements$getGenerationType();

	@Override
    default boolean additionalplacements$generateAdditionalStates() {
		return true;
	}

	default boolean additionalplacements$canGenerateAdditionalStates() {
		return additionalplacements$generateAdditionalStates() && !additionalplacements$hasAdditionalStates();
	}
}