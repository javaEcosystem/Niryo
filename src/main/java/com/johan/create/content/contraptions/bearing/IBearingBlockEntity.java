package com.johan.create.content.contraptions.bearing;

import com.johan.create.content.contraptions.DirectionalExtenderScrollOptionSlot;
import com.johan.create.content.contraptions.IControlContraption;
import com.johan.create.foundation.blockEntity.behaviour.ValueBoxTransform;

import net.minecraft.core.Direction.Axis;

public interface IBearingBlockEntity extends IControlContraption {

	float getInterpolatedAngle(float partialTicks);

	boolean isWoodenTop();

	default ValueBoxTransform getMovementModeSlot() {
		return new DirectionalExtenderScrollOptionSlot((state, d) -> {
			Axis axis = d.getAxis();
			Axis bearingAxis = state.getValue(BearingBlock.FACING)
				.getAxis();
			return bearingAxis != axis;
		});
	}

	void setAngle(float forcedAngle);

}
