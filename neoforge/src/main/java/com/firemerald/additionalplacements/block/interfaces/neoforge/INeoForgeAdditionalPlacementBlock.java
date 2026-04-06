package com.firemerald.additionalplacements.block.interfaces.neoforge;

import com.firemerald.additionalplacements.block.AdditionalPlacementBlock;
import com.firemerald.additionalplacements.block.interfaces.IPlacementBlock;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface INeoForgeAdditionalPlacementBlock<T extends Block> extends IPlacementBlock<T>, IBlockExtension {
    BlockState getModelState(BlockState thisBlockState);

    BlockRotation getRotation(BlockState state);

    @Override
    default float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getFriction(modelState, level, pos, entity);
    }

    @Override
    default int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getLightEmission(modelState, level, pos);
    }

    @Override
    default boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isLadder(modelState, level, pos, entity);
    }

    @Override
    default boolean makesOpenTrapdoorAboveClimbable(BlockState state, LevelReader level, BlockPos pos, BlockState trapdoorState) {
        BlockState modelState = getModelState(state);
        return state.getBlock().makesOpenTrapdoorAboveClimbable(modelState, level, pos, trapdoorState);
    }

    @Override
    default boolean isBurning(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isBurning(modelState, level, pos);
    }

    @Override
    default boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().canHarvestBlock(modelState, level, pos, player);
    }

    @Override
    default boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().onDestroyedByPlayer(modelState, level, pos, player, willHarvest, fluid);
    }

    @Override
    default void onDestroyedByPushReaction(BlockState state, Level level, BlockPos pos, Direction pushDirection, FluidState fluid) {
        BlockState modelState = getModelState(state);
        modelState.getBlock().onDestroyedByPushReaction(modelState, level, pos, pushDirection, fluid);
    }

    @Override
    default boolean isBed(BlockState state, BlockGetter level, BlockPos pos, LivingEntity sleeper) {
        return false;
    }

    @Override
    default Optional<ServerPlayer.RespawnPosAngle> getRespawnPosition(BlockState state, EntityType<?> type, LevelReader levelReader, BlockPos pos, float orientation) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getRespawnPosition(modelState, type, levelReader, pos, orientation);
    }

    @Override
    default float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getExplosionResistance(modelState, level, pos, explosion);
    }

    @Override
    default ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getCloneItemStack(level, pos, modelState, includeData, player);
    }

    @Override
    default boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        BlockState modelState1 = AdditionalPlacementBlock.getModelStateSafe(state1);
        BlockState modelState2 = AdditionalPlacementBlock.getModelStateSafe(state2);
        return modelState1.getBlock().addLandingEffects(modelState1, level, pos, modelState2, entity, numberOfParticles);
    }

    @Override
    default boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().addRunningEffects(modelState, level, pos, entity);
    }

    @Override
    default TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, BlockState plant) {
        BlockState modelState = getModelState(state);
        Direction newFacing = getRotation(state).unapply(facing);
        return modelState.getBlock().canSustainPlant(modelState, level, pos, newFacing, plant);
    }

    @Override
    default boolean onTreeGrow(BlockState state, LevelReader level, BiConsumer<BlockPos, BlockState> placeFunction, RandomSource randomSource, BlockPos pos, TreeConfiguration config) {
        BlockState modelState = getModelState(state);
        BiConsumer<BlockPos, BlockState> newPlaceFunction = (placePos, placeState) -> {
            if (placePos.equals(pos)) placeState = AdditionalPlacementBlock.applyChanges(state, modelState, placeState);
            placeFunction.accept(placePos, placeState);
        };
        return modelState.getBlock().onTreeGrow(modelState, level, newPlaceFunction, randomSource, pos, config);
    }

    @Override
    default boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isFertile(modelState, level, pos);
    }

    @Override
    default boolean isConduitFrame(BlockState state, LevelReader level, BlockPos pos, BlockPos conduit) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isConduitFrame(modelState, level, pos, conduit);
    }

    @Override
    default boolean isPortalFrame(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isPortalFrame(modelState, level, pos);
    }

    @Override
    default int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, ItemStack tool) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getExpDrop(modelState, level, pos, blockEntity, breaker, tool);
    }

    @Override
    default BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return additionalplacements$rotateImpl(state, direction);
    }

    @Override
    default float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getEnchantPowerBonus(modelState, level, pos);
    }

    @Override
    default void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        BlockState modelState = getModelState(state);
        modelState.getBlock().onNeighborChange(modelState, level, pos, neighbor);
    }

    @Override
    default boolean shouldCheckWeakPower(BlockState state, SignalGetter level, BlockPos pos, Direction side) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().shouldCheckWeakPower(modelState, level, pos, getRotation(state).unapply(side));
    }

    @Override
    default boolean getWeakChanges(BlockState state, LevelReader level, BlockPos pos) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getWeakChanges(modelState, level, pos);
    }

    @Override
    default SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getSoundType(modelState, level, pos, entity);
    }

    @Override
    default @Nullable Integer getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getBeaconColorMultiplier(modelState, level, pos, beaconPos);
    }

    @Override
    @Nullable
    default PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getBlockPathType(modelState, level, pos, mob);
    }

    @Override
    @Nullable
    default PathType getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, PathType originalType) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getAdjacentBlockPathType(modelState, level, pos, mob, originalType);
    }

    @Override
    default boolean isSlimeBlock(BlockState state) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isSlimeBlock(state);
    }

    @Override
    default boolean isStickyBlock(BlockState state) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isStickyBlock(state);
    }

    @Override
    default boolean canStickTo(BlockState state, BlockState other) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().canStickTo(modelState, AdditionalPlacementBlock.getModelStateSafe(other));
    }

    @Override
    default int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getFlammability(modelState, level, pos, getRotation(state).unapply(direction));
    }

    @Override
    default boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isFlammable(modelState, level, pos, getRotation(state).unapply(direction));
    }

    @Override
    default void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction direction, @Nullable LivingEntity igniter) {
        BlockState modelState = getModelState(state);
        modelState.getBlock().onCaughtFire(modelState, level, pos, direction == null ? null : getRotation(state).unapply(direction), igniter);
    }

    @Override
    default int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getFireSpreadSpeed(modelState, level, pos, getRotation(state).unapply(direction));
    }

    @Override
    default boolean isFireSource(BlockState state, LevelReader level, BlockPos pos, Direction direction) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isFireSource(modelState, level, pos, getRotation(state).unapply(direction));
    }

    @Override
    default boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().canEntityDestroy(modelState, level, pos, entity);
    }

    @Override
    default boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().canDropFromExplosion(modelState, level, pos, explosion);
    }

    @Override
    default void onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion) {
        BlockState modelState = getModelState(state);
        modelState.getBlock().onBlockExploded(modelState, level, pos, explosion);
    }

    @Override
    default boolean collisionExtendsVertically(BlockState state, BlockGetter level, BlockPos pos, Entity collidingEntity) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().collisionExtendsVertically(modelState, level, pos, collidingEntity);
    }

    @Override
    default boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter level, BlockPos pos, FluidState fluidState) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().shouldDisplayFluidOverlay(modelState, level, pos, fluidState);
    }

    @Override
    @Nullable
    default BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        //TODO transform context
        BlockState modelState = getModelState(state);
        BlockState modifiedState = modelState.getToolModifiedState(context, itemAbility, simulate);
        return modifiedState == null ? null : AdditionalPlacementBlock.applyChanges(modelState, modelState, modifiedState);
    }

    @Override
    default boolean isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().isScaffolding(modelState, level, pos, entity);
    }

    @Override
    default boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().canConnectRedstone(modelState, level, pos, direction == null ? null : getRotation(state).unapply(direction));
    }

    @Override
    default void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        additionalplacements$getOtherBlock().onBlockStateChange(level, pos, AdditionalPlacementBlock.getModelStateSafe(oldState), getModelState(newState));
    }

    @Override
    default boolean canBeHydrated(BlockState state, BlockGetter getter, BlockPos pos, FluidState fluid, BlockPos fluidPos) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().canBeHydrated(modelState, getter, pos, fluid, fluidPos);
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
        return AdditionalPlacementBlock.applyChanges(modelState, modelState, newModelState);
    }

    @Override
    @Nullable
    default PushReaction getPistonPushReaction(BlockState state) {
        BlockState modelState = getModelState(state);
        return modelState.getBlock().getPistonPushReaction(state);
    }
}
