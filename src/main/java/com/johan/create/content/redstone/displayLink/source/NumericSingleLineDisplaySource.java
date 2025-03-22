package com.johan.create.content.redstone.displayLink.source;

import com.johan.create.content.redstone.displayLink.DisplayLinkContext;
import com.johan.create.content.trains.display.FlapDisplaySection;
import com.johan.create.foundation.utility.Components;

import net.minecraft.network.chat.Component;

public abstract class NumericSingleLineDisplaySource extends SingleLineDisplaySource {

	protected static final Component ZERO = Components.literal("0");

	@Override
	protected String getFlapDisplayLayoutName(DisplayLinkContext context) {
		return "Number";
	}

	@Override
	protected FlapDisplaySection createSectionForValue(DisplayLinkContext context, int size) {
		return new FlapDisplaySection(size * FlapDisplaySection.MONOSPACE, "numeric", false, false);
	}

}
