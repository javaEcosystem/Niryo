package com.johan.create.compat.jei.category;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.compat.jei.category.animations.AnimatedDeployer;
import com.johan.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.johan.create.content.processing.recipe.ProcessingOutput;
import com.johan.create.foundation.gui.AllGuiTextures;
import com.johan.create.foundation.utility.Lang;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;

@ParametersAreNonnullByDefault
public class DeployingCategory extends CreateRecipeCategory<DeployerApplicationRecipe> {

	private final AnimatedDeployer deployer = new AnimatedDeployer();

	public DeployingCategory(Info<DeployerApplicationRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, DeployerApplicationRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 27, 51)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getProcessedItem());
		IRecipeSlotBuilder handItemSlot = builder
				.addSlot(RecipeIngredientRole.INPUT, 51, 5)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getRequiredHeldItem());

		List<ProcessingOutput> results = recipe.getRollableResults();
		boolean single = results.size() == 1;
		for (int i = 0; i < results.size(); i++) {
			ProcessingOutput output = results.get(i);
			int xOffset = i % 2 == 0 ? 0 : 19;
			int yOffset = (i / 2) * -19;
			builder.addSlot(RecipeIngredientRole.OUTPUT, single ? 132 : 132 + xOffset, 51 + yOffset)
				.setBackground(getRenderedSlot(output), -1, -1)
				.addItemStack(output.getStack())
				.addTooltipCallback(addStochasticTooltip(output));
		}

		if (recipe.shouldKeepHeldItem())
			handItemSlot.addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(1, Lang.translateDirect("recipe.deploying.not_consumed").withStyle(ChatFormatting.GOLD)));

	}

	@Override
	public void draw(DeployerApplicationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		AllGuiTextures.JEI_SHADOW.render(matrixStack, 62, 57);
		AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 126, 29 + (recipe.getRollableResults().size() > 2 ? -19 : 0));
		deployer.draw(matrixStack, getBackground().getWidth() / 2 - 13, 22);
	}

}
