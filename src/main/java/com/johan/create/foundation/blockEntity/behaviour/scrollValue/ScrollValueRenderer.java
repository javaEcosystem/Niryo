package com.johan.create.foundation.blockEntity.behaviour.scrollValue;

import java.util.ArrayList;
import java.util.List;

import com.johan.create.AllBlocks;
import com.johan.create.AllItems;
import com.johan.create.AllKeys;
import com.johan.create.CreateClient;
import com.johan.create.foundation.blockEntity.SmartBlockEntity;
import com.johan.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.johan.create.foundation.blockEntity.behaviour.ValueBox;
import com.johan.create.foundation.blockEntity.behaviour.ValueBox.IconValueBox;
import com.johan.create.foundation.blockEntity.behaviour.ValueBox.TextValueBox;
import com.johan.create.foundation.utility.Components;
import com.johan.create.foundation.utility.Lang;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ScrollValueRenderer {

	public static void tick() {
		Minecraft mc = Minecraft.getInstance();
		HitResult target = mc.hitResult;
		if (target == null || !(target instanceof BlockHitResult))
			return;

		BlockHitResult result = (BlockHitResult) target;
		ClientLevel world = mc.level;
		BlockPos pos = result.getBlockPos();
		Direction face = result.getDirection();

		ScrollValueBehaviour behaviour = BlockEntityBehaviour.get(world, pos, ScrollValueBehaviour.TYPE);
		if (behaviour == null)
			return;
		if (!behaviour.isActive()) {
			CreateClient.OUTLINER.remove(pos);
			return;
		}
		ItemStack mainhandItem = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
		boolean clipboard = AllBlocks.CLIPBOARD.isIn(mainhandItem);
		if (behaviour.needsWrench && !AllItems.WRENCH.isIn(mainhandItem) && !clipboard)
			return;
		boolean highlight = behaviour.testHit(target.getLocation()) && !clipboard;

		if (behaviour instanceof BulkScrollValueBehaviour && AllKeys.ctrlDown()) {
			BulkScrollValueBehaviour bulkScrolling = (BulkScrollValueBehaviour) behaviour;
			for (SmartBlockEntity smartBlockEntity : bulkScrolling.getBulk()) {
				ScrollValueBehaviour other = smartBlockEntity.getBehaviour(ScrollValueBehaviour.TYPE);
				if (other != null)
					addBox(world, smartBlockEntity.getBlockPos(), face, other, highlight);
			}
		} else
			addBox(world, pos, face, behaviour, highlight);

		if (!highlight)
			return;

		List<MutableComponent> tip = new ArrayList<>();
		tip.add(behaviour.label.copy());
		tip.add(Lang.translateDirect("gui.value_settings.hold_to_edit"));
		CreateClient.VALUE_SETTINGS_HANDLER.showHoverTip(tip);
	}

	protected static void addBox(ClientLevel world, BlockPos pos, Direction face, ScrollValueBehaviour behaviour,
		boolean highlight) {
		AABB bb = new AABB(Vec3.ZERO, Vec3.ZERO).inflate(.5f)
			.contract(0, 0, -.5f)
			.move(0, 0, -.125f);
		Component label = behaviour.label;
		ValueBox box;

		if (behaviour instanceof ScrollOptionBehaviour) {
			box = new IconValueBox(label, ((ScrollOptionBehaviour<?>) behaviour).getIconForSelected(), bb, pos);
		} else {
			box = new TextValueBox(label, bb, pos, Components.literal(behaviour.formatValue()));
		}

		box.passive(!highlight)
			.wideOutline();

		CreateClient.OUTLINER.showValueBox(pos, box.transform(behaviour.slotPositioning))
			.highlightFace(face);
	}

}
