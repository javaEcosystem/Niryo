package com.johan.create.content.kinetics.belt;

import com.johan.create.foundation.utility.Lang;

import net.minecraft.util.StringRepresentable;

public enum BeltSlope implements StringRepresentable {
	HORIZONTAL, UPWARD, DOWNWARD, VERTICAL, SIDEWAYS;

	@Override
	public String getSerializedName() {
		return Lang.asId(name());
	}

	public boolean isDiagonal() {
		return this == UPWARD || this == DOWNWARD;
	}
}
