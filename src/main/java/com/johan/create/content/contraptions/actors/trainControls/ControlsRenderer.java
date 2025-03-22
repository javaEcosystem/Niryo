package com.johan.create.content.contraptions.actors.trainControls;

import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllPartialModels;
import com.johan.create.content.contraptions.behaviour.MovementContext;
import com.johan.create.content.contraptions.render.ContraptionMatrices;
import com.johan.create.content.contraptions.render.ContraptionRenderDispatcher;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;
import com.johan.create.foundation.utility.AngleHelper;
import com.johan.create.foundation.utility.Iterate;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class ControlsRenderer {

	public static void render(MovementContext context, VirtualRenderWorld renderWorld, ContraptionMatrices matrices,
		MultiBufferSource buffer, float equipAnimation, float firstLever, float secondLever) {
		BlockState state = context.state;
		Direction facing = state.getValue(ControlsBlock.FACING);

		SuperByteBuffer cover = CachedBufferer.partial(AllPartialModels.TRAIN_CONTROLS_COVER, state);
		float hAngle = 180 + AngleHelper.horizontalAngle(facing);
		PoseStack ms = matrices.getModel();
		cover.transform(ms)
			.centre()
			.rotateY(hAngle)
			.unCentre()
			.light(matrices.getWorld(), ContraptionRenderDispatcher.getContraptionWorldLight(context, renderWorld))
			.renderInto(matrices.getViewProjection(), buffer.getBuffer(RenderType.cutoutMipped()));

		double yOffset = Mth.lerp(equipAnimation * equipAnimation, -0.15f, 0.05f);

		for (boolean first : Iterate.trueAndFalse) {
			float vAngle = (float) Mth.clamp(first ? firstLever * 70 - 25 : secondLever * 15, -45, 45);
			SuperByteBuffer lever = CachedBufferer.partial(AllPartialModels.TRAIN_CONTROLS_LEVER, state);
			ms.pushPose();
			TransformStack.cast(ms)
				.centre()
				.rotateY(hAngle)
				.translate(0, 0, 4 / 16f)
				.rotateX(vAngle - 45)
				.translate(0, yOffset, 0)
				.rotateX(45)
				.unCentre()
				.translate(0, -2 / 16f, -3 / 16f)
				.translate(first ? 0 : 6 / 16f, 0, 0);
			lever.transform(ms)
				.light(matrices.getWorld(), ContraptionRenderDispatcher.getContraptionWorldLight(context, renderWorld))
				.renderInto(matrices.getViewProjection(), buffer.getBuffer(RenderType.solid()));
			ms.popPose();
		}

	}

}
