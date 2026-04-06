package com.firemerald.additionalplacements.client.block.modeldef;

import com.firemerald.additionalplacements.block.stairs.AdditionalStairBlock;
import com.firemerald.additionalplacements.client.models.definitions.StairModels;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import com.firemerald.additionalplacements.util.PlatformUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;

public class StairsModelDef implements IBlockModelDef<AdditionalStairBlock> {
    static {
        PlatformUtils.checkIsClient();
    }
    public static final StairsModelDef INSTANCE = new StairsModelDef();

    private StairsModelDef() {}

    @Override
    public Identifier getBaseModelPrefix(AdditionalStairBlock block) {
        return StairModels.BASE_MODEL_FOLDER;
    }

    @Override
    public StateModelDefinition getModelDefinition(AdditionalStairBlock block, BlockState state) {
        return StairModels.getModelDefinition(block.additionalplacements$getShapeState(state));
    }

    @Override
    public String[] getAllModels(AdditionalStairBlock block) {
        return StairModels.MODELS;
    }
}
