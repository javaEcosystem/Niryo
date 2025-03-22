package com.johan.create.content.kinetics.millstone;

import javax.annotation.ParametersAreNonnullByDefault;

import com.johan.create.AllRecipeTypes;
import com.johan.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.johan.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

@ParametersAreNonnullByDefault
public class MillingRecipe extends AbstractCrushingRecipe {

	public MillingRecipe(ProcessingRecipeParams params) {
		super(AllRecipeTypes.MILLING, params);
	}

	@Override
	public boolean matches(RecipeWrapper inv, Level worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
			.test(inv.getItem(0));
	}

	@Override
	protected int getMaxOutputCount() {
		return 4;
	}
}
