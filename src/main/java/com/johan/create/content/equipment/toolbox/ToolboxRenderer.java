package com.johan.create.content.equipment.toolbox;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.johan.create.AllPartialModels;
import com.johan.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;
import com.johan.create.foundation.utility.Iterate;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class ToolboxRenderer extends SmartBlockEntityRenderer<ToolboxBlockEntity> {

	public ToolboxRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(ToolboxBlockEntity blockEntity, float partialTicks, PoseStack ms,
		MultiBufferSource buffer, int light, int overlay) {

		BlockState blockState = blockEntity.getBlockState();
		Direction facing = blockState.getValue(ToolboxBlock.FACING)
			.getOpposite();
		SuperByteBuffer lid =
			CachedBufferer.partial(AllPartialModels.TOOLBOX_LIDS.get(blockEntity.getColor()), blockState);
		SuperByteBuffer drawer = CachedBufferer.partial(AllPartialModels.TOOLBOX_DRAWER, blockState);

		float lidAngle = blockEntity.lid.getValue(partialTicks);
		float drawerOffset = blockEntity.drawers.getValue(partialTicks);

		VertexConsumer builder = buffer.getBuffer(RenderType.cutoutMipped());
		lid.centre()
			.rotateY(-facing.toYRot())
			.unCentre()
			.translate(0, 6 / 16f, 12 / 16f)
			.rotateX(135 * lidAngle)
			.translate(0, -6 / 16f, -12 / 16f)
			.light(light)
			.renderInto(ms, builder);

		for (int offset : Iterate.zeroAndOne) {
			drawer.centre()
					.rotateY(-facing.toYRot())
					.unCentre()
					.translate(0, offset * 1 / 8f, -drawerOffset * .175f * (2 - offset))
					.light(light)
					.renderInto(ms, builder);
		}

	}

}
