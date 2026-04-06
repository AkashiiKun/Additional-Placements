package com.firemerald.additionalplacements.client.block.modeldef;

import com.firemerald.additionalplacements.block.AdditionalBasePressurePlateBlock;
import com.firemerald.additionalplacements.client.models.definitions.PressurePlateModels;
import com.firemerald.additionalplacements.util.PlatformUtils;
import net.minecraft.resources.Identifier;

public abstract class BasePressurePlateModelDef<T extends AdditionalBasePressurePlateBlock<?>> implements IBlockModelDef<T> {
    static {
        PlatformUtils.checkIsClient();
    }

    protected BasePressurePlateModelDef() {}

    @Override
    public Identifier getBaseModelPrefix(T block) {
        return PressurePlateModels.BASE_MODEL_FOLDER;
    }

    @Override
    public String[] getAllModels(T block) {
        return PressurePlateModels.MODELS;
    }
}
