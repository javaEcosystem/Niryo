package com.johan.create.foundation.ponder.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.foundation.ponder.PonderScene;
import com.johan.create.foundation.ponder.ui.PonderUI;
import com.johan.create.foundation.utility.animation.LerpedFloat;

public abstract class AnimatedOverlayElement extends PonderOverlayElement {

	protected LerpedFloat fade;

	public AnimatedOverlayElement() {
		fade = LerpedFloat.linear()
			.startWithValue(0);
	}

	public void setFade(float fade) {
		this.fade.setValue(fade);
	}

	@Override
	public final void render(PonderScene scene, PonderUI screen, PoseStack ms, float partialTicks) {
		float currentFade = fade.getValue(partialTicks);
		render(scene, screen, ms, partialTicks, currentFade);
	}

	protected abstract void render(PonderScene scene, PonderUI screen, PoseStack ms, float partialTicks, float fade);

}
