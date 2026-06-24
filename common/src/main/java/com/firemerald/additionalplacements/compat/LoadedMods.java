package com.firemerald.additionalplacements.compat;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.block.AdditionalBasePressurePlateBlock;
import com.firemerald.additionalplacements.block.AdditionalBlockStateProperties;
import com.firemerald.additionalplacements.block.AdditionalCarpetBlock;
import com.firemerald.additionalplacements.client.models.WrapperBlockStateModelExtension;
import com.firemerald.additionalplacements.client.models.Unwrapper;
import com.firemerald.additionalplacements.util.PlatformUtils;
import com.zurrtum.create.api.contraption.BlockMovementChecks;
import dev.architectury.injectables.annotations.ExpectPlatform;
import me.pepperbell.continuity.client.model.CtmBlockStateModel;
import me.pepperbell.continuity.client.model.EmissiveBlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public enum LoadedMods {
    DOUBLESLABS("doubleslabs", () -> AdditionalPlacementsMod.LOGGER.info("DoubleSlabs detected, disabling vertical slab placement under certain conditions")),
    CONTINUITY("continuity", () -> {
        if (PlatformUtils.isClient()) {
            AdditionalPlacementsMod.LOGGER.info("Continuity detected, registering continuity BakedModel unwrappers");
            Unwrapper.registerUnwrapper(model -> {
                if (model instanceof CtmBlockStateModel) return ((WrapperBlockStateModelExtension) model).additionalplacements$wrapped();
                else if (model instanceof EmissiveBlockStateModel) return ((WrapperBlockStateModelExtension) model).additionalplacements$wrapped();
                else return null;
            });
        }
    }),
    CREATE("create", () -> {
        AdditionalPlacementsMod.LOGGER.info("Create detected, registering checks");
        BlockMovementChecks.registerBrittleCheck(state -> {
            Block block = state.getBlock();
            if (
                    block instanceof AdditionalBasePressurePlateBlock<?> ||
                    block instanceof AdditionalCarpetBlock
            ) {
                return BlockMovementChecks.CheckResult.SUCCESS;
            } else {
                return BlockMovementChecks.CheckResult.PASS;
            }
        });
        BlockMovementChecks.registerAttachedCheck((BlockState state, Level world, BlockPos pos, Direction direction) -> {
            Block block = state.getBlock();
            if (
                    block instanceof AdditionalBasePressurePlateBlock<?> ||
                    block instanceof AdditionalCarpetBlock
            ) {
                return direction == state.getValue(AdditionalBlockStateProperties.HORIZONTAL_OR_UP_PLACING) ? BlockMovementChecks.CheckResult.SUCCESS : BlockMovementChecks.CheckResult.FAIL;
            } else {
                return BlockMovementChecks.CheckResult.PASS;
            }
        });
        BlockMovementChecks.registerNotSupportiveCheck((BlockState state, Direction direction) -> {
            Block block = state.getBlock();
            if (
                    block instanceof AdditionalCarpetBlock
            ) {
                return direction == state.getValue(AdditionalBlockStateProperties.HORIZONTAL_OR_UP_PLACING).getOpposite() ? BlockMovementChecks.CheckResult.SUCCESS : BlockMovementChecks.CheckResult.FAIL;
            } else {
                return BlockMovementChecks.CheckResult.PASS;
            }
        });
    });

    public static void populate() {
        AdditionalPlacementsMod.LOGGER.info("Checking loaded mods...");
        for (LoadedMods val : LoadedMods.values()) {
            if (isModLoaded(val.modId)) {
                val.isPresent = true;
                val.whenDetected.run();
            }
        }
        populatePlatform();
        AdditionalPlacementsMod.LOGGER.info("Done");
    }

    @ExpectPlatform
    public static void populatePlatform() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        throw new AssertionError();
    }

    public final String modId;
    public final Runnable whenDetected;
    public boolean isPresent;

    LoadedMods(String modId, Runnable whenDetected) {
        this.modId = modId;
        this.whenDetected = whenDetected;
    }
}
