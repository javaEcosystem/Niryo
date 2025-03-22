package com.johan.create.content.kinetics.mixer;

import com.johan.create.AllRecipeTypes;
import com.johan.create.content.processing.basin.BasinRecipe;
import com.johan.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

public class MixingRecipe extends BasinRecipe {

	public MixingRecipe(ProcessingRecipeParams params) {
		super(AllRecipeTypes.MIXING, params);
	}

}
