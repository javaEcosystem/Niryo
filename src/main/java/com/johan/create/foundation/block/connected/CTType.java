package com.johan.create.foundation.block.connected;

import com.johan.create.foundation.block.connected.ConnectedTextureBehaviour.CTContext;
import com.johan.create.foundation.block.connected.ConnectedTextureBehaviour.ContextRequirement;

import net.minecraft.resources.ResourceLocation;

public interface CTType {
	ResourceLocation getId();

	int getSheetSize();

	ContextRequirement getContextRequirement();

	int getTextureIndex(CTContext context);
}
