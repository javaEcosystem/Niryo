package com.johan.create.content.kinetics.motor;

import com.johan.create.AllPartialModels;
import com.johan.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeMotorRenderer extends KineticBlockEntityRenderer<CreativeMotorBlockEntity> {

	public CreativeMotorRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected SuperByteBuffer getRotatedModel(CreativeMotorBlockEntity be, BlockState state) {
		return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state);
	}

}
