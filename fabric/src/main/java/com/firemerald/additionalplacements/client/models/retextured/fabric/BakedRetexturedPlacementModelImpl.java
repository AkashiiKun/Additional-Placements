package com.firemerald.additionalplacements.client.models.retextured.fabric;

import com.firemerald.additionalplacements.client.models.fabric.PlacementModelWrapperImpl;
import com.firemerald.additionalplacements.client.models.retextured.BakedRetexturedPlacementModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;

public class BakedRetexturedPlacementModelImpl extends BakedRetexturedPlacementModel implements PlacementModelWrapperImpl {
	public static BakedRetexturedPlacementModel of(BakedModel ourModel, BlockState theirModelState) {
		return new BakedRetexturedPlacementModelImpl(ourModel, theirModelState);
	}

	private BakedRetexturedPlacementModelImpl(BakedModel ourModel, BlockState theirModelState) {
		super(ourModel, theirModelState);
    }
}
