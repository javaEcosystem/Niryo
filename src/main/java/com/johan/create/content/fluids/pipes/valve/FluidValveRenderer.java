package com.johan.create.content.fluids.pipes.valve;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllPartialModels;
import com.johan.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;
import com.johan.create.foundation.utility.AngleHelper;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class FluidValveRenderer extends KineticBlockEntityRenderer<FluidValveBlockEntity> {

	public FluidValveRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(FluidValveBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
		int light, int overlay) {

		if (Backend.canUseInstancing(be.getLevel())) return;

		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
		BlockState blockState = be.getBlockState();
		SuperByteBuffer pointer = CachedBufferer.partial(AllPartialModels.FLUID_VALVE_POINTER, blockState);
		Direction facing = blockState.getValue(FluidValveBlock.FACING);

		float pointerRotation = Mth.lerp(be.pointer.getValue(partialTicks), 0, -90);
		Axis pipeAxis = FluidValveBlock.getPipeAxis(blockState);
		Axis shaftAxis = getRotationAxisOf(be);

		int pointerRotationOffset = 0;
		if (pipeAxis.isHorizontal() && shaftAxis == Axis.X || pipeAxis.isVertical())
			pointerRotationOffset = 90;

		pointer.centre()
			.rotateY(AngleHelper.horizontalAngle(facing))
			.rotateX(facing == Direction.UP ? 0 : facing == Direction.DOWN ? 180 : 90)
			.rotateY(pointerRotationOffset + pointerRotation)
			.unCentre()
			.light(light)
			.renderInto(ms, buffer.getBuffer(RenderType.solid()));
	}

	@Override
	protected BlockState getRenderedBlockState(FluidValveBlockEntity be) {
		return shaft(getRotationAxisOf(be));
	}

}
