package com.johan.create.foundation.data.recipe;

import com.johan.create.AllItems;
import com.johan.create.AllRecipeTypes;

import net.minecraft.data.DataGenerator;

public class PolishingRecipeGen extends ProcessingRecipeGen {

	GeneratedRecipe

	ROSE_QUARTZ = create(AllItems.ROSE_QUARTZ::get, b -> b.output(AllItems.POLISHED_ROSE_QUARTZ.get()))

	;

	public PolishingRecipeGen(DataGenerator p_i48262_1_) {
		super(p_i48262_1_);
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.SANDPAPER_POLISHING;
	}

}
