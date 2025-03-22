package com.johan.create.content.kinetics.belt;

import com.johan.create.foundation.utility.Lang;

import net.minecraft.util.StringRepresentable;

public enum BeltPart implements StringRepresentable {
	START, MIDDLE, END, PULLEY;

	@Override
	public String getSerializedName() {
		return Lang.asId(name());
	}
}
