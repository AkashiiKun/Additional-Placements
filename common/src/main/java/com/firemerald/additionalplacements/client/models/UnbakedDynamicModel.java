package com.firemerald.additionalplacements.client.models;

import com.firemerald.additionalplacements.block.AdditionalPlacementBlock;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import com.firemerald.additionalplacements.client.models.retextured.BakedRetexturedPlacementModel;
import com.firemerald.additionalplacements.client.models.rotated.BakedRotatedPlacementModel;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public record UnbakedDynamicModel(AdditionalPlacementBlock<?> block) implements BlockStateModel.UnbakedRoot {
    @Override
    @NotNull
    public BlockStateModel bake(BlockState state, ModelBaker baker) {
        if (block.rotatesModel(state)) {
            BlockState theirModelState = block.getModelState(state);
            BlockRotation modelRotation = block.getRotation(state);
            boolean rotatesTexture = block.rotatesTexture(state);
            return BakedRotatedPlacementModel.of(theirModelState, modelRotation, rotatesTexture);
        } else {
            StateModelDefinition modelDefinition = block.getModelDefinition(state);
            SingleVariant.Unbaked ourModel = new SingleVariant.Unbaked(new Variant(
                    modelDefinition.location(block.getBaseModelPrefix()),
                    new Variant.SimpleModelState(
                            modelDefinition.xRotation(),
                            modelDefinition.yRotation(),
                            true
                    )
            ));
            BlockState theirModelState = block.getModelState(state);
            return BakedRetexturedPlacementModel.of(ourModel.bake(baker), theirModelState);
        }
    }

    @Override
    @NotNull
    public Object visualEqualityGroup(BlockState state) {
        return this;
    }

    @Override
    public void resolveDependencies(Resolver resolver) {
        ResourceLocation rootFolder = block.getBaseModelPrefix();
        for (String model : block.getAllModels()) resolver.markDependency(rootFolder.withSuffix(model));
    }
}
