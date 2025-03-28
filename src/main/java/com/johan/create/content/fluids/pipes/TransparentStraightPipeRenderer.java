package com.johan.create.content.fluids.pipes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.content.fluids.FluidTransportBehaviour;
import com.johan.create.content.fluids.PipeConnection.Flow;
import com.johan.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.johan.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.johan.create.foundation.fluid.FluidRenderer;
import com.johan.create.foundation.utility.Iterate;
import com.johan.create.foundation.utility.animation.LerpedFloat;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.FluidStack;

public class TransparentStraightPipeRenderer extends SafeBlockEntityRenderer<StraightPipeBlockEntity> {

	public TransparentStraightPipeRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected void renderSafe(StraightPipeBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
		int light, int overlay) {
		FluidTransportBehaviour pipe = be.getBehaviour(FluidTransportBehaviour.TYPE);
		if (pipe == null)
			return;

		for (Direction side : Iterate.directions) {

			Flow flow = pipe.getFlow(side);
			if (flow == null)
				continue;
			FluidStack fluidStack = flow.fluid;
			if (fluidStack.isEmpty())
				continue;
			LerpedFloat progress = flow.progress;
			if (progress == null)
				continue;

			float value = progress.getValue(partialTicks);
			boolean inbound = flow.inbound;
			if (value == 1) {
				if (inbound) {
					Flow opposite = pipe.getFlow(side.getOpposite());
					if (opposite == null)
						value -= 1e-6f;
				} else {
					FluidTransportBehaviour adjacent = BlockEntityBehaviour.get(be.getLevel(), be.getBlockPos()
						.relative(side), FluidTransportBehaviour.TYPE);
					if (adjacent == null)
						value -= 1e-6f;
					else {
						Flow other = adjacent.getFlow(side.getOpposite());
						if (other == null || !other.inbound && !other.complete)
							value -= 1e-6f;
					}
				}
			}

			FluidRenderer.renderFluidStream(fluidStack, side, 3 / 16f, value, inbound, buffer, ms, light);
		}

	}

}
