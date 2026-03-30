package com.firemerald.additionalplacements.client.models;

import net.minecraft.client.resources.model.*;

public interface IAPUnbakedModel extends ResolvableModel {
    BakedModel bake(ModelBaker baker, ModelState modelState);
}
