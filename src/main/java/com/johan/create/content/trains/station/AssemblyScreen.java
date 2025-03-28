package com.johan.create.content.trains.station;

import java.lang.ref.WeakReference;
import java.util.List;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllPackets;
import com.johan.create.AllPartialModels;
import com.johan.create.content.contraptions.AssemblyException;
import com.johan.create.content.trains.entity.Carriage;
import com.johan.create.content.trains.entity.Train;
import com.johan.create.content.trains.entity.TrainIconType;
import com.johan.create.foundation.gui.AllGuiTextures;
import com.johan.create.foundation.gui.AllIcons;
import com.johan.create.foundation.gui.widget.IconButton;
import com.johan.create.foundation.gui.widget.ScrollInput;
import com.johan.create.foundation.utility.Lang;

import net.minecraft.client.gui.components.Widget;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class AssemblyScreen extends AbstractStationScreen {

	private IconButton quitAssembly;
	private IconButton toggleAssemblyButton;
	private List<ResourceLocation> iconTypes;
	private ScrollInput iconTypeScroll;

	public AssemblyScreen(StationBlockEntity be, GlobalStation station) {
		super(be, station);
		background = AllGuiTextures.STATION_ASSEMBLING;
	}

	@Override
	protected void init() {
		super.init();
		int x = guiLeft;
		int y = guiTop;
		int by = y + background.height - 24;

		Widget widget = renderables.get(0);
		if (widget instanceof IconButton ib) {
			ib.setIcon(AllIcons.I_PRIORITY_VERY_LOW);
			ib.setToolTip(Lang.translateDirect("station.close"));
		}

		iconTypes = TrainIconType.REGISTRY.keySet()
			.stream()
			.toList();
		iconTypeScroll = new ScrollInput(x + 4, y + 17, 184, 14).titled(Lang.translateDirect("station.icon_type"));
		iconTypeScroll.withRange(0, iconTypes.size());
		iconTypeScroll.withStepFunction(ctx -> -iconTypeScroll.standardStep()
			.apply(ctx));
		iconTypeScroll.calling(s -> {
			Train train = displayedTrain.get();
			if (train != null)
				train.icon = TrainIconType.byId(iconTypes.get(s));
		});
		iconTypeScroll.active = iconTypeScroll.visible = false;
		addRenderableWidget(iconTypeScroll);

		toggleAssemblyButton = new WideIconButton(x + 94, by, AllGuiTextures.I_ASSEMBLE_TRAIN);
		toggleAssemblyButton.active = false;
		toggleAssemblyButton.setToolTip(Lang.translateDirect("station.assemble_train"));
		toggleAssemblyButton.withCallback(() -> {
			AllPackets.getChannel()
				.sendToServer(StationEditPacket.tryAssemble(blockEntity.getBlockPos()));
		});

		quitAssembly = new IconButton(x + 73, by, AllIcons.I_DISABLE);
		quitAssembly.active = true;
		quitAssembly.setToolTip(Lang.translateDirect("station.cancel"));
		quitAssembly.withCallback(() -> {
			AllPackets.getChannel()
				.sendToServer(StationEditPacket.configure(blockEntity.getBlockPos(), false, station.name, null));
			minecraft.setScreen(new StationScreen(blockEntity, station));
		});

		addRenderableWidget(toggleAssemblyButton);
		addRenderableWidget(quitAssembly);

		tickTrainDisplay();
	}

	@Override
	public void tick() {
		super.tick();
		tickTrainDisplay();
		Train train = displayedTrain.get();
		toggleAssemblyButton.active = blockEntity.bogeyCount > 0 || train != null;

		if (train != null) {
			AllPackets.getChannel()
				.sendToServer(StationEditPacket.configure(blockEntity.getBlockPos(), false, station.name, null));
			minecraft.setScreen(new StationScreen(blockEntity, station));
			for (Carriage carriage : train.carriages)
				carriage.updateConductors();
		}
	}

	private void tickTrainDisplay() {
		if (getImminent() == null) {
			displayedTrain = new WeakReference<>(null);
			quitAssembly.active = true;
			iconTypeScroll.active = iconTypeScroll.visible = false;
			toggleAssemblyButton.setToolTip(Lang.translateDirect("station.assemble_train"));
			toggleAssemblyButton.setIcon(AllGuiTextures.I_ASSEMBLE_TRAIN);
			toggleAssemblyButton.withCallback(() -> {
				AllPackets.getChannel()
					.sendToServer(StationEditPacket.tryAssemble(blockEntity.getBlockPos()));
			});
		} else {
			AllPackets.getChannel()
				.sendToServer(StationEditPacket.configure(blockEntity.getBlockPos(), false, station.name, null));
			minecraft.setScreen(new StationScreen(blockEntity, station));
		}
	}

	@Override
	protected void renderWindow(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		super.renderWindow(ms, mouseX, mouseY, partialTicks);
		int x = guiLeft;
		int y = guiTop;

		MutableComponent header = Lang.translateDirect("station.assembly_title");
		font.draw(ms, header, x + background.width / 2 - font.width(header) / 2, y + 4, 0x0E2233);

		AssemblyException lastAssemblyException = blockEntity.lastException;
		if (lastAssemblyException != null) {
			MutableComponent text = Lang.translateDirect("station.failed");
			font.draw(ms, text, x + 97 - font.width(text) / 2, y + 47, 0x775B5B);
			int offset = 0;
			if (blockEntity.failedCarriageIndex != -1) {
				font.draw(ms, Lang.translateDirect("station.carriage_number", blockEntity.failedCarriageIndex), x + 30,
					y + 67, 0x7A7A7A);
				offset += 10;
			}
			font.drawWordWrap(lastAssemblyException.component, x + 30, y + 67 + offset, 134, 0x775B5B);
			offset += font.split(lastAssemblyException.component, 134)
				.size() * 9 + 5;
			font.drawWordWrap(Lang.translateDirect("station.retry"), x + 30, y + 67 + offset, 134, 0x7A7A7A);
			return;
		}

		int bogeyCount = blockEntity.bogeyCount;

		MutableComponent text = Lang.translateDirect(
			bogeyCount == 0 ? "station.no_bogeys" : bogeyCount == 1 ? "station.one_bogey" : "station.more_bogeys",
			bogeyCount);
		font.draw(ms, text, x + 97 - font.width(text) / 2, y + 47, 0x7A7A7A);

		font.drawWordWrap(Lang.translateDirect("station.how_to"), x + 28, y + 62, 134, 0x7A7A7A);
		font.drawWordWrap(Lang.translateDirect("station.how_to_1"), x + 28, y + 94, 134, 0x7A7A7A);
		font.drawWordWrap(Lang.translateDirect("station.how_to_2"), x + 28, y + 117, 138, 0x7A7A7A);
	}

	@Override
	public void removed() {
		super.removed();
		Train train = displayedTrain.get();
		if (train != null) {
			ResourceLocation iconId = iconTypes.get(iconTypeScroll.getState());
			train.icon = TrainIconType.byId(iconId);
			AllPackets.getChannel()
				.sendToServer(new TrainEditPacket(train.id, "", iconId));
		}
	}

	@Override
	protected PartialModel getFlag(float partialTicks) {
		return AllPartialModels.STATION_ASSEMBLE;
	}

}
