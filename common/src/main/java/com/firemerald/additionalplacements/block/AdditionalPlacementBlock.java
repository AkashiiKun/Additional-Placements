package com.firemerald.additionalplacements.block;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Triple;

import com.firemerald.additionalplacements.block.interfaces.IPlacementBlock;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import com.firemerald.additionalplacements.common.AdditionalPlacementsBlockTags;
import com.firemerald.additionalplacements.util.BlockRotation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AdditionalPlacementBlock<T extends Block> extends Block implements IPlacementBlock<T> {
	public static BlockState getModelStateSafe(BlockState worldState) {
		if (worldState.getBlock() instanceof AdditionalPlacementBlock<?> block) return block.getModelState(worldState);
		else return worldState;
	}

	private static final List<Property<?>> copyPropsStatic = new ArrayList<>();
	public final T parentBlock;
	private final Property<?>[] copyProps;

	public AdditionalPlacementBlock(T parentBlock) {
		super(theHack(parentBlock));
		this.copyProps = copyPropsStatic.toArray(Property[]::new);
		copyPropsStatic.clear();
		this.parentBlock = parentBlock;
	}

	@Override
	public T additionalplacements$getOtherBlock() {
		return parentBlock;
	}

	public static Properties theHack(Block parentBlock) {
		copyPropsStatic.addAll(parentBlock.defaultBlockState().getProperties());
		return Properties.copy(parentBlock);
	}

	public boolean hasCustomColors() {
		return false;
	}

	public Property<?>[] getCopyProps() {
		return copyProps;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BlockState copyProperties(BlockState from, BlockState to) {
		for (Property prop : copyProps) to = to.setValue(prop, from.getValue(prop));
		return to;
	}

	public boolean isValidProperty(Property<?> prop) {
		return true;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		Set<Property<?>> invalid = new HashSet<>();
		copyPropsStatic.forEach(prop -> {
			if (isValidProperty(prop)) builder.add(prop);
			else invalid.add(prop);
		});
		copyPropsStatic.removeAll(invalid);
	}

	@Override
	public @NotNull Item asItem() {
		return parentBlock.asItem();
	}

	@Override
	public @NotNull String getDescriptionId() {
		return parentBlock.getDescriptionId();
	}

	public BlockState getOtherBlockState() {
		return additionalplacements$getOtherBlock().defaultBlockState();
	}

	public BlockState getModelState(BlockState worldState) {
		return withUnrotatedPlacement(worldState, copyProperties(worldState, getOtherBlockState()));
	}

	public abstract BlockState withUnrotatedPlacement(BlockState worldState, BlockState modelState);

	@Override
	@Deprecated
	@SuppressWarnings("deprecation")
	public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder params) {
		return parentBlock.getDrops(this.getModelState(state), params);
	}

	@Override
	@Deprecated
	public @NotNull ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return parentBlock.getCloneItemStack(level, pos, state);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().animateTick(modelState, level, pos, random);
	}

	@Override
	public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float fallDistance) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().fallOn(level, modelState, pos, entity, fallDistance);
	}

	@Override
	public void updateEntityAfterFallOn(@NotNull BlockGetter level, @NotNull Entity entity) {
		additionalplacements$getOtherBlock().updateEntityAfterFallOn(level, entity);
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().stepOn(level, pos, modelState, entity);
	}

	@Override
	public void handlePrecipitation(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Biome.Precipitation precipitation) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().handlePrecipitation(modelState, level, pos, precipitation);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void attack(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().attack(modelState, level, pos, player);
	}

	@Override
	public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().destroy(level, pos, modelState);
	}

	@Override
	@Deprecated
	public float getExplosionResistance() {
		return additionalplacements$getOtherBlock().getExplosionResistance();
	}

	@Override
	@Deprecated
	@SuppressWarnings("deprecation")
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().onPlace(modelState, level, pos, getModelStateSafe(oldState), movedByPiston);
		applyChanges(state, modelState, level, pos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().onRemove(modelState, level, pos, getModelStateSafe(newState), movedByPiston);
	}

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state) {
		BlockState modelState = getModelState(state);
		return modelState.getBlock().isRandomlyTicking(modelState);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().randomTick(modelState, level, pos, rand);
		applyChanges(state, modelState, level, pos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
		BlockState modelState = getModelState(state);
		modelState.getBlock().tick(modelState, level, pos, rand);
		applyChanges(state, modelState, level, pos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
		BlockState modelState = getModelState(state);
		InteractionResult res = modelState.getBlock().use(modelState, level, pos, player, hand, hitResult);
		applyChanges(state, modelState, level, pos);
		return res;
	}

	public static void applyChanges(BlockState oldState, BlockState modelState, Level level, BlockPos pos) {
		BlockState currentState = level.getBlockState(pos);
		BlockState newState = applyChanges(oldState, modelState, currentState);
		if (newState != currentState) level.setBlock(pos, newState, 3);
	}

	public static BlockState applyChanges(BlockState ourLastState, BlockState modelState, BlockState newModelState) {
		if (newModelState.getBlock() == ourLastState.getBlock()) { //block unchanged
 			return newModelState;
		} else if (modelState == newModelState) { //state unchanged
			return ourLastState;
		} else if (modelState.getBlock() == newModelState.getBlock()) { //only changed properties
			BlockState newState = ourLastState;
			for (Property<?> property : modelState.getProperties()) {
				if (ourLastState.hasProperty(property)) {
					if (modelState.getValue(property) != newModelState.getValue(property)) {
						newState = copy(property, newModelState, newState);
					}
				}
			}
			return newState;
		} else if (newModelState.getBlock() instanceof IPlacementBlock<?> placement && placement.additionalplacements$hasAdditionalStates()) { //new placement block
			BlockState newState = placement.additionalplacements$getDefaultAdditionalState(newModelState);
			for (Property<?> property : newState.getProperties()) {
				if (newModelState.hasProperty(property)) newState = copy(property, newModelState, newState);
				else if (ourLastState.hasProperty(property)) newState = copy(property, ourLastState, newState);
			}
			return newState;
		}
		return newModelState;
	}

	public static <V extends Comparable<V>> BlockState copy(Property<V> property, BlockState from, BlockState to) {
		return to.setValue(property, from.getValue(property));
	}

	@Override
	public void wasExploded(@NotNull Level level, @NotNull BlockPos pos, @NotNull Explosion explosion) {
		additionalplacements$getOtherBlock().wasExploded(level, pos, explosion);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType pathType) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Nullable
	public Triple<Block, Collection<TagKey<Block>>, Collection<TagKey<Block>>> checkTagMismatch() {
		Set<TagKey<Block>> desiredTags = getDesiredTags();
		Set<TagKey<Block>> hasTags = this.builtInRegistryHolder().tags().collect(Collectors.toSet());
		List<TagKey<Block>> hasTagsList = new ArrayList<>(hasTags);
		List<TagKey<Block>> desiredTagsList = new ArrayList<>(desiredTags);
		hasTagsList.removeAll(desiredTags);
		desiredTagsList.removeAll(hasTags);
		if (!hasTagsList.isEmpty() || !desiredTagsList.isEmpty()) return Triple.of(this, desiredTagsList, hasTagsList);
		else return null;
	}

	@SuppressWarnings("deprecation")
	public Set<TagKey<Block>> getDesiredTags() {
		return modifyTags(parentBlock.builtInRegistryHolder().tags());
	}

	public abstract String getTagTypeName();

	public abstract String getTagTypeNamePlural();

	public Set<TagKey<Block>> modifyTags(Stream<TagKey<Block>> tags) {
		return AdditionalPlacementsBlockTags.remap(tags, getTagTypeName(), getTagTypeNamePlural());
	}

	@Override
	public boolean additionalplacements$hasAdditionalStates() {
		return true;
	}

	@Override
	public boolean additionalplacements$isThis(BlockState blockState) {
		return blockState.is(this) || blockState.is(parentBlock);
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		return additionalplacements$getStateForPlacementImpl(context, this.defaultBlockState());
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull BlockState rotate(@NotNull BlockState blockState, @NotNull Rotation rotation) {
		return additionalplacements$rotateImpl(blockState, rotation);
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull BlockState mirror(@NotNull BlockState blockState, @NotNull Mirror mirror) {
		return additionalplacements$mirrorImpl(blockState, mirror);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		additionalplacements$appendHoverTextImpl(stack, level, tooltip, flag);
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState otherState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos otherPos) {
		return additionalplacements$updateShapeImpl(state, direction, otherState, level, pos, otherPos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull FluidState getFluidState(@NotNull BlockState state) {
		BlockState modelState = getModelState(state);
		return modelState.getBlock().getFluidState(modelState);
	}

	@Override
    @SuppressWarnings("deprecation")
	public boolean skipRendering(@NotNull BlockState thisState, @NotNull BlockState adjacentState, @NotNull Direction dir) {
		//TODO make better?
		return false;
	}

	@Override
	public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		BlockState modelState = getModelState(state);
		return modelState.getBlock().propagatesSkylightDown(modelState, level, pos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		BlockState modelState = getModelState(state);
		return modelState.getBlock().getShadeBrightness(modelState, level, pos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isSignalSource(@NotNull BlockState state) {
		BlockState modelState = getModelState(state);
		return modelState.getBlock().isSignalSource(modelState);
	}

	public abstract boolean rotatesLogic(BlockState state);

	public abstract boolean rotatesTexture(BlockState state);

	public abstract boolean rotatesModel(BlockState state);

	public abstract BlockRotation getRotation(BlockState state);

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		if (rotatesModel(state))
			return getRotation(state).applyBlockSpace(getModelState(state).getShape(level, pos, context));
		else
			return getShapeInternal(state, level, pos, context);
	}

	public abstract VoxelShape getShapeInternal(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context);

	@Override
	public boolean additionalplacements$canGenerateAdditionalStates() {
		return false;
	}

	@Environment(EnvType.CLIENT)
	public abstract ResourceLocation getBaseModelPrefix();

	@Environment(EnvType.CLIENT)
	public abstract StateModelDefinition getModelDefinition(BlockState state);
}
