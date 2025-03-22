package com.johan.create.foundation.ponder.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.foundation.ponder.PonderScene;
import com.johan.create.foundation.ponder.ui.PonderUI;

public abstract class PonderOverlayElement extends PonderElement {

	public void tick(PonderScene scene) {}

	public abstract void render(PonderScene scene, PonderUI screen, PoseStack ms, float partialTicks);

}
