package com.johan.create.content.equipment.goggles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllBlocks;
import com.johan.create.AllItems;
import com.johan.create.CreateClient;
import com.johan.create.compat.Mods;
import com.johan.create.content.contraptions.IDisplayAssemblyExceptions;
import com.johan.create.content.contraptions.piston.MechanicalPistonBlock;
import com.johan.create.content.contraptions.piston.PistonExtensionPoleBlock;
import com.johan.create.content.trains.entity.TrainRelocator;
import com.johan.create.foundation.blockEntity.behaviour.ValueBox;
import com.johan.create.foundation.gui.RemovedGuiUtils;
import com.johan.create.foundation.gui.Theme;
import com.johan.create.foundation.gui.element.GuiGameElement;
import com.johan.create.foundation.mixin.accessor.MouseHandlerAccessor;
import com.johan.create.foundation.outliner.Outline;
import com.johan.create.foundation.outliner.Outliner.OutlineEntry;
import com.johan.create.foundation.utility.Color;
import com.johan.create.foundation.utility.Components;
import com.johan.create.foundation.utility.Iterate;
import com.johan.create.foundation.utility.Lang;
import com.johan.create.infrastructure.config.AllConfigs;
import com.johan.create.infrastructure.config.CClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class GoggleOverlayRenderer {

	public static final IIngameOverlay OVERLAY = GoggleOverlayRenderer::renderOverlay;

	private static final Map<Object, OutlineEntry> outlines = CreateClient.OUTLINER.getOutlines();

	public static int hoverTicks = 0;
	public static BlockPos lastHovered = null;

	public static void renderOverlay(ForgeIngameGui gui, PoseStack poseStack, float partialTicks, int width,
		int height) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
			return;

		HitResult objectMouseOver = mc.hitResult;
		if (!(objectMouseOver instanceof BlockHitResult)) {
			lastHovered = null;
			hoverTicks = 0;
			return;
		}

		for (OutlineEntry entry : outlines.values()) {
			if (!entry.isAlive())
				continue;
			Outline outline = entry.getOutline();
			if (outline instanceof ValueBox && !((ValueBox) outline).isPassive)
				return;
		}

		BlockHitResult result = (BlockHitResult) objectMouseOver;
		ClientLevel world = mc.level;
		BlockPos pos = result.getBlockPos();

		int prevHoverTicks = hoverTicks;
		hoverTicks++;
		lastHovered = pos;

		pos = proxiedOverlayPosition(world, pos);

		BlockEntity be = world.getBlockEntity(pos);
		boolean wearingGoggles = GogglesItem.isWearingGoggles(mc.player);

		boolean hasGoggleInformation = be instanceof IHaveGoggleInformation;
		boolean hasHoveringInformation = be instanceof IHaveHoveringInformation;

		boolean goggleAddedInformation = false;
		boolean hoverAddedInformation = false;

		ItemStack item = AllItems.GOGGLES.asStack();

		List<Component> tooltip = new ArrayList<>();

		if (hasGoggleInformation && wearingGoggles) {
			boolean isShifting = mc.player.isShiftKeyDown();

			IHaveGoggleInformation gte = (IHaveGoggleInformation) be;
			goggleAddedInformation = gte.addToGoggleTooltip(tooltip, isShifting);
			item = gte.getIcon(isShifting);
		}

		if (hasHoveringInformation) {
			if (!tooltip.isEmpty())
				tooltip.add(Components.immutableEmpty());
			IHaveHoveringInformation hte = (IHaveHoveringInformation) be;
			hoverAddedInformation = hte.addToTooltip(tooltip, mc.player.isShiftKeyDown());

			if (goggleAddedInformation && !hoverAddedInformation)
				tooltip.remove(tooltip.size() - 1);
		}

		if (be instanceof IDisplayAssemblyExceptions) {
			boolean exceptionAdded = ((IDisplayAssemblyExceptions) be).addExceptionToTooltip(tooltip);
			if (exceptionAdded) {
				hasHoveringInformation = true;
				hoverAddedInformation = true;
			}
		}

		if (!hasHoveringInformation)
			if (hasHoveringInformation =
				hoverAddedInformation = TrainRelocator.addToTooltip(tooltip, mc.player.isShiftKeyDown()))
				hoverTicks = prevHoverTicks + 1;

		// break early if goggle or hover returned false when present
		if ((hasGoggleInformation && !goggleAddedInformation) && (hasHoveringInformation && !hoverAddedInformation)) {
			hoverTicks = 0;
			return;
		}

		// check for piston poles if goggles are worn
		BlockState state = world.getBlockState(pos);
		if (wearingGoggles && AllBlocks.PISTON_EXTENSION_POLE.has(state)) {
			Direction[] directions = Iterate.directionsInAxis(state.getValue(PistonExtensionPoleBlock.FACING)
				.getAxis());
			int poles = 1;
			boolean pistonFound = false;
			for (Direction dir : directions) {
				int attachedPoles = PistonExtensionPoleBlock.PlacementHelper.get()
					.attachedPoles(world, pos, dir);
				poles += attachedPoles;
				pistonFound |= world.getBlockState(pos.relative(dir, attachedPoles + 1))
					.getBlock() instanceof MechanicalPistonBlock;
			}

			if (!pistonFound) {
				hoverTicks = 0;
				return;
			}
			if (!tooltip.isEmpty())
				tooltip.add(Components.immutableEmpty());

			Lang.translate("gui.goggles.pole_length").text(" " + poles)
				.forGoggles(tooltip);
		}

		if (tooltip.isEmpty()) {
			hoverTicks = 0;
			return;
		}

		poseStack.pushPose();

		int tooltipTextWidth = 0;
		for (FormattedText textLine : tooltip) {
			int textLineWidth = mc.font.width(textLine);
			if (textLineWidth > tooltipTextWidth)
				tooltipTextWidth = textLineWidth;
		}

		int tooltipHeight = 8;
		if (tooltip.size() > 1) {
			tooltipHeight += 2; // gap between title lines and next lines
			tooltipHeight += (tooltip.size() - 1) * 10;
		}

		CClient cfg = AllConfigs.client();
		int posX = width / 2 + cfg.overlayOffsetX.get();
		int posY = height / 2 + cfg.overlayOffsetY.get();

		posX = Math.min(posX, width - tooltipTextWidth - 20);
		posY = Math.min(posY, height - tooltipHeight - 20);

		float fade = Mth.clamp((hoverTicks + partialTicks) / 24f, 0, 1);
		Boolean useCustom = cfg.overlayCustomColor.get();
		Color colorBackground = useCustom ? new Color(cfg.overlayBackgroundColor.get())
			: Theme.c(Theme.Key.VANILLA_TOOLTIP_BACKGROUND)
				.scaleAlpha(.75f);
		Color colorBorderTop = useCustom ? new Color(cfg.overlayBorderColorTop.get())
			: Theme.c(Theme.Key.VANILLA_TOOLTIP_BORDER, true)
				.copy();
		Color colorBorderBot = useCustom ? new Color(cfg.overlayBorderColorBot.get())
			: Theme.c(Theme.Key.VANILLA_TOOLTIP_BORDER, false)
				.copy();

		if (fade < 1) {
			poseStack.translate(Math.pow(1 - fade, 3) * Math.signum(cfg.overlayOffsetX.get() + .5f) * 8, 0, 0);
			colorBackground.scaleAlpha(fade);
			colorBorderTop.scaleAlpha(fade);
			colorBorderBot.scaleAlpha(fade);
		}

		GuiGameElement.of(item)
			.at(posX + 10, posY - 16, 450)
			.render(poseStack);

		if (!Mods.MODERNUI.isLoaded()) {
			// default tooltip rendering when modernUI is not loaded
			RemovedGuiUtils.drawHoveringText(poseStack, tooltip, posX, posY, width, height, -1, colorBackground.getRGB(),
				colorBorderTop.getRGB(), colorBorderBot.getRGB(), mc.font);

			poseStack.popPose();

			return;
		}

		/*
		 * special handling for modernUI
		 *
		 * their tooltip handler causes the overlay to jiggle each frame,
		 * if the mouse is moving, guiScale is anything but 1 and exactPositioning is enabled
		 *
		 * this is a workaround to fix this behavior
		 */
		MouseHandler mouseHandler = Minecraft.getInstance().mouseHandler;
		Window window = Minecraft.getInstance().getWindow();
		double guiScale = window.getGuiScale();
		double cursorX = mouseHandler.xpos();
		double cursorY = mouseHandler.ypos();
		((MouseHandlerAccessor) mouseHandler).create$setXPos(Math.round(cursorX / guiScale) * guiScale);
		((MouseHandlerAccessor) mouseHandler).create$setYPos(Math.round(cursorY / guiScale) * guiScale);

		RemovedGuiUtils.drawHoveringText(poseStack, tooltip, posX, posY, width, height, -1, colorBackground.getRGB(),
			colorBorderTop.getRGB(), colorBorderBot.getRGB(), mc.font);

		((MouseHandlerAccessor) mouseHandler).create$setXPos(cursorX);
		((MouseHandlerAccessor) mouseHandler).create$setYPos(cursorY);

		poseStack.popPose();

	}

	public static BlockPos proxiedOverlayPosition(Level level, BlockPos pos) {
		BlockState targetedState = level.getBlockState(pos);
		if (targetedState.getBlock() instanceof IProxyHoveringInformation proxy)
			return proxy.getInformationSource(level, pos, targetedState);
		return pos;
	}

}
