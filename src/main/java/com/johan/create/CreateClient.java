package com.johan.create;

import com.johan.create.content.contraptions.glue.SuperGlueSelectionHandler;
import com.johan.create.content.contraptions.render.ContraptionRenderDispatcher;
import com.johan.create.content.contraptions.render.SBBContraptionManager;
import com.johan.create.content.decoration.encasing.CasingConnectivity;
import com.johan.create.content.equipment.armor.RemainingAirOverlay;
import com.johan.create.content.equipment.bell.SoulPulseEffectHandler;
import com.johan.create.content.equipment.blueprint.BlueprintOverlayRenderer;
import com.johan.create.content.equipment.goggles.GoggleOverlayRenderer;
import com.johan.create.content.equipment.potatoCannon.PotatoCannonRenderHandler;
import com.johan.create.content.equipment.toolbox.ToolboxHandlerClient;
import com.johan.create.content.equipment.zapper.ZapperRenderHandler;
import com.johan.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.johan.create.content.kinetics.waterwheel.WaterWheelRenderer;
import com.johan.create.content.redstone.link.controller.LinkedControllerClientHandler;
import com.johan.create.content.schematics.client.ClientSchematicLoader;
import com.johan.create.content.schematics.client.SchematicAndQuillHandler;
import com.johan.create.content.schematics.client.SchematicHandler;
import com.johan.create.content.trains.GlobalRailwayManager;
import com.johan.create.content.trains.TrainHUD;
import com.johan.create.content.trains.track.TrackPlacementOverlay;
import com.johan.create.foundation.ClientResourceReloadListener;
import com.johan.create.foundation.blockEntity.behaviour.ValueSettingsClient;
import com.johan.create.foundation.gui.UIRenderHelper;
import com.johan.create.foundation.outliner.Outliner;
import com.johan.create.foundation.ponder.element.WorldSectionElement;
import com.johan.create.foundation.render.CachedBufferer;
import com.johan.create.foundation.render.CreateContexts;
import com.johan.create.foundation.render.SuperByteBufferCache;
import com.johan.create.foundation.utility.Components;
import com.johan.create.foundation.utility.ModelSwapper;
import com.johan.create.foundation.utility.ghost.GhostBlocks;
import com.johan.create.infrastructure.config.AllConfigs;
import com.johan.create.infrastructure.ponder.AllPonderTags;
import com.johan.create.infrastructure.ponder.PonderIndex;

import net.minecraft.ChatFormatting;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateClient {

	public static final SuperByteBufferCache BUFFER_CACHE = new SuperByteBufferCache();
	public static final Outliner OUTLINER = new Outliner();
	public static final GhostBlocks GHOST_BLOCKS = new GhostBlocks();
	public static final ModelSwapper MODEL_SWAPPER = new ModelSwapper();
	public static final CasingConnectivity CASING_CONNECTIVITY = new CasingConnectivity();

	public static final ClientSchematicLoader SCHEMATIC_SENDER = new ClientSchematicLoader();
	public static final SchematicHandler SCHEMATIC_HANDLER = new SchematicHandler();
	public static final SchematicAndQuillHandler SCHEMATIC_AND_QUILL_HANDLER = new SchematicAndQuillHandler();
	public static final SuperGlueSelectionHandler GLUE_HANDLER = new SuperGlueSelectionHandler();

	public static final ZapperRenderHandler ZAPPER_RENDER_HANDLER = new ZapperRenderHandler();
	public static final PotatoCannonRenderHandler POTATO_CANNON_RENDER_HANDLER = new PotatoCannonRenderHandler();
	public static final SoulPulseEffectHandler SOUL_PULSE_EFFECT_HANDLER = new SoulPulseEffectHandler();
	public static final GlobalRailwayManager RAILWAYS = new GlobalRailwayManager();
	public static final ValueSettingsClient VALUE_SETTINGS_HANDLER = new ValueSettingsClient();

	public static final ClientResourceReloadListener RESOURCE_RELOAD_LISTENER = new ClientResourceReloadListener();

	public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
		modEventBus.addListener(CreateClient::clientInit);
		modEventBus.addListener(AllParticleTypes::registerFactories);
		modEventBus.addListener(CreateContexts::flwInit);
		modEventBus.addListener(ContraptionRenderDispatcher::gatherContext);

		MODEL_SWAPPER.registerListeners(modEventBus);

		ZAPPER_RENDER_HANDLER.registerListeners(forgeEventBus);
		POTATO_CANNON_RENDER_HANDLER.registerListeners(forgeEventBus);
	}

	public static void clientInit(final FMLClientSetupEvent event) {
		BUFFER_CACHE.registerCompartment(CachedBufferer.GENERIC_BLOCK);
		BUFFER_CACHE.registerCompartment(CachedBufferer.PARTIAL);
		BUFFER_CACHE.registerCompartment(CachedBufferer.DIRECTIONAL_PARTIAL);
		BUFFER_CACHE.registerCompartment(KineticBlockEntityRenderer.KINETIC_BLOCK);
		BUFFER_CACHE.registerCompartment(WaterWheelRenderer.WATER_WHEEL);
		BUFFER_CACHE.registerCompartment(SBBContraptionManager.CONTRAPTION, 20);
		BUFFER_CACHE.registerCompartment(WorldSectionElement.DOC_WORLD_SECTION, 20);

		AllKeys.register();
		AllPartialModels.init();

		AllPonderTags.register();
		PonderIndex.register();

		registerOverlays();

		UIRenderHelper.init();
	}

	private static void registerOverlays() {
		// Register overlays in reverse order
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.AIR_LEVEL_ELEMENT, "Create's Remaining Air", RemainingAirOverlay.INSTANCE);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "Create's Train Driver HUD", TrainHUD.OVERLAY);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "Create's Goggle Information", GoggleOverlayRenderer.OVERLAY);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "Create's Blueprints", BlueprintOverlayRenderer.OVERLAY);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "Create's Linked Controller", LinkedControllerClientHandler.OVERLAY);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "Create's Schematics", SCHEMATIC_HANDLER.getOverlayRenderer());
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "Create's Toolboxes", ToolboxHandlerClient.OVERLAY);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "Create's Value Settings", VALUE_SETTINGS_HANDLER);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "Create's Track Placement", TrackPlacementOverlay.OVERLAY);
	}

	public static void invalidateRenderers() {
		BUFFER_CACHE.invalidate();

		SCHEMATIC_HANDLER.updateRenderers();
		ContraptionRenderDispatcher.reset();
	}

	public static void checkGraphicsFanciness() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null)
			return;

		if (mc.options.graphicsMode != GraphicsStatus.FABULOUS)
			return;

		if (AllConfigs.client().ignoreFabulousWarning.get())
			return;

		MutableComponent text = ComponentUtils.wrapInSquareBrackets(Components.literal("WARN"))
			.withStyle(ChatFormatting.GOLD)
			.append(Components.literal(
				" Some of Create's visual features will not be available while Fabulous graphics are enabled!"))
			.withStyle(style -> style
				.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/create dismissFabulousWarning"))
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					Components.literal("Click here to disable this warning"))));

		mc.player.displayClientMessage(text, false);
	}

}
