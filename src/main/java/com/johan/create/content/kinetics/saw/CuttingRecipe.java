package com.johan.create.content.kinetics.saw;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.ParametersAreNonnullByDefault;

import com.johan.create.AllBlocks;
import com.johan.create.AllRecipeTypes;
import com.johan.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.johan.create.content.processing.recipe.ProcessingRecipe;
import com.johan.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.johan.create.content.processing.sequenced.IAssemblyRecipe;
import com.johan.create.foundation.utility.Lang;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.wrapper.RecipeWrapper;

@ParametersAreNonnullByDefault
public class CuttingRecipe extends ProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe {

	public CuttingRecipe(ProcessingRecipeParams params) {
		super(AllRecipeTypes.CUTTING, params);
	}

	@Override
	public boolean matches(RecipeWrapper inv, Level worldIn) {
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
		return 4;
	}

	@Override
	protected boolean canSpecifyDuration() {
		return true;
	}

	@Override
	public void addAssemblyIngredients(List<Ingredient> list) {}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Component getDescriptionForAssembly() {
		return Lang.translateDirect("recipe.assembly.cutting");
	}

	@Override
	public void addRequiredMachines(Set<ItemLike> list) {
		list.add(AllBlocks.MECHANICAL_SAW.get());
	}

	@Override
	public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
		return () -> SequencedAssemblySubCategory.AssemblyCutting::new;
	}

}
