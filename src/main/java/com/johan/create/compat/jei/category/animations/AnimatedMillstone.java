package com.johan.create.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllBlocks;
import com.johan.create.AllPartialModels;
import com.johan.create.foundation.gui.AllGuiTextures;

public class AnimatedMillstone extends AnimatedKinetics {

	@Override
	public void draw(PoseStack matrixStack, int xOffset, int yOffset) {
		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 0);
		AllGuiTextures.JEI_SHADOW.render(matrixStack, -16, 13);
		matrixStack.translate(-2, 18, 0);
		int scale = 22;

		blockElement(AllPartialModels.MILLSTONE_COG)
			.rotateBlock(22.5, getCurrentAngle() * 2, 0)
			.scale(scale)
			.render(matrixStack);

		blockElement(AllBlocks.MILLSTONE.getDefaultState())
			.rotateBlock(22.5, 22.5, 0)
			.scale(scale)
			.render(matrixStack);

		matrixStack.popPose();
	}

}
