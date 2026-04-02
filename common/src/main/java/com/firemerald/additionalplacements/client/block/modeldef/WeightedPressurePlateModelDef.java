package com.firemerald.additionalplacements.client.block.modeldef;

import com.firemerald.additionalplacements.block.AdditionalWeightedPressurePlateBlock;
import com.firemerald.additionalplacements.client.models.definitions.PressurePlateModels;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import com.firemerald.additionalplacements.util.PlatformUtils;
import net.minecraft.world.level.block.state.BlockState;

public class WeightedPressurePlateModelDef extends BasePressurePlateModelDef<AdditionalWeightedPressurePlateBlock> {
    static {
        PlatformUtils.checkIsClient();
    }
    public static final WeightedPressurePlateModelDef INSTANCE = new WeightedPressurePlateModelDef();

    private WeightedPressurePlateModelDef() {}

    @Override
    public StateModelDefinition getModelDefinition(AdditionalWeightedPressurePlateBlock block, BlockState state) {
        return PressurePlateModels.getWeightedPressurePlateModel(state);
    }
}
