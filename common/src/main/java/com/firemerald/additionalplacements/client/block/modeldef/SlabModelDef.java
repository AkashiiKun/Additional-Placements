package com.firemerald.additionalplacements.client.block.modeldef;

import com.firemerald.additionalplacements.block.VerticalSlabBlock;
import com.firemerald.additionalplacements.client.models.definitions.SlabModels;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import com.firemerald.additionalplacements.util.PlatformUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class SlabModelDef implements IBlockModelDef<VerticalSlabBlock> {
    static {
        PlatformUtils.checkIsClient();
    }
    public static final SlabModelDef INSTANCE = new SlabModelDef();

    private SlabModelDef() {}

    @Override
    public ResourceLocation getBaseModelPrefix(VerticalSlabBlock block) {
        return SlabModels.BASE_MODEL_FOLDER;
    }

    @Override
    public StateModelDefinition getModelDefinition(VerticalSlabBlock block, BlockState state) {
        return SlabModels.getModel(state);
    }

    @Override
    public String[] getAllModels(VerticalSlabBlock block) {
        return SlabModels.MODELS;
    }
}
