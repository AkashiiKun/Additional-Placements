package com.firemerald.additionalplacements.client.block.modeldef;

import com.firemerald.additionalplacements.block.AdditionalPlacementBlock;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockModelDef<T extends AdditionalPlacementBlock<?>> {
    Identifier getBaseModelPrefix(T block);

    StateModelDefinition getModelDefinition(T block, BlockState state);

    String[] getAllModels(T block);
}
