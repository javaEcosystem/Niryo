package com.johan.create.content.contraptions.bearing;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllPartialModels;
import com.johan.create.content.kinetics.base.KineticBlockEntity;
import com.johan.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;
import com.johan.create.foundation.utility.AngleHelper;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BearingRenderer<T extends KineticBlockEntity & IBearingBlockEntity> extends KineticBlockEntityRenderer<T> {

	public BearingRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(T be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
		int light, int overlay) {

		if (Backend.canUseInstancing(be.getLevel())) return;

		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

		final Direction facing = be.getBlockState()
				.getValue(BlockStateProperties.FACING);
		PartialModel top =
				be.isWoodenTop() ? AllPartialModels.BEARING_TOP_WOODEN : AllPartialModels.BEARING_TOP;
		SuperByteBuffer superBuffer = CachedBufferer.partial(top, be.getBlockState());

		float interpolatedAngle = be.getInterpolatedAngle(partialTicks - 1);
		kineticRotationTransform(superBuffer, be, facing.getAxis(), (float) (interpolatedAngle / 180 * Math.PI), light);

		if (facing.getAxis()
				.isHorizontal())
			superBuffer.rotateCentered(Direction.UP,
					AngleHelper.rad(AngleHelper.horizontalAngle(facing.getOpposite())));
		superBuffer.rotateCentered(Direction.EAST, AngleHelper.rad(-90 - AngleHelper.verticalAngle(facing)));
		superBuffer.renderInto(ms, buffer.getBuffer(RenderType.solid()));
	}

	@Override
	protected SuperByteBuffer getRotatedModel(KineticBlockEntity be, BlockState state) {
		return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state, state
				.getValue(BearingBlock.FACING)
				.getOpposite());
	}

}
