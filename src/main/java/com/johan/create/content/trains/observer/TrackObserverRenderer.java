package com.johan.create.content.trains.observer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.content.trains.track.ITrackBlock;
import com.johan.create.content.trains.track.TrackTargetingBehaviour;
import com.johan.create.content.trains.track.TrackTargetingBehaviour.RenderedTrackOverlayType;
import com.johan.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TrackObserverRenderer extends SmartBlockEntityRenderer<TrackObserverBlockEntity> {

	public TrackObserverRenderer(Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(TrackObserverBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
		int light, int overlay) {
		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
		BlockPos pos = be.getBlockPos();

		TrackTargetingBehaviour<TrackObserver> target = be.edgePoint;
		BlockPos targetPosition = target.getGlobalPosition();
		Level level = be.getLevel();
		BlockState trackState = level.getBlockState(targetPosition);
		Block block = trackState.getBlock();

		if (!(block instanceof ITrackBlock))
			return;

		ms.pushPose();
		TransformStack.cast(ms)
			.translate(targetPosition.subtract(pos));
		RenderedTrackOverlayType type = RenderedTrackOverlayType.OBSERVER;
		TrackTargetingBehaviour.render(level, targetPosition, target.getTargetDirection(), target.getTargetBezier(), ms,
			buffer, light, overlay, type, 1);
		ms.popPose();

	}

}
