package com.johan.create.content.fluids.hosePulley;

import com.jozufozu.flywheel.core.PartialModel;
import com.johan.create.AllPartialModels;
import com.johan.create.content.contraptions.pulley.AbstractPulleyRenderer;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction.Axis;

public class HosePulleyRenderer extends AbstractPulleyRenderer<HosePulleyBlockEntity> {

	public HosePulleyRenderer(BlockEntityRendererProvider.Context context) {
		super(context, AllPartialModels.HOSE_HALF, AllPartialModels.HOSE_HALF_MAGNET);
	}

	@Override
	protected Axis getShaftAxis(HosePulleyBlockEntity be) {
		return be.getBlockState()
			.getValue(HosePulleyBlock.HORIZONTAL_FACING)
			.getClockWise()
			.getAxis();
	}

	@Override
	protected PartialModel getCoil() {
		return AllPartialModels.HOSE_COIL;
	}

	@Override
	protected SuperByteBuffer renderRope(HosePulleyBlockEntity be) {
		return CachedBufferer.partial(AllPartialModels.HOSE, be.getBlockState());
	}

	@Override
	protected SuperByteBuffer renderMagnet(HosePulleyBlockEntity be) {
		return CachedBufferer.partial(AllPartialModels.HOSE_MAGNET, be.getBlockState());
	}

	@Override
	protected float getOffset(HosePulleyBlockEntity be, float partialTicks) {
		return be.getInterpolatedOffset(partialTicks);
	}

	@Override
	protected boolean isRunning(HosePulleyBlockEntity be) {
		return true;
	}

}
