package com.johan.create.content.redstone.displayLink.source;

import com.johan.create.content.contraptions.elevator.ElevatorContactBlockEntity;
import com.johan.create.content.redstone.displayLink.DisplayLinkContext;
import com.johan.create.content.redstone.displayLink.target.DisplayTargetStats;
import com.johan.create.foundation.utility.Components;

import net.minecraft.network.chat.MutableComponent;

public class CurrentFloorDisplaySource extends SingleLineDisplaySource {

	@Override
	protected MutableComponent provideLine(DisplayLinkContext context, DisplayTargetStats stats) {
		if (!(context.getSourceBlockEntity() instanceof ElevatorContactBlockEntity ecbe))
			return EMPTY_LINE;
		return Components.literal(ecbe.lastReportedCurrentFloor);
	}

	@Override
	protected String getTranslationKey() {
		return "current_floor";
	}

	@Override
	protected boolean allowsLabeling(DisplayLinkContext context) {
		return false;
	}

}
