package com.johan.create.foundation.blockEntity.renderer;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class ColoredOverlayBlockEntityRenderer<T extends BlockEntity> extends SafeBlockEntityRenderer<T> {

	public ColoredOverlayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected void renderSafe(T be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
			int light, int overlay) {

		if (Backend.canUseInstancing(be.getLevel())) return;

		SuperByteBuffer render = render(getOverlayBuffer(be), getColor(be, partialTicks), light);
		render.renderInto(ms, buffer.getBuffer(RenderType.solid()));
	}

	protected abstract int getColor(T be, float partialTicks);

	protected abstract SuperByteBuffer getOverlayBuffer(T be);

	public static SuperByteBuffer render(SuperByteBuffer buffer, int color, int light) {
		return buffer.color(color).light(light);
	}

}
