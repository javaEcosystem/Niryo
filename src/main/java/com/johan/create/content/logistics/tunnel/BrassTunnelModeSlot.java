package com.johan.create.content.logistics.tunnel;

import com.johan.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;

import net.minecraft.core.Direction;

public class BrassTunnelModeSlot extends CenteredSideValueBoxTransform {

	public BrassTunnelModeSlot() {
		super((state, d) -> d == Direction.UP);
	}

	@Override
	public int getOverrideColor() {
		return 0x592424;
	}

}
