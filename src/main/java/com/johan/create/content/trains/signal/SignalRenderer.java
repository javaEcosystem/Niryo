package com.johan.create.content.trains.signal;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllPartialModels;
import com.johan.create.content.trains.signal.SignalBlockEntity.OverlayState;
import com.johan.create.content.trains.signal.SignalBlockEntity.SignalState;
import com.johan.create.content.trains.track.ITrackBlock;
import com.johan.create.content.trains.track.TrackTargetingBehaviour;
import com.johan.create.content.trains.track.TrackTargetingBehaviour.RenderedTrackOverlayType;
import com.johan.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.utility.AnimationTickHolder;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SignalRenderer extends SafeBlockEntityRenderer<SignalBlockEntity> {

	public SignalRenderer(BlockEntityRendererProvider.Context context) {}

	@Override
	protected void renderSafe(SignalBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
		int light, int overlay) {
		BlockState blockState = be.getBlockState();
		SignalState signalState = be.getState();
		OverlayState overlayState = be.getOverlay();

		float renderTime = AnimationTickHolder.getRenderTime(be.getLevel());
		if (signalState.isRedLight(renderTime))
			CachedBufferer.partial(AllPartialModels.SIGNAL_ON, blockState)
				.renderInto(ms, buffer.getBuffer(RenderType.solid()));
		else
			CachedBufferer.partial(AllPartialModels.SIGNAL_OFF, blockState)
				.light(light)
				.renderInto(ms, buffer.getBuffer(RenderType.solid()));

		BlockPos pos = be.getBlockPos();
		TrackTargetingBehaviour<SignalBoundary> target = be.edgePoint;
		BlockPos targetPosition = target.getGlobalPosition();
		Level level = be.getLevel();
		BlockState trackState = level.getBlockState(targetPosition);
		Block block = trackState.getBlock();

		if (!(block instanceof ITrackBlock))
			return;
		if (overlayState == OverlayState.SKIP)
			return;

		ms.pushPose();
		TransformStack.cast(ms)
			.translate(targetPosition.subtract(pos));
		RenderedTrackOverlayType type =
			overlayState == OverlayState.DUAL ? RenderedTrackOverlayType.DUAL_SIGNAL : RenderedTrackOverlayType.SIGNAL;
		TrackTargetingBehaviour.render(level, targetPosition, target.getTargetDirection(), target.getTargetBezier(), ms,
			buffer, light, overlay, type, 1);
		ms.popPose();

	}

}
