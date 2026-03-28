package com.firemerald.additionalplacements.block.interfaces.forge;

import com.firemerald.additionalplacements.block.AdditionalPlacementBlock;
import com.firemerald.additionalplacements.block.interfaces.IPlacementBlock;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.Nullable;

public interface IForgeAdditionalPlacementBlock<T extends Block> extends IPlacementBlock<T>, IForgeBlock {
    BlockState getModelState(BlockState thisBlockState);

    BlockRotation getRotation(BlockState state);

    @Override
    default float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return getModelState(state).getFriction(level, pos, entity);
    }

    @Override
    @Nullable
    default BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return getModelState(state).getBlockPathType(level, pos, mob);
    }

    @Override
    @Nullable
    default BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, BlockPathTypes originalType) {
        return getModelState(state).getAdjacentBlockPathType(level, pos, mob, originalType);
    }

    @Override
    default boolean isSlimeBlock(BlockState state) {
        return getModelState(state).isSlimeBlock();
    }

    @Override
    default boolean isStickyBlock(BlockState state) {
        return getModelState(state).isStickyBlock();
    }

    @Override
    default boolean canStickTo(BlockState state, BlockState other) {
        return getModelState(state).canStickTo(AdditionalPlacementBlock.getModelStateSafe(other));
    }

    @Override
    default int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return getModelState(state).getFlammability(level, pos, getRotation(state).unapply(direction));
    }

    @Override
    default boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return getModelState(state).isFlammable(level, pos, getRotation(state).unapply(direction));
    }

    @Override
    default void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction direction, @Nullable LivingEntity igniter) {
        getModelState(state).onCaughtFire(level, pos, direction == null ? null : getRotation(state).unapply(direction), igniter);
    }

    @Override
    default int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return getModelState(state).getFireSpreadSpeed(level, pos, getRotation(state).unapply(direction));
    }

    @Override
    default boolean isFireSource(BlockState state, LevelReader level, BlockPos pos, Direction direction) {
        return getModelState(state).isFireSource(level, pos, getRotation(state).unapply(direction));
    }

    @Override
    default boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return getModelState(state).canEntityDestroy(level, pos, entity);
    }

    @Override
    default boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return getModelState(state).canDropFromExplosion(level, pos, explosion);
    }

    @Override
    default void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        getModelState(state).onBlockExploded(level, pos, explosion);
    }

    @Override
    default boolean collisionExtendsVertically(BlockState state, BlockGetter level, BlockPos pos, Entity collidingEntity) {
        return getModelState(state).collisionExtendsVertically(level, pos, collidingEntity);
    }

    @Override
    default boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter level, BlockPos pos, FluidState fluidState) {
        return getModelState(state).shouldDisplayFluidOverlay(level, pos, fluidState);
    }

    @Override
    @Nullable
    default BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        //TODO transform context
        BlockState modelState = getModelState(state);
        BlockState modifiedState = modelState.getToolModifiedState(context, toolAction, simulate);
        return modifiedState == null ? null : AdditionalPlacementBlock.applyChanges(state, modelState, modifiedState);
    }

    @Override
    default boolean isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isScaffolding(modelState, level, pos, entity);
    }

    @Override
    default boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return getModelState(state).canRedstoneConnectTo(level, pos, direction == null ? null : getRotation(state).unapply(direction));
    }

    @Override
    default void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        additionalplacements$getOtherBlock().onBlockStateChange(level, pos, AdditionalPlacementBlock.getModelStateSafe(oldState), getModelState(newState));
    }

    @Override
    default boolean canBeHydrated(BlockState state, BlockGetter getter, BlockPos pos, FluidState fluid, BlockPos fluidPos) {
        return getModelState(state).canBeHydrated(getter, pos, fluid, fluidPos);
    }

    @Override
    default MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getMapColor(modelState, level, pos, defaultColor);
    }

    @Override
    default BlockState getAppearance(BlockState state, BlockAndTintGetter level, BlockPos pos, Direction side, @Nullable BlockState queryState, @Nullable BlockPos queryPos) {
        BlockState modelState = getModelState(state);
        BlockState newModelState = modelState.getAppearance(level, pos, getRotation(state).unapply(side), queryState, queryPos);
        return AdditionalPlacementBlock.applyChanges(state, modelState, newModelState);
    }

    @Override
    @Nullable
    default PushReaction getPistonPushReaction(BlockState state) {
        return getModelState(state).getPistonPushReaction();
    }
}
