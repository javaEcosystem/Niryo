package com.johan.create.content.redstone.diodes;

import com.johan.create.AllPartialModels;
import com.johan.create.foundation.blockEntity.renderer.ColoredOverlayBlockEntityRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;
import com.johan.create.foundation.utility.Color;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class BrassDiodeRenderer extends ColoredOverlayBlockEntityRenderer<BrassDiodeBlockEntity> {

	public BrassDiodeRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected int getColor(BrassDiodeBlockEntity be, float partialTicks) {
		return Color.mixColors(0x2C0300, 0xCD0000, be.getProgress());
	}

	@Override
	protected SuperByteBuffer getOverlayBuffer(BrassDiodeBlockEntity be) {
		return CachedBufferer.partial(AllPartialModels.FLEXPEATER_INDICATOR, be.getBlockState());
	}

}
