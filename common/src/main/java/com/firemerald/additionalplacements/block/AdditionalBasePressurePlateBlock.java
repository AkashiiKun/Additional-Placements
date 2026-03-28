package com.firemerald.additionalplacements.block;

import com.firemerald.additionalplacements.block.interfaces.IBasePressurePlateBlock;
import com.firemerald.additionalplacements.block.interfaces.IBasePressurePlateBlockExtensions;
import com.firemerald.additionalplacements.client.models.definitions.PressurePlateModels;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AdditionalBasePressurePlateBlock<T extends BasePressurePlateBlock> extends AdditionalFloorBlock<T> implements IBasePressurePlateBlock<T> {
	public static final VoxelShape[] AABBS = {
			Block.box(1, 15, 1, 15, 16, 15),
			Block.box(1, 1, 0, 15, 15, 1),
			Block.box(1, 1, 15, 15, 15, 16),
			Block.box(0, 1, 1, 1, 15, 15),
			Block.box(15, 1, 1, 16, 15, 15)
	};
	public static final VoxelShape[] PRESSED_AABBS = {
			Block.box(1, 15.5, 1, 15, 16, 15),
			Block.box(1, 1, 0, 15, 15, .5),
			Block.box(1, 1, 15.5, 15, 15, 16),
			Block.box(0, 1, 1, .5, 15, 15),
			Block.box(15.5, 1, 1, 16, 15, 15)
	};
	public static final AABB[] TOUCH_AABBS = {
			new AABB(0.125, 0.75, 0.125, 0.875, 1, 0.875),
			new AABB(0.125, 0.125, 0, 0.875, 0.875, 0.25),
			new AABB(0.125, 0.125, 0.75, 0.875, 0.875, 1),
			new AABB(0, 0.125, 0.125, 0.25, 0.875, 0.875),
			new AABB(0.75, 0.125, 0.125, 1, 0.875, 0.875)
	};

	public final IBasePressurePlateBlockExtensions plateMethods;

	@SuppressWarnings("unchecked")
	public AdditionalBasePressurePlateBlock(T plate) {
		super(plate);
		this.registerDefaultState(copyProperties(getOtherBlockState(), this.stateDefinition.any()).setValue(PLACING, Direction.NORTH));
		((IVanillaBasePressurePlateBlock<AdditionalBasePressurePlateBlock<T>>) plate).additionalplacements$setOtherBlock(this);
		plateMethods = (IBasePressurePlateBlockExtensions) plate;
	}

	@Override
	public VoxelShape getShapeInternal(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext) {
		return plateMethods.additionalplacements$getSignalForStatePublic(state) > 0 ? PRESSED_AABBS[state.getValue(PLACING).ordinal() - 1] : AABBS[state.getValue(PLACING).ordinal() - 1];
	}

	@Override
	public String getTagTypeName() {
		return "pressure_plate";
	}

	@Override
	public String getTagTypeNamePlural() {
		return "pressure_plates";
	}

	@Override
	public BlockState additionalplacements$updateShapeImpl(BlockState thisState, Direction updatedDirection, BlockState otherState, LevelAccessor level, BlockPos thisPos, BlockPos otherPos) {
		return !thisState.canSurvive(level, thisPos) ? Blocks.AIR.defaultBlockState() : thisState;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean canSurvive(BlockState state, @NotNull LevelReader level, BlockPos pos) {
		Direction dir = state.getValue(PLACING);
		BlockPos blockpos = pos.relative(dir);
		return canSupportRigidBlock(level, blockpos, dir.getOpposite()) || canSupportCenter(level, blockpos, dir.getOpposite());
	}

	public static boolean canSupportRigidBlock(BlockGetter level, BlockPos pos, Direction dir) {
		return level.getBlockState(pos).isFaceSturdy(level, pos, dir, SupportType.RIGID);
	}

	protected abstract int getSignalStrength(Level level, BlockPos pos);

	@Override
	public boolean isPossibleToRespawnInThis(@NotNull BlockState state) {
		return parentBlock.isPossibleToRespawnInThis(this.additionalplacements$getDefaultVanillaState(state));
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
		int strength = plateMethods.additionalplacements$getSignalForStatePublic(state);
		if (strength > 0) this.checkPressed(null, level, pos, state, strength);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void entityInside(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (!level.isClientSide) {
			int strength = plateMethods.additionalplacements$getSignalForStatePublic(state);
			if (strength == 0) this.checkPressed(entity, level, pos, state, strength);
		}
	}

	protected void checkPressed(@Nullable Entity entity, Level level, BlockPos pos, BlockState state, int oldStrength) {
		int strength = this.getSignalStrength(level, pos);
		boolean prevPowered = oldStrength > 0;
		boolean powered = strength > 0;
		if (oldStrength != strength) {
			BlockState blockstate = plateMethods.additionalplacements$setSignalForStatePublic(state, strength);
			level.setBlock(pos, blockstate, 2);
			this.updateNeighbours(level, pos, state);
			level.setBlocksDirty(pos, state, blockstate);
		}
		if (!powered && prevPowered) {
			plateMethods.additionalplacements$playOffSoundPublic(level, pos);
			level.gameEvent(entity, GameEvent.BLOCK_DEACTIVATE, pos);
		}
		else if (powered && !prevPowered) {
			plateMethods.additionalplacements$playOnSoundPublic(level, pos);
			level.gameEvent(entity, GameEvent.BLOCK_ACTIVATE, pos);
		}
		if (powered) level.scheduleTick(new BlockPos(pos), this, plateMethods.additionalplacements$getPressedTimePublic());
	}

	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (!isMoving && !state.is(newState.getBlock())) {
			if (plateMethods.additionalplacements$getSignalForStatePublic(state) > 0) this.updateNeighbours(level, pos, state);
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	protected void updateNeighbours(Level level, BlockPos pos, BlockState state) {
		level.updateNeighborsAt(pos, this);
		level.updateNeighborsAt(pos.relative(state.getValue(PLACING)), this);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getSignal(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction dir) {
		return this.plateMethods.additionalplacements$getSignalForStatePublic(state);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getDirectSignal(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction dir) {
		return dir == state.getValue(PLACING).getOpposite() ? plateMethods.additionalplacements$getSignalForStatePublic(state) : 0;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ResourceLocation getBaseModelPrefix() {
		return PressurePlateModels.BASE_MODEL_FOLDER;
	}
}
