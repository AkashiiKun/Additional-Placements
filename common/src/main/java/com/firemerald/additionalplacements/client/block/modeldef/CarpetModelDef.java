package com.firemerald.additionalplacements.client.block.modeldef;

import com.firemerald.additionalplacements.block.AdditionalCarpetBlock;
import com.firemerald.additionalplacements.client.models.definitions.CarpetModels;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import com.firemerald.additionalplacements.util.PlatformUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class CarpetModelDef implements IBlockModelDef<AdditionalCarpetBlock> {
    static {
        PlatformUtils.checkIsClient();
    }
    public static final CarpetModelDef INSTANCE = new CarpetModelDef();

    private CarpetModelDef() {}

    @Override
    public ResourceLocation getBaseModelPrefix(AdditionalCarpetBlock block) {
        return CarpetModels.BASE_MODEL_FOLDER;
    }

    @Override
    public StateModelDefinition getModelDefinition(AdditionalCarpetBlock block, BlockState state) {
        return CarpetModels.getModel(state);
    }

    @Override
    public String[] getAllModels(AdditionalCarpetBlock block) {
        return CarpetModels.MODELS;
    }
}
