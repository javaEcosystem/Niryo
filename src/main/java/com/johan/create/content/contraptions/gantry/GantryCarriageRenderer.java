package com.johan.create.content.contraptions.gantry;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllPartialModels;
import com.johan.create.content.kinetics.base.KineticBlockEntity;
import com.johan.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;
import com.johan.create.foundation.utility.AngleHelper;
import com.johan.create.foundation.utility.AnimationTickHolder;
import com.johan.create.foundation.utility.Iterate;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.block.state.BlockState;

public class GantryCarriageRenderer extends KineticBlockEntityRenderer<GantryCarriageBlockEntity> {

	public GantryCarriageRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(GantryCarriageBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
		int light, int overlay) {
		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

		if (Backend.canUseInstancing(be.getLevel())) return;

		BlockState state = be.getBlockState();
		Direction facing = state.getValue(GantryCarriageBlock.FACING);
		Boolean alongFirst = state.getValue(GantryCarriageBlock.AXIS_ALONG_FIRST_COORDINATE);
		Axis rotationAxis = getRotationAxisOf(be);
		BlockPos visualPos = facing.getAxisDirection() == AxisDirection.POSITIVE ? be.getBlockPos()
				: be.getBlockPos()
				.relative(facing.getOpposite());
		float angleForBE = getAngleForBE(be, visualPos, rotationAxis);

		Axis gantryAxis = Axis.X;
		for (Axis axis : Iterate.axes)
			if (axis != rotationAxis && axis != facing.getAxis())
				gantryAxis = axis;

		if (gantryAxis == Axis.X)
			if (facing == Direction.UP)
				angleForBE *= -1;
		if (gantryAxis == Axis.Y)
			if (facing == Direction.NORTH || facing == Direction.EAST)
				angleForBE *= -1;

		SuperByteBuffer cogs = CachedBufferer.partial(AllPartialModels.GANTRY_COGS, state);
		cogs.centre()
				.rotateY(AngleHelper.horizontalAngle(facing))
				.rotateX(facing == Direction.UP ? 0 : facing == Direction.DOWN ? 180 : 90)
				.rotateY(alongFirst ^ facing.getAxis() == Axis.X ? 0 : 90)
				.translate(0, -9 / 16f, 0)
				.rotateX(-angleForBE)
				.translate(0, 9 / 16f, 0)
				.unCentre();

		cogs.light(light)
			.renderInto(ms, buffer.getBuffer(RenderType.solid()));

	}

	public static float getAngleForBE(KineticBlockEntity be, final BlockPos pos, Axis axis) {
		float time = AnimationTickHolder.getRenderTime(be.getLevel());
		float offset = getRotationOffsetForPosition(be, pos, axis);
		return (time * be.getSpeed() * 3f / 20 + offset) % 360;
	}

	@Override
	protected BlockState getRenderedBlockState(GantryCarriageBlockEntity be) {
		return shaft(getRotationAxisOf(be));
	}

}
