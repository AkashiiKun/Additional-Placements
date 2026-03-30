package com.firemerald.additionalplacements.mixin;

import com.firemerald.additionalplacements.client.models.DelegateBakedModelExtension;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.DelegateBakedModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DelegateBakedModel.class)
public abstract class MixinDelegateBakedModel implements DelegateBakedModelExtension {
    @Final
    @Shadow
    protected BakedModel parent;

    @Override
    public BakedModel additionalplacements$parent() {
        return parent;
    }
}
