package com.johan.create.content.kinetics.speedController;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.johan.create.AllPartialModels;
import com.johan.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.johan.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SpeedControllerRenderer extends SmartBlockEntityRenderer<SpeedControllerBlockEntity> {

	public SpeedControllerRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(SpeedControllerBlockEntity blockEntity, float partialTicks, PoseStack ms,
		MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);

		VertexConsumer builder = buffer.getBuffer(RenderType.solid());
		if (!Backend.canUseInstancing(blockEntity.getLevel())) {
			KineticBlockEntityRenderer.renderRotatingBuffer(blockEntity, getRotatedModel(blockEntity), ms, builder, light);
		}

		if (!blockEntity.hasBracket)
			return;

		BlockPos pos = blockEntity.getBlockPos();
		Level world = blockEntity.getLevel();
		BlockState blockState = blockEntity.getBlockState();
		boolean alongX = blockState.getValue(SpeedControllerBlock.HORIZONTAL_AXIS) == Axis.X;

		SuperByteBuffer bracket = CachedBufferer.partial(AllPartialModels.SPEED_CONTROLLER_BRACKET, blockState);
		bracket.translate(0, 1, 0);
		bracket.rotateCentered(Direction.UP,
				(float) (alongX ? Math.PI : Math.PI / 2));
		bracket.light(LevelRenderer.getLightColor(world, pos.above()));
		bracket.renderInto(ms, builder);
	}

	private SuperByteBuffer getRotatedModel(SpeedControllerBlockEntity blockEntity) {
		return CachedBufferer.block(KineticBlockEntityRenderer.KINETIC_BLOCK,
				KineticBlockEntityRenderer.shaft(KineticBlockEntityRenderer.getRotationAxisOf(blockEntity)));
	}

}
