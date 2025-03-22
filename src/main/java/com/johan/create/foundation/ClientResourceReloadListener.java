package com.johan.create.foundation;

import com.johan.create.CreateClient;
import com.johan.create.content.kinetics.belt.BeltHelper;
import com.johan.create.foundation.sound.SoundScapes;
import com.johan.create.foundation.utility.LangNumberFormat;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class ClientResourceReloadListener implements ResourceManagerReloadListener {

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		CreateClient.invalidateRenderers();
		SoundScapes.invalidateAll();
		LangNumberFormat.numberFormat.update();
		BeltHelper.uprightCache.clear();
	}

}
