package com.johan.create.content.fluids.pump;

import com.johan.create.AllPartialModels;
import com.johan.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class PumpRenderer extends KineticBlockEntityRenderer<PumpBlockEntity> {

	public PumpRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected SuperByteBuffer getRotatedModel(PumpBlockEntity be, BlockState state) {
		return CachedBufferer.partialFacing(AllPartialModels.MECHANICAL_PUMP_COG, state);
	}

}
