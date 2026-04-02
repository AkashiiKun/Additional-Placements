package com.firemerald.additionalplacements.block.interfaces;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.firemerald.additionalplacements.client.block.highlight.IBlockHighlight;
import com.firemerald.additionalplacements.client.block.highlight.NoBlockHighlight;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IFloorBlock<T extends Block> extends IPlacementBlock<T> {
	@Override
    default BlockState additionalplacements$transform(BlockState blockState, Function<Direction, Direction> transform) {
		Direction placing = additionalplacements$getPlacing(blockState);
		return placing == null ? blockState : additionalplacements$forPlacing(transform.apply(placing), blockState);
	}

	@Override
    default BlockState additionalplacements$getStateForPlacementImpl(BlockPlaceContext context, BlockState currentState) {
		return additionalplacements$forPlacing(additionalplacements$getPlacingDirection(context), currentState);
	}

	BlockState additionalplacements$forPlacing(Direction dir, BlockState blockState);

	Direction additionalplacements$getPlacing(BlockState blockState);

	default Direction additionalplacements$getPlacingDirection(BlockPlaceContext context) {
		return context.getClickedFace().getOpposite();
	}

	@Override
	default Supplier<? extends IBlockHighlight<?>> getBlockHighlight() {
		//PlatformUtils.checkIsClient(); check omitted for performance
		return () -> NoBlockHighlight.INSTANCE;
	}

    @Override
    default void additionalplacements$addPlacementTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.additionalplacements.vertical_placement"));
		tooltip.add(Component.translatable("tooltip.additionalplacements.ceiling_placement"));
	}
}