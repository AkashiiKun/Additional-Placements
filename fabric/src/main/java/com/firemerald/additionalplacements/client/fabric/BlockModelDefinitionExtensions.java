package com.firemerald.additionalplacements.client.fabric;

import com.firemerald.additionalplacements.client.models.IAPCustomBlockModelDefinition;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface BlockModelDefinitionExtensions {
    @Nullable
    IAPCustomBlockModelDefinition additionalplacements$getCustomModelDefinition();

    @ApiStatus.Internal
    void additionalplacements$setCustomModelDefinition(@Nullable IAPCustomBlockModelDefinition customDefinition);
}
