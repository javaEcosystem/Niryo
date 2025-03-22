package com.johan.create.content.kinetics.fan.processing;

import javax.annotation.ParametersAreNonnullByDefault;

import com.johan.create.AllRecipeTypes;
import com.johan.create.content.kinetics.fan.processing.SplashingRecipe.SplashingWrapper;
import com.johan.create.content.processing.recipe.ProcessingRecipe;
import com.johan.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

@ParametersAreNonnullByDefault
public class SplashingRecipe extends ProcessingRecipe<SplashingWrapper> {

	public SplashingRecipe(ProcessingRecipeParams params) {
		super(AllRecipeTypes.SPLASHING, params);
	}

	@Override
	public boolean matches(SplashingWrapper inv, Level worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
			.test(inv.getItem(0));
	}

	@Override
	protected int getMaxInputCount() {
		return 1;
	}

	@Override
	protected int getMaxOutputCount() {
		return 12;
	}

	public static class SplashingWrapper extends RecipeWrapper {
		public SplashingWrapper() {
			super(new ItemStackHandler(1));
		}
	}

}
