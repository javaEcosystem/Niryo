package com.johan.create.content.redstone.displayLink;

import java.util.Collections;
import java.util.List;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllPackets;
import com.johan.create.content.redstone.displayLink.source.DisplaySource;
import com.johan.create.content.redstone.displayLink.source.SingleLineDisplaySource;
import com.johan.create.content.redstone.displayLink.target.DisplayTarget;
import com.johan.create.content.redstone.displayLink.target.DisplayTargetStats;
import com.johan.create.foundation.gui.AbstractSimiScreen;
import com.johan.create.foundation.gui.AllGuiTextures;
import com.johan.create.foundation.gui.AllIcons;
import com.johan.create.foundation.gui.ModularGuiLine;
import com.johan.create.foundation.gui.ModularGuiLineBuilder;
import com.johan.create.foundation.gui.ScreenOpener;
import com.johan.create.foundation.gui.element.GuiGameElement;
import com.johan.create.foundation.gui.widget.AbstractSimiWidget;
import com.johan.create.foundation.gui.widget.ElementWidget;
import com.johan.create.foundation.gui.widget.IconButton;
import com.johan.create.foundation.gui.widget.Label;
import com.johan.create.foundation.gui.widget.ScrollInput;
import com.johan.create.foundation.gui.widget.SelectionScrollInput;
import com.johan.create.foundation.ponder.ui.PonderTagScreen;
import com.johan.create.foundation.utility.Components;
import com.johan.create.foundation.utility.Couple;
import com.johan.create.foundation.utility.Lang;
import com.johan.create.infrastructure.ponder.AllPonderTags;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


public class DisplayLinkScreen extends AbstractSimiScreen {

	private static final ItemStack FALLBACK = new ItemStack(Items.BARRIER);

	private AllGuiTextures background;
	private DisplayLinkBlockEntity blockEntity;
	private IconButton confirmButton;

	BlockState sourceState;
	BlockState targetState;
	List<DisplaySource> sources;
	DisplayTarget target;

	ScrollInput sourceTypeSelector;
	Label sourceTypeLabel;
	ScrollInput targetLineSelector;
	Label targetLineLabel;
	AbstractSimiWidget sourceWidget;
	AbstractSimiWidget targetWidget;

	Couple<ModularGuiLine> configWidgets;

	public DisplayLinkScreen(DisplayLinkBlockEntity be) {
		this.background = AllGuiTextures.DATA_GATHERER;
		this.blockEntity = be;
		sources = Collections.emptyList();
		configWidgets = Couple.create(ModularGuiLine::new);
		target = null;
	}

	@Override
	protected void init() {
		setWindowSize(background.width, background.height);
		super.init();
		clearWidgets();

		int x = guiLeft;
		int y = guiTop;


		initGathererOptions();

		confirmButton = new IconButton(x + background.width - 33, y + background.height - 24, AllIcons.I_CONFIRM);
		confirmButton.withCallback(this::onClose);
		addRenderableWidget(confirmButton);
	}

	@Override
	public void tick() {
		super.tick();
		if (sourceState != null && sourceState.getBlock() != minecraft.level.getBlockState(blockEntity.getSourcePosition())
				.getBlock()
				|| targetState != null && targetState.getBlock() != minecraft.level.getBlockState(blockEntity.getTargetPosition())
				.getBlock())
			initGathererOptions();
	}

	@SuppressWarnings("deprecation")
	private void initGathererOptions() {
		ClientLevel level = minecraft.level;
		sourceState = level.getBlockState(blockEntity.getSourcePosition());
		targetState = level.getBlockState(blockEntity.getTargetPosition());

		ItemStack asItem;
		int x = guiLeft;
		int y = guiTop;

		Block sourceBlock = sourceState.getBlock();
		Block targetBlock = targetState.getBlock();

		asItem = sourceBlock.getCloneItemStack(level, blockEntity.getSourcePosition(), sourceState);
		ItemStack sourceIcon = asItem == null || asItem.isEmpty() ? FALLBACK : asItem;
		asItem = targetBlock.getCloneItemStack(level, blockEntity.getTargetPosition(), targetState);
		ItemStack targetIcon = asItem == null || asItem.isEmpty() ? FALLBACK : asItem;

		sources = AllDisplayBehaviours.sourcesOf(level, blockEntity.getSourcePosition());
		target = AllDisplayBehaviours.targetOf(level, blockEntity.getTargetPosition());

		removeWidget(targetLineSelector);
		removeWidget(targetLineLabel);
		removeWidget(sourceTypeSelector);
		removeWidget(sourceTypeLabel);
		removeWidget(sourceWidget);
		removeWidget(targetWidget);

		configWidgets.forEach(s -> s.forEach(this::removeWidget));

		targetLineSelector = null;
		sourceTypeSelector = null;

		if (target != null) {
			DisplayTargetStats stats = target.provideStats(new DisplayLinkContext(level, blockEntity));
			int rows = stats.maxRows();
			int startIndex = Math.min(blockEntity.targetLine, rows);

			targetLineLabel = new Label(x + 65, y + 109, Components.immutableEmpty()).withShadow();
			targetLineLabel.text = target.getLineOptionText(startIndex);

			if (rows > 1) {
				targetLineSelector = new ScrollInput(x + 61, y + 105, 135, 16).withRange(0, rows)
						.titled(Lang.translateDirect("display_link.display_on"))
						.inverted()
						.calling(i -> targetLineLabel.text = target.getLineOptionText(i))
						.setState(startIndex);
				addRenderableWidget(targetLineSelector);
			}

			addRenderableWidget(targetLineLabel);
		}

		sourceWidget = new ElementWidget(x + 37, y + 26)
				.showingElement(GuiGameElement.of(sourceIcon))
				.withCallback((mX, mY) -> {
					ScreenOpener.open(new PonderTagScreen(AllPonderTags.DISPLAY_SOURCES));
				});

		sourceWidget.getToolTip().addAll(List.of(
				Lang.translateDirect("display_link.reading_from"),
				sourceState.getBlock().getName()
						.withStyle(s -> s.withColor(sources.isEmpty() ? 0xF68989 : 0xF2C16D)),
				Lang.translateDirect("display_link.attached_side"),
				Lang.translateDirect("display_link.view_compatible")
						.withStyle(ChatFormatting.GRAY)
		));

		addRenderableWidget(sourceWidget);

		targetWidget = new ElementWidget(x + 37, y + 105)
				.showingElement(GuiGameElement.of(targetIcon))
				.withCallback((mX, mY) -> {
					ScreenOpener.open(new PonderTagScreen(AllPonderTags.DISPLAY_TARGETS));
				});

		targetWidget.getToolTip().addAll(List.of(
				Lang.translateDirect("display_link.writing_to"),
				targetState.getBlock().getName()
						.withStyle(s -> s.withColor(target == null ? 0xF68989 : 0xF2C16D)),
				Lang.translateDirect("display_link.targeted_location"),
				Lang.translateDirect("display_link.view_compatible")
						.withStyle(ChatFormatting.GRAY)
		));

		addRenderableWidget(targetWidget);

		if (!sources.isEmpty()) {
			int startIndex = Math.max(sources.indexOf(blockEntity.activeSource), 0);

			sourceTypeLabel = new Label(x + 65, y + 30, Components.immutableEmpty()).withShadow();
			sourceTypeLabel.text = sources.get(startIndex)
					.getName();

			if (sources.size() > 1) {
				List<Component> options = sources.stream()
						.map(DisplaySource::getName)
						.toList();
				sourceTypeSelector = new SelectionScrollInput(x + 61, y + 26, 135, 16).forOptions(options)
						.writingTo(sourceTypeLabel)
						.titled(Lang.translateDirect("display_link.information_type"))
						.calling(this::initGathererSourceSubOptions)
						.setState(startIndex);
				sourceTypeSelector.onChanged();
				addRenderableWidget(sourceTypeSelector);
			} else
				initGathererSourceSubOptions(0);

			addRenderableWidget(sourceTypeLabel);
		}

	}

	private void initGathererSourceSubOptions(int i) {
		DisplaySource source = sources.get(i);
		source.populateData(new DisplayLinkContext(blockEntity.getLevel(), blockEntity));

		if (targetLineSelector != null)
			targetLineSelector
					.titled(source instanceof SingleLineDisplaySource ? Lang.translateDirect("display_link.display_on")
							: Lang.translateDirect("display_link.display_on_multiline"));

		configWidgets.forEach(s -> {
			s.forEach(this::removeWidget);
			s.clear();
		});

		DisplayLinkContext context = new DisplayLinkContext(minecraft.level, blockEntity);
		configWidgets.forEachWithContext((s, first) -> source.initConfigurationWidgets(context,
				new ModularGuiLineBuilder(font, s, guiLeft + 60, guiTop + (first ? 51 : 72)), first));
		configWidgets
				.forEach(s -> s.loadValues(blockEntity.getSourceConfig(), this::addRenderableWidget, this::addRenderableOnly));
	}

	@Override
	public void onClose() {
		super.onClose();
		CompoundTag sourceData = new CompoundTag();

		if (!sources.isEmpty()) {
			sourceData.putString("Id",
					sources.get(sourceTypeSelector == null ? 0 : sourceTypeSelector.getState()).id.toString());
			configWidgets.forEach(s -> s.saveValues(sourceData));
		}

		AllPackets.getChannel().sendToServer(new DisplayLinkConfigurationPacket(blockEntity.getBlockPos(), sourceData,
				targetLineSelector == null ? 0 : targetLineSelector.getState()));
	}

	@Override
	protected void renderWindow(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		int x = guiLeft;
		int y = guiTop;

		background.render(ms, x, y, this);
		MutableComponent header = Lang.translateDirect("display_link.title");
		font.draw(ms, header, x + background.width / 2 - font.width(header) / 2, y + 4, 0x592424);

		if (sources.isEmpty())
			font.drawShadow(ms, Lang.translateDirect("display_link.no_source"), x + 65, y + 30, 0xD3D3D3);
		if (target == null)
			font.drawShadow(ms, Lang.translateDirect("display_link.no_target"), x + 65, y + 109, 0xD3D3D3);

		ms.pushPose();
		ms.translate(0, guiTop + 46, 0);
		configWidgets.getFirst()
				.renderWidgetBG(guiLeft, ms);
		ms.translate(0, 21, 0);
		configWidgets.getSecond()
				.renderWidgetBG(guiLeft, ms);
		ms.popPose();

		ms.pushPose();
		TransformStack.cast(ms)
				.pushPose()
				.translate(x + background.width + 4, y + background.height + 4, 100)
				.scale(40)
				.rotateX(-22)
				.rotateY(63);
		GuiGameElement.of(blockEntity.getBlockState()
						.setValue(DisplayLinkBlock.FACING, Direction.UP))
				.render(ms);
		ms.popPose();
	}

	@Override
	protected void removeWidget(GuiEventListener p_169412_) {
		if (p_169412_ != null)
			super.removeWidget(p_169412_);
	}

}
