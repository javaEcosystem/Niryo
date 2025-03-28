package com.johan.create.foundation.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.AllFluids;
import com.johan.create.AllPackets;
import com.johan.create.Create;
import com.johan.create.CreateClient;
import com.johan.create.content.contraptions.ContraptionHandler;
import com.johan.create.content.contraptions.actors.seat.ContraptionPlayerPassengerRotation;
import com.johan.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.johan.create.content.contraptions.chassis.ChassisRangeDisplay;
import com.johan.create.content.contraptions.minecart.CouplingHandlerClient;
import com.johan.create.content.contraptions.minecart.CouplingPhysics;
import com.johan.create.content.contraptions.minecart.CouplingRenderer;
import com.johan.create.content.contraptions.minecart.capability.CapabilityMinecartController;
import com.johan.create.content.contraptions.render.ContraptionRenderDispatcher;
import com.johan.create.content.decoration.girder.GirderWrenchBehavior;
import com.johan.create.content.equipment.armor.BacktankArmorLayer;
import com.johan.create.content.equipment.armor.DivingHelmetItem;
import com.johan.create.content.equipment.armor.NetheriteBacktankFirstPersonRenderer;
import com.johan.create.content.equipment.armor.NetheriteDivingHandler;
import com.johan.create.content.equipment.blueprint.BlueprintOverlayRenderer;
import com.johan.create.content.equipment.clipboard.ClipboardValueSettingsHandler;
import com.johan.create.content.equipment.extendoGrip.ExtendoGripRenderHandler;
import com.johan.create.content.equipment.toolbox.ToolboxHandlerClient;
import com.johan.create.content.equipment.zapper.ZapperItem;
import com.johan.create.content.equipment.zapper.terrainzapper.WorldshaperRenderHandler;
import com.johan.create.content.kinetics.KineticDebugger;
import com.johan.create.content.kinetics.belt.item.BeltConnectorHandler;
import com.johan.create.content.kinetics.fan.AirCurrent;
import com.johan.create.content.kinetics.mechanicalArm.ArmInteractionPointHandler;
import com.johan.create.content.kinetics.turntable.TurntableHandler;
import com.johan.create.content.logistics.depot.EjectorTargetHandler;
import com.johan.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.johan.create.content.redstone.displayLink.DisplayLinkBlockItem;
import com.johan.create.content.redstone.link.LinkRenderer;
import com.johan.create.content.redstone.link.controller.LinkedControllerClientHandler;
import com.johan.create.content.trains.CameraDistanceModifier;
import com.johan.create.content.trains.TrainHUD;
import com.johan.create.content.trains.entity.CarriageContraptionEntity;
import com.johan.create.content.trains.entity.CarriageCouplingRenderer;
import com.johan.create.content.trains.entity.TrainRelocator;
import com.johan.create.content.trains.schedule.TrainHatArmorLayer;
import com.johan.create.content.trains.track.CurvedTrackInteraction;
import com.johan.create.content.trains.track.TrackBlockOutline;
import com.johan.create.content.trains.track.TrackPlacement;
import com.johan.create.content.trains.track.TrackTargetingClient;
import com.johan.create.foundation.blockEntity.behaviour.edgeInteraction.EdgeInteractionRenderer;
import com.johan.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import com.johan.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.johan.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueRenderer;
import com.johan.create.foundation.config.ui.BaseConfigScreen;
import com.johan.create.foundation.fluid.FluidHelper;
import com.johan.create.foundation.item.TooltipModifier;
import com.johan.create.foundation.networking.LeftClickPacket;
import com.johan.create.foundation.placement.PlacementHelpers;
import com.johan.create.foundation.ponder.PonderTooltipHandler;
import com.johan.create.foundation.render.SuperRenderTypeBuffer;
import com.johan.create.foundation.sound.SoundScapes;
import com.johan.create.foundation.utility.AnimationTickHolder;
import com.johan.create.foundation.utility.CameraAngleAnimationService;
import com.johan.create.foundation.utility.ServerSpeedProvider;
import com.johan.create.foundation.utility.worldWrappers.WrappedClientWorld;
import com.johan.create.infrastructure.config.AllConfigs;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void onTick(ClientTickEvent event) {
		if (!isGameActive())
			return;

		Level world = Minecraft.getInstance().level;
		if (event.phase == Phase.START) {
			LinkedControllerClientHandler.tick();
			ControlsHandler.tick();
			AirCurrent.tickClientPlayerSounds();
			return;
		}

		SoundScapes.tick();
		AnimationTickHolder.tick();

		CreateClient.SCHEMATIC_SENDER.tick();
		CreateClient.SCHEMATIC_AND_QUILL_HANDLER.tick();
		CreateClient.GLUE_HANDLER.tick();
		CreateClient.SCHEMATIC_HANDLER.tick();
		CreateClient.ZAPPER_RENDER_HANDLER.tick();
		CreateClient.POTATO_CANNON_RENDER_HANDLER.tick();
		CreateClient.SOUL_PULSE_EFFECT_HANDLER.tick(world);
		CreateClient.RAILWAYS.clientTick();

		ContraptionHandler.tick(world);
		CapabilityMinecartController.tick(world);
		CouplingPhysics.tick(world);

		PonderTooltipHandler.tick();
		// ScreenOpener.tick();
		ServerSpeedProvider.clientTick();
		BeltConnectorHandler.tick();
//		BeltSlicer.tickHoveringInformation();
		FilteringRenderer.tick();
		LinkRenderer.tick();
		ScrollValueRenderer.tick();
		ChassisRangeDisplay.tick();
		EdgeInteractionRenderer.tick();
		GirderWrenchBehavior.tick();
		WorldshaperRenderHandler.tick();
		CouplingHandlerClient.tick();
		CouplingRenderer.tickDebugModeRenders();
		KineticDebugger.tick();
		ExtendoGripRenderHandler.tick();
		// CollisionDebugger.tick();
		ArmInteractionPointHandler.tick();
		EjectorTargetHandler.tick();
		PlacementHelpers.tick();
		CreateClient.OUTLINER.tickOutlines();
		CreateClient.GHOST_BLOCKS.tickGhosts();
		ContraptionRenderDispatcher.tick(world);
		BlueprintOverlayRenderer.tick();
		ToolboxHandlerClient.clientTick();
		TrackTargetingClient.clientTick();
		TrackPlacement.clientTick();
		TrainRelocator.clientTick();
		DisplayLinkBlockItem.clientTick();
		CurvedTrackInteraction.clientTick();
		CameraDistanceModifier.tick();
		CameraAngleAnimationService.tick();
		TrainHUD.tick();
		ClipboardValueSettingsHandler.clientTick();
		CreateClient.VALUE_SETTINGS_HANDLER.tick();
		ScrollValueHandler.tick();
		NetheriteBacktankFirstPersonRenderer.clientTick();
		ContraptionPlayerPassengerRotation.tick();
	}

	@SubscribeEvent
	public static void onJoin(ClientPlayerNetworkEvent.LoggedInEvent event) {
		CreateClient.checkGraphicsFanciness();
	}

	@SubscribeEvent
	public static void onLeave(ClientPlayerNetworkEvent.LoggedOutEvent event) {
		CreateClient.RAILWAYS.cleanUp();
	}

	@SubscribeEvent
	public static void onLoadWorld(WorldEvent.Load event) {
		LevelAccessor world = event.getWorld();
		if (world.isClientSide() && world instanceof ClientLevel && !(world instanceof WrappedClientWorld)) {
			CreateClient.invalidateRenderers();
			AnimationTickHolder.reset();
		}
	}

	@SubscribeEvent
	public static void onUnloadWorld(WorldEvent.Unload event) {
		if (!event.getWorld()
			.isClientSide())
			return;
		CreateClient.invalidateRenderers();
		CreateClient.SOUL_PULSE_EFFECT_HANDLER.refresh();
		AnimationTickHolder.reset();
		ControlsHandler.levelUnloaded(event.getWorld());
	}

	@SubscribeEvent
	public static void onRenderWorld(RenderLevelLastEvent event) {
		PoseStack ms = event.getPoseStack();
		ms.pushPose();
		SuperRenderTypeBuffer buffer = SuperRenderTypeBuffer.getInstance();
		float partialTicks = AnimationTickHolder.getPartialTicks();
		Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera()
			.getPosition();

		TrackBlockOutline.drawCurveSelection(ms, buffer, camera);
		TrackTargetingClient.render(ms, buffer, camera);
		CouplingRenderer.renderAll(ms, buffer, camera);
		CarriageCouplingRenderer.renderAll(ms, buffer, camera);
		CreateClient.SCHEMATIC_HANDLER.render(ms, buffer, camera);
		CreateClient.GHOST_BLOCKS.renderAll(ms, buffer, camera);
		CreateClient.OUTLINER.renderOutlines(ms, buffer, camera, partialTicks);

		buffer.draw();
		RenderSystem.enableCull();
		ms.popPose();

		ContraptionPlayerPassengerRotation.frame();
	}

	@SubscribeEvent
	public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
		float partialTicks = AnimationTickHolder.getPartialTicks();

		if (CameraAngleAnimationService.isYawAnimating())
			event.setYaw(CameraAngleAnimationService.getYaw(partialTicks));

		if (CameraAngleAnimationService.isPitchAnimating())
			event.setPitch(CameraAngleAnimationService.getPitch(partialTicks));
	}

	@SubscribeEvent
	public static void getItemTooltipColor(RenderTooltipEvent.Color event) {
		PonderTooltipHandler.handleTooltipColor(event);
	}

	@SubscribeEvent
	public static void addToItemTooltip(ItemTooltipEvent event) {
		if (!AllConfigs.client().tooltips.get())
			return;
		if (event.getPlayer() == null)
			return;

		Item item = event.getItemStack().getItem();
		TooltipModifier modifier = TooltipModifier.REGISTRY.get(item);
		if (modifier != null && modifier != TooltipModifier.EMPTY) {
			modifier.modify(event);
		}

		PonderTooltipHandler.addToTooltip(event);
		SequencedAssemblyRecipe.addToTooltip(event);
	}

	@SubscribeEvent
	public static void onRenderTick(RenderTickEvent event) {
		if (!isGameActive())
			return;
		TurntableHandler.gameRenderTick();
	}

	@SubscribeEvent
	public static void onMount(EntityMountEvent event) {
		if (event.getEntityMounting() != Minecraft.getInstance().player)
			return;

		if (event.isDismounting()) {
			CameraDistanceModifier.reset();
			return;
		}

		if (!event.isMounting() || !(event.getEntityBeingMounted() instanceof CarriageContraptionEntity carriage)) {
			return;
		}

		CameraDistanceModifier.zoomOut();
	}

	protected static boolean isGameActive() {
		return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
	}

	@SubscribeEvent
	public static void getFogDensity(EntityViewRenderEvent.RenderFogEvent event) {
		Camera camera = event.getCamera();
		Level level = Minecraft.getInstance().level;
		BlockPos blockPos = camera.getBlockPosition();
		FluidState fluidState = level.getFluidState(blockPos);
		if (camera.getPosition().y >= blockPos.getY() + fluidState.getHeight(level, blockPos))
			return;

		Fluid fluid = fluidState.getType();
		Entity entity = camera.getEntity();

		if (AllFluids.CHOCOLATE.get()
			.isSame(fluid)) {
			event.scaleFarPlaneDistance(1f / 32f * AllConfigs.client().chocolateTransparencyMultiplier.getF());
			event.setCanceled(true);
			return;
		}

		if (AllFluids.HONEY.get()
			.isSame(fluid)) {
			event.scaleFarPlaneDistance(1f / 8f * AllConfigs.client().honeyTransparencyMultiplier.getF());
			event.setCanceled(true);
			return;
		}

		if (entity.isSpectator())
			return;

		ItemStack divingHelmet = DivingHelmetItem.getWornItem(entity);
		if (!divingHelmet.isEmpty()) {
			if (FluidHelper.isWater(fluid)) {
				event.scaleFarPlaneDistance(6.25f);
				event.setCanceled(true);
				return;
			} else if (FluidHelper.isLava(fluid) && NetheriteDivingHandler.isNetheriteDivingHelmet(divingHelmet)) {
				event.setNearPlaneDistance(-4.0f);
				event.setFarPlaneDistance(20.0f);
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent
	public static void getFogColor(EntityViewRenderEvent.FogColors event) {
		Camera info = event.getCamera();
		Level level = Minecraft.getInstance().level;
		BlockPos blockPos = info.getBlockPosition();
		FluidState fluidState = level.getFluidState(blockPos);
		if (info.getPosition().y > blockPos.getY() + fluidState.getHeight(level, blockPos))
			return;

		Fluid fluid = fluidState.getType();

		if (AllFluids.CHOCOLATE.get()
			.isSame(fluid)) {
			event.setRed(98 / 255f);
			event.setGreen(32 / 255f);
			event.setBlue(32 / 255f);
			return;
		}

		if (AllFluids.HONEY.get()
			.isSame(fluid)) {
			event.setRed(234 / 255f);
			event.setGreen(174 / 255f);
			event.setBlue(47 / 255f);
			return;
		}
	}

	@SubscribeEvent
	public static void leftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof ZapperItem) {
			AllPackets.getChannel().sendToServer(new LeftClickPacket());
		}
	}

	@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
	public static class ModBusEvents {

		@SubscribeEvent
		public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
			event.registerReloadListener(CreateClient.RESOURCE_RELOAD_LISTENER);
		}

		@SubscribeEvent
		public static void addEntityRendererLayers(EntityRenderersEvent.AddLayers event) {
			EntityRenderDispatcher dispatcher = Minecraft.getInstance()
				.getEntityRenderDispatcher();
			BacktankArmorLayer.registerOnAll(dispatcher);
			TrainHatArmorLayer.registerOnAll(dispatcher);
		}

		@SubscribeEvent
		public static void onLoadComplete(FMLLoadCompleteEvent event) {
			ModContainer createContainer = ModList.get()
				.getModContainerById(Create.ID)
				.orElseThrow(() -> new IllegalStateException("Create mod container missing on LoadComplete"));
			createContainer.registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
				() -> new ConfigGuiHandler.ConfigGuiFactory(
					(mc, previousScreen) -> BaseConfigScreen.forCreate(previousScreen)));
		}

	}

}
