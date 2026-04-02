package com.firemerald.additionalplacements.client.block.modeldef;

import com.firemerald.additionalplacements.block.AdditionalPressurePlateBlock;
import com.firemerald.additionalplacements.client.models.definitions.PressurePlateModels;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import com.firemerald.additionalplacements.util.PlatformUtils;
import net.minecraft.world.level.block.state.BlockState;

public class PressurePlateModelDef extends BasePressurePlateModelDef<AdditionalPressurePlateBlock> {
    static {
        PlatformUtils.checkIsClient();
    }
    public static final PressurePlateModelDef INSTANCE = new PressurePlateModelDef();

    private PressurePlateModelDef() {}

    @Override
    public StateModelDefinition getModelDefinition(AdditionalPressurePlateBlock block, BlockState state) {
        return PressurePlateModels.getPressurePlateModel(state);
    }
}
