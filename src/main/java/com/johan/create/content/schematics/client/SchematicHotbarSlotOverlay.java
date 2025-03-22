package com.johan.create.content.schematics.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.foundation.gui.AllGuiTextures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

public class SchematicHotbarSlotOverlay extends GuiComponent {

	public void renderOn(PoseStack matrixStack, int slot) {
		Window mainWindow = Minecraft.getInstance().getWindow();
		int x = mainWindow.getGuiScaledWidth() / 2 - 88;
		int y = mainWindow.getGuiScaledHeight() - 19;
		RenderSystem.enableDepthTest();
		matrixStack.pushPose();
		AllGuiTextures.SCHEMATIC_SLOT.render(matrixStack, x + 20 * slot, y, this);
		matrixStack.popPose();
	}

}
