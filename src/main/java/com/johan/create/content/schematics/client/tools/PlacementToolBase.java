package com.johan.create.content.schematics.client.tools;

import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.foundation.render.SuperRenderTypeBuffer;

import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.ForgeIngameGui;

public abstract class PlacementToolBase extends SchematicToolBase {

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void updateSelection() {
		super.updateSelection();
	}

	@Override
	public void renderTool(PoseStack ms, SuperRenderTypeBuffer buffer, Vec3 camera) {
		super.renderTool(ms, buffer, camera);
	}

	@Override
	public void renderOverlay(ForgeIngameGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
		super.renderOverlay(gui, poseStack, partialTicks, width, height);
	}

	@Override
	public boolean handleMouseWheel(double delta) {
		return false;
	}

	@Override
	public boolean handleRightClick() {
		return false;
	}

}
