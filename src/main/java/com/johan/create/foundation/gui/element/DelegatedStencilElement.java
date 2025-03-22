package com.johan.create.foundation.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.foundation.gui.UIRenderHelper;
import com.johan.create.foundation.utility.Color;

public class DelegatedStencilElement extends StencilElement {

	protected static final ElementRenderer EMPTY_RENDERER = (ms, width, height, alpha) -> {};
	protected static final ElementRenderer DEFAULT_ELEMENT = (ms, width, height, alpha) -> UIRenderHelper.angledGradient(ms, 0, -3, 5, height+4, width+6, new Color(0xff_10dd10).scaleAlpha(alpha), new Color(0xff_1010dd).scaleAlpha(alpha));

	protected ElementRenderer stencil;
	protected ElementRenderer element;

	public DelegatedStencilElement() {
		stencil = EMPTY_RENDERER;
		element = DEFAULT_ELEMENT;
	}

	public DelegatedStencilElement(ElementRenderer stencil, ElementRenderer element) {
		this.stencil = stencil;
		this.element = element;
	}

	public <T extends DelegatedStencilElement> T withStencilRenderer(ElementRenderer renderer) {
		stencil = renderer;
		//noinspection unchecked
		return (T) this;
	}

	public <T extends DelegatedStencilElement> T withElementRenderer(ElementRenderer renderer) {
		element = renderer;
		//noinspection unchecked
		return (T) this;
	}

	@Override
	protected void renderStencil(PoseStack ms) {
		stencil.render(ms, width, height, 1);
	}

	@Override
	protected void renderElement(PoseStack ms) {
		element.render(ms, width, height, alpha);
	}

	@FunctionalInterface
	public interface ElementRenderer {
		void render(PoseStack ms, int width, int height, float alpha);
	}

}
