package com.johan.create.content.processing.sequenced;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import com.johan.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.johan.create.foundation.fluid.FluidIngredient;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IAssemblyRecipe {

	default boolean supportsAssembly() {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	public Component getDescriptionForAssembly();

	public void addRequiredMachines(Set<ItemLike> list);

	public void addAssemblyIngredients(List<Ingredient> list);

	default void addAssemblyFluidIngredients(List<FluidIngredient> list) {}

	public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory();

}
