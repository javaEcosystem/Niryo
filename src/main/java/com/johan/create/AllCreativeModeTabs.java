package com.johan.create;

import com.johan.create.content.decoration.palettes.PalettesCreativeModeTab;
import com.johan.create.infrastructure.item.BaseCreativeModeTab;

import net.minecraft.world.item.CreativeModeTab;

public class AllCreativeModeTabs {
	public static final CreativeModeTab BASE_CREATIVE_TAB = new BaseCreativeModeTab();
	public static final CreativeModeTab PALETTES_CREATIVE_TAB = new PalettesCreativeModeTab();

	public static void init() {
	}
}
