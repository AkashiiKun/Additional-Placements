package com.firemerald.additionalplacements.client.models.rotated.fabric;

import com.firemerald.additionalplacements.client.models.fabric.PlacementModelWrapperImpl;
import com.firemerald.additionalplacements.client.models.rotated.BakedRotatedPlacementModel;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.world.level.block.state.BlockState;

public class BakedRotatedPlacementModelImpl extends BakedRotatedPlacementModel implements PlacementModelWrapperImpl {
	public static BakedRotatedPlacementModel of(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		return new BakedRotatedPlacementModelImpl(theirModelState, modelRotation, rotatesTexture);
	}

	private BakedRotatedPlacementModelImpl(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		super(theirModelState, modelRotation, rotatesTexture);
    }
}
