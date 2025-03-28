package com.johan.create.content.equipment.wrench;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.johan.create.Create;
import com.johan.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.johan.create.foundation.item.render.CustomRenderedItemModel;
import com.johan.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.johan.create.foundation.item.render.PartialItemModelRenderer;
import com.johan.create.foundation.utility.AnimationTickHolder;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class WrenchItemRenderer extends CustomRenderedItemModelRenderer {

	protected static final PartialModel GEAR = new PartialModel(Create.asResource("item/wrench/gear"));

	@Override
	protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemTransforms.TransformType transformType,
		PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		renderer.render(model.getOriginalModel(), light);

		float xOffset = -1/16f;
		ms.translate(-xOffset, 0, 0);
		ms.mulPose(Vector3f.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())));
		ms.translate(xOffset, 0, 0);

		renderer.render(GEAR.get(), light);
	}

}
