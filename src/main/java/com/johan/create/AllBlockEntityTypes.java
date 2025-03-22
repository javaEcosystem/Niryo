package com.johan.create;

import static com.johan.create.Create.REGISTRATE;
import static com.johan.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviourBE;

import com.johan.create.content.contraptions.actors.contraptionControls.ContraptionControlsBlockEntity;
import com.johan.create.content.contraptions.actors.contraptionControls.ContraptionControlsRenderer;
import com.johan.create.content.contraptions.actors.harvester.HarvesterBlockEntity;
import com.johan.create.content.contraptions.actors.harvester.HarvesterRenderer;
import com.johan.create.content.contraptions.actors.psi.PSIInstance;
import com.johan.create.content.contraptions.actors.psi.PortableFluidInterfaceBlockEntity;
import com.johan.create.content.contraptions.actors.psi.PortableItemInterfaceBlockEntity;
import com.johan.create.content.contraptions.actors.psi.PortableStorageInterfaceRenderer;
import com.johan.create.content.contraptions.actors.roller.RollerBlockEntity;
import com.johan.create.content.contraptions.actors.roller.RollerRenderer;
import com.johan.create.content.contraptions.bearing.BearingInstance;
import com.johan.create.content.contraptions.bearing.BearingRenderer;
import com.johan.create.content.contraptions.bearing.ClockworkBearingBlockEntity;
import com.johan.create.content.contraptions.bearing.MechanicalBearingBlockEntity;
import com.johan.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.johan.create.content.contraptions.chassis.ChassisBlockEntity;
import com.johan.create.content.contraptions.chassis.StickerBlockEntity;
import com.johan.create.content.contraptions.chassis.StickerInstance;
import com.johan.create.content.contraptions.chassis.StickerRenderer;
import com.johan.create.content.contraptions.elevator.ElevatorContactBlockEntity;
import com.johan.create.content.contraptions.elevator.ElevatorPulleyBlockEntity;
import com.johan.create.content.contraptions.elevator.ElevatorPulleyRenderer;
import com.johan.create.content.contraptions.gantry.GantryCarriageBlockEntity;
import com.johan.create.content.contraptions.gantry.GantryCarriageInstance;
import com.johan.create.content.contraptions.gantry.GantryCarriageRenderer;
import com.johan.create.content.contraptions.mounted.CartAssemblerBlockEntity;
import com.johan.create.content.contraptions.piston.MechanicalPistonBlockEntity;
import com.johan.create.content.contraptions.piston.MechanicalPistonRenderer;
import com.johan.create.content.contraptions.pulley.HosePulleyInstance;
import com.johan.create.content.contraptions.pulley.PulleyBlockEntity;
import com.johan.create.content.contraptions.pulley.PulleyRenderer;
import com.johan.create.content.contraptions.pulley.RopePulleyInstance;
import com.johan.create.content.decoration.copycat.CopycatBlockEntity;
import com.johan.create.content.decoration.placard.PlacardBlockEntity;
import com.johan.create.content.decoration.placard.PlacardRenderer;
import com.johan.create.content.decoration.slidingDoor.SlidingDoorBlockEntity;
import com.johan.create.content.decoration.slidingDoor.SlidingDoorRenderer;
import com.johan.create.content.decoration.steamWhistle.WhistleBlockEntity;
import com.johan.create.content.decoration.steamWhistle.WhistleRenderer;
import com.johan.create.content.equipment.armor.BacktankBlockEntity;
import com.johan.create.content.equipment.armor.BacktankInstance;
import com.johan.create.content.equipment.armor.BacktankRenderer;
import com.johan.create.content.equipment.bell.BellRenderer;
import com.johan.create.content.equipment.bell.HauntedBellBlockEntity;
import com.johan.create.content.equipment.bell.PeculiarBellBlockEntity;
import com.johan.create.content.equipment.clipboard.ClipboardBlockEntity;
import com.johan.create.content.equipment.toolbox.ToolBoxInstance;
import com.johan.create.content.equipment.toolbox.ToolboxBlockEntity;
import com.johan.create.content.equipment.toolbox.ToolboxRenderer;
import com.johan.create.content.fluids.drain.ItemDrainBlockEntity;
import com.johan.create.content.fluids.drain.ItemDrainRenderer;
import com.johan.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import com.johan.create.content.fluids.hosePulley.HosePulleyRenderer;
import com.johan.create.content.fluids.pipes.FluidPipeBlockEntity;
import com.johan.create.content.fluids.pipes.SmartFluidPipeBlockEntity;
import com.johan.create.content.fluids.pipes.StraightPipeBlockEntity;
import com.johan.create.content.fluids.pipes.TransparentStraightPipeRenderer;
import com.johan.create.content.fluids.pipes.valve.FluidValveBlockEntity;
import com.johan.create.content.fluids.pipes.valve.FluidValveInstance;
import com.johan.create.content.fluids.pipes.valve.FluidValveRenderer;
import com.johan.create.content.fluids.pump.PumpBlockEntity;
import com.johan.create.content.fluids.pump.PumpCogInstance;
import com.johan.create.content.fluids.pump.PumpRenderer;
import com.johan.create.content.fluids.spout.SpoutBlockEntity;
import com.johan.create.content.fluids.spout.SpoutRenderer;
import com.johan.create.content.fluids.tank.CreativeFluidTankBlockEntity;
import com.johan.create.content.fluids.tank.FluidTankBlockEntity;
import com.johan.create.content.fluids.tank.FluidTankRenderer;
import com.johan.create.content.kinetics.base.CutoutRotatingInstance;
import com.johan.create.content.kinetics.base.HalfShaftInstance;
import com.johan.create.content.kinetics.base.HorizontalHalfShaftInstance;
import com.johan.create.content.kinetics.base.KineticBlockEntity;
import com.johan.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.johan.create.content.kinetics.base.ShaftInstance;
import com.johan.create.content.kinetics.base.ShaftRenderer;
import com.johan.create.content.kinetics.base.SingleRotatingInstance;
import com.johan.create.content.kinetics.belt.BeltBlockEntity;
import com.johan.create.content.kinetics.belt.BeltInstance;
import com.johan.create.content.kinetics.belt.BeltRenderer;
import com.johan.create.content.kinetics.chainDrive.ChainGearshiftBlockEntity;
import com.johan.create.content.kinetics.clock.CuckooClockBlockEntity;
import com.johan.create.content.kinetics.clock.CuckooClockRenderer;
import com.johan.create.content.kinetics.crafter.MechanicalCrafterBlockEntity;
import com.johan.create.content.kinetics.crafter.MechanicalCrafterRenderer;
import com.johan.create.content.kinetics.crafter.ShaftlessCogwheelInstance;
import com.johan.create.content.kinetics.crank.HandCrankBlockEntity;
import com.johan.create.content.kinetics.crank.HandCrankInstance;
import com.johan.create.content.kinetics.crank.HandCrankRenderer;
import com.johan.create.content.kinetics.crank.ValveHandleBlockEntity;
import com.johan.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.johan.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.johan.create.content.kinetics.deployer.DeployerBlockEntity;
import com.johan.create.content.kinetics.deployer.DeployerInstance;
import com.johan.create.content.kinetics.deployer.DeployerRenderer;
import com.johan.create.content.kinetics.drill.DrillBlockEntity;
import com.johan.create.content.kinetics.drill.DrillInstance;
import com.johan.create.content.kinetics.drill.DrillRenderer;
import com.johan.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.johan.create.content.kinetics.fan.EncasedFanRenderer;
import com.johan.create.content.kinetics.fan.FanInstance;
import com.johan.create.content.kinetics.fan.NozzleBlockEntity;
import com.johan.create.content.kinetics.flywheel.FlywheelBlockEntity;
import com.johan.create.content.kinetics.flywheel.FlywheelInstance;
import com.johan.create.content.kinetics.flywheel.FlywheelRenderer;
import com.johan.create.content.kinetics.gantry.GantryShaftBlockEntity;
import com.johan.create.content.kinetics.gauge.GaugeInstance;
import com.johan.create.content.kinetics.gauge.GaugeRenderer;
import com.johan.create.content.kinetics.gauge.SpeedGaugeBlockEntity;
import com.johan.create.content.kinetics.gauge.StressGaugeBlockEntity;
import com.johan.create.content.kinetics.gearbox.GearboxBlockEntity;
import com.johan.create.content.kinetics.gearbox.GearboxInstance;
import com.johan.create.content.kinetics.gearbox.GearboxRenderer;
import com.johan.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.johan.create.content.kinetics.mechanicalArm.ArmInstance;
import com.johan.create.content.kinetics.mechanicalArm.ArmRenderer;
import com.johan.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.johan.create.content.kinetics.millstone.MillstoneCogInstance;
import com.johan.create.content.kinetics.millstone.MillstoneRenderer;
import com.johan.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.johan.create.content.kinetics.mixer.MechanicalMixerRenderer;
import com.johan.create.content.kinetics.mixer.MixerInstance;
import com.johan.create.content.kinetics.motor.CreativeMotorBlockEntity;
import com.johan.create.content.kinetics.motor.CreativeMotorRenderer;
import com.johan.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.johan.create.content.kinetics.press.MechanicalPressRenderer;
import com.johan.create.content.kinetics.press.PressInstance;
import com.johan.create.content.kinetics.saw.SawBlockEntity;
import com.johan.create.content.kinetics.saw.SawInstance;
import com.johan.create.content.kinetics.saw.SawRenderer;
import com.johan.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.johan.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import com.johan.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.johan.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.johan.create.content.kinetics.simpleRelays.encased.EncasedCogInstance;
import com.johan.create.content.kinetics.simpleRelays.encased.EncasedCogRenderer;
import com.johan.create.content.kinetics.speedController.SpeedControllerBlockEntity;
import com.johan.create.content.kinetics.speedController.SpeedControllerRenderer;
import com.johan.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import com.johan.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.johan.create.content.kinetics.steamEngine.SteamEngineInstance;
import com.johan.create.content.kinetics.steamEngine.SteamEngineRenderer;
import com.johan.create.content.kinetics.transmission.ClutchBlockEntity;
import com.johan.create.content.kinetics.transmission.GearshiftBlockEntity;
import com.johan.create.content.kinetics.transmission.SplitShaftInstance;
import com.johan.create.content.kinetics.transmission.SplitShaftRenderer;
import com.johan.create.content.kinetics.transmission.sequencer.SequencedGearshiftBlockEntity;
import com.johan.create.content.kinetics.turntable.TurntableBlockEntity;
import com.johan.create.content.kinetics.waterwheel.LargeWaterWheelBlockEntity;
import com.johan.create.content.kinetics.waterwheel.WaterWheelBlockEntity;
import com.johan.create.content.kinetics.waterwheel.WaterWheelInstance;
import com.johan.create.content.kinetics.waterwheel.WaterWheelRenderer;
import com.johan.create.content.logistics.chute.ChuteBlockEntity;
import com.johan.create.content.logistics.chute.ChuteRenderer;
import com.johan.create.content.logistics.chute.SmartChuteBlockEntity;
import com.johan.create.content.logistics.chute.SmartChuteRenderer;
import com.johan.create.content.logistics.crate.CreativeCrateBlockEntity;
import com.johan.create.content.logistics.depot.DepotBlockEntity;
import com.johan.create.content.logistics.depot.DepotRenderer;
import com.johan.create.content.logistics.depot.EjectorBlockEntity;
import com.johan.create.content.logistics.depot.EjectorInstance;
import com.johan.create.content.logistics.depot.EjectorRenderer;
import com.johan.create.content.logistics.funnel.FunnelBlockEntity;
import com.johan.create.content.logistics.funnel.FunnelInstance;
import com.johan.create.content.logistics.funnel.FunnelRenderer;
import com.johan.create.content.logistics.tunnel.BeltTunnelBlockEntity;
import com.johan.create.content.logistics.tunnel.BeltTunnelInstance;
import com.johan.create.content.logistics.tunnel.BeltTunnelRenderer;
import com.johan.create.content.logistics.tunnel.BrassTunnelBlockEntity;
import com.johan.create.content.logistics.vault.ItemVaultBlockEntity;
import com.johan.create.content.processing.basin.BasinBlockEntity;
import com.johan.create.content.processing.basin.BasinRenderer;
import com.johan.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.johan.create.content.processing.burner.BlazeBurnerRenderer;
import com.johan.create.content.redstone.analogLever.AnalogLeverBlockEntity;
import com.johan.create.content.redstone.analogLever.AnalogLeverInstance;
import com.johan.create.content.redstone.analogLever.AnalogLeverRenderer;
import com.johan.create.content.redstone.diodes.BrassDiodeInstance;
import com.johan.create.content.redstone.diodes.BrassDiodeRenderer;
import com.johan.create.content.redstone.diodes.PulseExtenderBlockEntity;
import com.johan.create.content.redstone.diodes.PulseRepeaterBlockEntity;
import com.johan.create.content.redstone.displayLink.DisplayLinkBlockEntity;
import com.johan.create.content.redstone.displayLink.DisplayLinkRenderer;
import com.johan.create.content.redstone.displayLink.source.NixieTubeDisplaySource;
import com.johan.create.content.redstone.displayLink.target.NixieTubeDisplayTarget;
import com.johan.create.content.redstone.link.RedstoneLinkBlockEntity;
import com.johan.create.content.redstone.link.controller.LecternControllerBlockEntity;
import com.johan.create.content.redstone.link.controller.LecternControllerRenderer;
import com.johan.create.content.redstone.nixieTube.NixieTubeBlockEntity;
import com.johan.create.content.redstone.nixieTube.NixieTubeRenderer;
import com.johan.create.content.redstone.smartObserver.SmartObserverBlockEntity;
import com.johan.create.content.redstone.thresholdSwitch.ThresholdSwitchBlockEntity;
import com.johan.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.johan.create.content.schematics.cannon.SchematicannonInstance;
import com.johan.create.content.schematics.cannon.SchematicannonRenderer;
import com.johan.create.content.schematics.table.SchematicTableBlockEntity;
import com.johan.create.content.trains.bogey.BogeyBlockEntityRenderer;
import com.johan.create.content.trains.bogey.StandardBogeyBlockEntity;
import com.johan.create.content.trains.display.FlapDisplayBlockEntity;
import com.johan.create.content.trains.display.FlapDisplayRenderer;
import com.johan.create.content.trains.observer.TrackObserverBlockEntity;
import com.johan.create.content.trains.observer.TrackObserverRenderer;
import com.johan.create.content.trains.signal.SignalBlockEntity;
import com.johan.create.content.trains.signal.SignalRenderer;
import com.johan.create.content.trains.station.StationBlockEntity;
import com.johan.create.content.trains.station.StationRenderer;
import com.johan.create.content.trains.track.FakeTrackBlockEntity;
import com.johan.create.content.trains.track.TrackBlockEntity;
import com.johan.create.content.trains.track.TrackInstance;
import com.johan.create.content.trains.track.TrackMaterial;
import com.johan.create.content.trains.track.TrackRenderer;
import com.johan.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {

	// Schematics
	public static final BlockEntityEntry<SchematicannonBlockEntity> SCHEMATICANNON = REGISTRATE
		.blockEntity("schematicannon", SchematicannonBlockEntity::new)
		.instance(() -> SchematicannonInstance::new)
		.validBlocks(AllBlocks.SCHEMATICANNON)
		.renderer(() -> SchematicannonRenderer::new)
		.register();

	public static final BlockEntityEntry<SchematicTableBlockEntity> SCHEMATIC_TABLE = REGISTRATE
		.blockEntity("schematic_table", SchematicTableBlockEntity::new)
		.validBlocks(AllBlocks.SCHEMATIC_TABLE)
		.register();

	// Kinetics
	public static final BlockEntityEntry<BracketedKineticBlockEntity> BRACKETED_KINETIC = REGISTRATE
		.blockEntity("simple_kinetic", BracketedKineticBlockEntity::new)
		.instance(() -> BracketedKineticBlockEntityInstance::new, false)
		.validBlocks(AllBlocks.SHAFT, AllBlocks.COGWHEEL, AllBlocks.LARGE_COGWHEEL)
		.renderer(() -> BracketedKineticBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<CreativeMotorBlockEntity> MOTOR = REGISTRATE
		.blockEntity("motor", CreativeMotorBlockEntity::new)
		.instance(() -> HalfShaftInstance::new, false)
		.validBlocks(AllBlocks.CREATIVE_MOTOR)
		.renderer(() -> CreativeMotorRenderer::new)
		.register();

	public static final BlockEntityEntry<GearboxBlockEntity> GEARBOX = REGISTRATE
		.blockEntity("gearbox", GearboxBlockEntity::new)
		.instance(() -> GearboxInstance::new, false)
		.validBlocks(AllBlocks.GEARBOX)
		.renderer(() -> GearboxRenderer::new)
		.register();

	public static final BlockEntityEntry<KineticBlockEntity> ENCASED_SHAFT = REGISTRATE
		.blockEntity("encased_shaft", KineticBlockEntity::new)
		.instance(() -> ShaftInstance::new, false)
		.validBlocks(AllBlocks.ANDESITE_ENCASED_SHAFT, AllBlocks.BRASS_ENCASED_SHAFT, AllBlocks.ENCASED_CHAIN_DRIVE,
			AllBlocks.METAL_GIRDER_ENCASED_SHAFT)
		.renderer(() -> ShaftRenderer::new)
		.register();

	public static final BlockEntityEntry<SimpleKineticBlockEntity> ENCASED_COGWHEEL = REGISTRATE
		.blockEntity("encased_cogwheel", SimpleKineticBlockEntity::new)
		.instance(() -> EncasedCogInstance::small, false)
		.validBlocks(AllBlocks.ANDESITE_ENCASED_COGWHEEL, AllBlocks.BRASS_ENCASED_COGWHEEL)
		.renderer(() -> EncasedCogRenderer::small)
		.register();

	public static final BlockEntityEntry<SimpleKineticBlockEntity> ENCASED_LARGE_COGWHEEL = REGISTRATE
		.blockEntity("encased_large_cogwheel", SimpleKineticBlockEntity::new)
		.instance(() -> EncasedCogInstance::large, false)
		.validBlocks(AllBlocks.ANDESITE_ENCASED_LARGE_COGWHEEL, AllBlocks.BRASS_ENCASED_LARGE_COGWHEEL)
		.renderer(() -> EncasedCogRenderer::large)
		.register();

	public static final BlockEntityEntry<ChainGearshiftBlockEntity> ADJUSTABLE_CHAIN_GEARSHIFT = REGISTRATE
		.blockEntity("adjustable_chain_gearshift", ChainGearshiftBlockEntity::new)
		.instance(() -> ShaftInstance::new, false)
		.validBlocks(AllBlocks.ADJUSTABLE_CHAIN_GEARSHIFT)
		.renderer(() -> ShaftRenderer::new)
		.register();

	public static final BlockEntityEntry<EncasedFanBlockEntity> ENCASED_FAN = REGISTRATE
		.blockEntity("encased_fan", EncasedFanBlockEntity::new)
		.instance(() -> FanInstance::new, false)
		.validBlocks(AllBlocks.ENCASED_FAN)
		.renderer(() -> EncasedFanRenderer::new)
		.register();

	public static final BlockEntityEntry<NozzleBlockEntity> NOZZLE = REGISTRATE
		.blockEntity("nozzle", NozzleBlockEntity::new)
		.validBlocks(AllBlocks.NOZZLE)
		// .renderer(() -> renderer)
		.register();

	public static final BlockEntityEntry<ClutchBlockEntity> CLUTCH = REGISTRATE
		.blockEntity("clutch", ClutchBlockEntity::new)
		.instance(() -> SplitShaftInstance::new, false)
		.validBlocks(AllBlocks.CLUTCH)
		.renderer(() -> SplitShaftRenderer::new)
		.register();

	public static final BlockEntityEntry<GearshiftBlockEntity> GEARSHIFT = REGISTRATE
		.blockEntity("gearshift", GearshiftBlockEntity::new)
		.instance(() -> SplitShaftInstance::new, false)
		.validBlocks(AllBlocks.GEARSHIFT)
		.renderer(() -> SplitShaftRenderer::new)
		.register();

	public static final BlockEntityEntry<TurntableBlockEntity> TURNTABLE = REGISTRATE
		.blockEntity("turntable", TurntableBlockEntity::new)
		.instance(() -> SingleRotatingInstance::new, false)
		.validBlocks(AllBlocks.TURNTABLE)
		.renderer(() -> KineticBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<HandCrankBlockEntity> HAND_CRANK = REGISTRATE
		.blockEntity("hand_crank", HandCrankBlockEntity::new)
		.instance(() -> HandCrankInstance::new)
		.validBlocks(AllBlocks.HAND_CRANK)
		.renderer(() -> HandCrankRenderer::new)
		.register();

	public static final BlockEntityEntry<ValveHandleBlockEntity> VALVE_HANDLE = REGISTRATE
		.blockEntity("valve_handle", ValveHandleBlockEntity::new)
		.instance(() -> HandCrankInstance::new)
		.validBlocks(AllBlocks.COPPER_VALVE_HANDLE)
		.validBlocks(AllBlocks.DYED_VALVE_HANDLES.toArray())
		.renderer(() -> HandCrankRenderer::new)
		.register();

	public static final BlockEntityEntry<CuckooClockBlockEntity> CUCKOO_CLOCK = REGISTRATE
		.blockEntity("cuckoo_clock", CuckooClockBlockEntity::new)
		.instance(() -> HorizontalHalfShaftInstance::new)
		.validBlocks(AllBlocks.CUCKOO_CLOCK, AllBlocks.MYSTERIOUS_CUCKOO_CLOCK)
		.renderer(() -> CuckooClockRenderer::new)
		.register();

	public static final BlockEntityEntry<GantryShaftBlockEntity> GANTRY_SHAFT = REGISTRATE
		.blockEntity("gantry_shaft", GantryShaftBlockEntity::new)
		.instance(() -> SingleRotatingInstance::new, false)
		.validBlocks(AllBlocks.GANTRY_SHAFT)
		.renderer(() -> KineticBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<GantryCarriageBlockEntity> GANTRY_PINION = REGISTRATE
		.blockEntity("gantry_pinion", GantryCarriageBlockEntity::new)
		.instance(() -> GantryCarriageInstance::new)
		.validBlocks(AllBlocks.GANTRY_CARRIAGE)
		.renderer(() -> GantryCarriageRenderer::new)
		.register();

	public static final BlockEntityEntry<PumpBlockEntity> MECHANICAL_PUMP = REGISTRATE
		.blockEntity("mechanical_pump", PumpBlockEntity::new)
		.instance(() -> PumpCogInstance::new)
		.validBlocks(AllBlocks.MECHANICAL_PUMP)
		.renderer(() -> PumpRenderer::new)
		.register();

	public static final BlockEntityEntry<SmartFluidPipeBlockEntity> SMART_FLUID_PIPE = REGISTRATE
		.blockEntity("smart_fluid_pipe", SmartFluidPipeBlockEntity::new)
		.validBlocks(AllBlocks.SMART_FLUID_PIPE)
		.renderer(() -> SmartBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<FluidPipeBlockEntity> FLUID_PIPE = REGISTRATE
		.blockEntity("fluid_pipe", FluidPipeBlockEntity::new)
		.validBlocks(AllBlocks.FLUID_PIPE)
		.register();

	public static final BlockEntityEntry<FluidPipeBlockEntity> ENCASED_FLUID_PIPE = REGISTRATE
		.blockEntity("encased_fluid_pipe", FluidPipeBlockEntity::new)
		.validBlocks(AllBlocks.ENCASED_FLUID_PIPE)
		.register();

	public static final BlockEntityEntry<StraightPipeBlockEntity> GLASS_FLUID_PIPE = REGISTRATE
		.blockEntity("glass_fluid_pipe", StraightPipeBlockEntity::new)
		.validBlocks(AllBlocks.GLASS_FLUID_PIPE)
		.renderer(() -> TransparentStraightPipeRenderer::new)
		.register();

	public static final BlockEntityEntry<FluidValveBlockEntity> FLUID_VALVE = REGISTRATE
		.blockEntity("fluid_valve", FluidValveBlockEntity::new)
		.instance(() -> FluidValveInstance::new)
		.validBlocks(AllBlocks.FLUID_VALVE)
		.renderer(() -> FluidValveRenderer::new)
		.register();

	public static final BlockEntityEntry<FluidTankBlockEntity> FLUID_TANK = REGISTRATE
		.blockEntity("fluid_tank", FluidTankBlockEntity::new)
		.validBlocks(AllBlocks.FLUID_TANK)
		.renderer(() -> FluidTankRenderer::new)
		.register();

	public static final BlockEntityEntry<CreativeFluidTankBlockEntity> CREATIVE_FLUID_TANK = REGISTRATE
		.blockEntity("creative_fluid_tank", CreativeFluidTankBlockEntity::new)
		.validBlocks(AllBlocks.CREATIVE_FLUID_TANK)
		.renderer(() -> FluidTankRenderer::new)
		.register();

	public static final BlockEntityEntry<HosePulleyBlockEntity> HOSE_PULLEY = REGISTRATE
		.blockEntity("hose_pulley", HosePulleyBlockEntity::new)
		.instance(() -> HosePulleyInstance::new)
		.validBlocks(AllBlocks.HOSE_PULLEY)
		.renderer(() -> HosePulleyRenderer::new)
		.register();

	public static final BlockEntityEntry<SpoutBlockEntity> SPOUT = REGISTRATE
		.blockEntity("spout", SpoutBlockEntity::new)
		.validBlocks(AllBlocks.SPOUT)
		.renderer(() -> SpoutRenderer::new)
		.register();

	public static final BlockEntityEntry<ItemDrainBlockEntity> ITEM_DRAIN = REGISTRATE
		.blockEntity("item_drain", ItemDrainBlockEntity::new)
		.validBlocks(AllBlocks.ITEM_DRAIN)
		.renderer(() -> ItemDrainRenderer::new)
		.register();

	public static final BlockEntityEntry<BeltBlockEntity> BELT = REGISTRATE
		.blockEntity("belt", BeltBlockEntity::new)
		.instance(() -> BeltInstance::new, BeltBlockEntity::shouldRenderNormally)
		.validBlocks(AllBlocks.BELT)
		.renderer(() -> BeltRenderer::new)
		.register();

	public static final BlockEntityEntry<ChuteBlockEntity> CHUTE = REGISTRATE
		.blockEntity("chute", ChuteBlockEntity::new)
		.validBlocks(AllBlocks.CHUTE)
		.renderer(() -> ChuteRenderer::new)
		.register();

	public static final BlockEntityEntry<SmartChuteBlockEntity> SMART_CHUTE = REGISTRATE
		.blockEntity("smart_chute", SmartChuteBlockEntity::new)
		.validBlocks(AllBlocks.SMART_CHUTE)
		.renderer(() -> SmartChuteRenderer::new)
		.register();

	public static final BlockEntityEntry<BeltTunnelBlockEntity> ANDESITE_TUNNEL = REGISTRATE
		.blockEntity("andesite_tunnel", BeltTunnelBlockEntity::new)
		.instance(() -> BeltTunnelInstance::new)
		.validBlocks(AllBlocks.ANDESITE_TUNNEL)
		.renderer(() -> BeltTunnelRenderer::new)
		.register();

	public static final BlockEntityEntry<BrassTunnelBlockEntity> BRASS_TUNNEL = REGISTRATE
		.blockEntity("brass_tunnel", BrassTunnelBlockEntity::new)
		.instance(() -> BeltTunnelInstance::new)
		.validBlocks(AllBlocks.BRASS_TUNNEL)
		.renderer(() -> BeltTunnelRenderer::new)
		.register();

	public static final BlockEntityEntry<ArmBlockEntity> MECHANICAL_ARM = REGISTRATE
		.blockEntity("mechanical_arm", ArmBlockEntity::new)
		.instance(() -> ArmInstance::new)
		.validBlocks(AllBlocks.MECHANICAL_ARM)
		.renderer(() -> ArmRenderer::new)
		.register();

	public static final BlockEntityEntry<ItemVaultBlockEntity> ITEM_VAULT = REGISTRATE
		.blockEntity("item_vault", ItemVaultBlockEntity::new)
		.validBlocks(AllBlocks.ITEM_VAULT)
		.register();

	public static final BlockEntityEntry<MechanicalPistonBlockEntity> MECHANICAL_PISTON = REGISTRATE
		.blockEntity("mechanical_piston", MechanicalPistonBlockEntity::new)
		.instance(() -> ShaftInstance::new, false)
		.validBlocks(AllBlocks.MECHANICAL_PISTON, AllBlocks.STICKY_MECHANICAL_PISTON)
		.renderer(() -> MechanicalPistonRenderer::new)
		.register();

	public static final BlockEntityEntry<WindmillBearingBlockEntity> WINDMILL_BEARING = REGISTRATE
		.blockEntity("windmill_bearing", WindmillBearingBlockEntity::new)
		.instance(() -> BearingInstance::new)
		.validBlocks(AllBlocks.WINDMILL_BEARING)
		.renderer(() -> BearingRenderer::new)
		.register();

	public static final BlockEntityEntry<MechanicalBearingBlockEntity> MECHANICAL_BEARING = REGISTRATE
		.blockEntity("mechanical_bearing", MechanicalBearingBlockEntity::new)
		.instance(() -> BearingInstance::new)
		.validBlocks(AllBlocks.MECHANICAL_BEARING)
		.renderer(() -> BearingRenderer::new)
		.register();

	public static final BlockEntityEntry<ClockworkBearingBlockEntity> CLOCKWORK_BEARING = REGISTRATE
		.blockEntity("clockwork_bearing", ClockworkBearingBlockEntity::new)
		.instance(() -> BearingInstance::new)
		.validBlocks(AllBlocks.CLOCKWORK_BEARING)
		.renderer(() -> BearingRenderer::new)
		.register();

	public static final BlockEntityEntry<PulleyBlockEntity> ROPE_PULLEY = REGISTRATE
		.blockEntity("rope_pulley", PulleyBlockEntity::new)
		.instance(() -> RopePulleyInstance::new, false)
		.validBlocks(AllBlocks.ROPE_PULLEY)
		.renderer(() -> PulleyRenderer::new)
		.register();

	public static final BlockEntityEntry<ElevatorPulleyBlockEntity> ELEVATOR_PULLEY =
		REGISTRATE.blockEntity("elevator_pulley", ElevatorPulleyBlockEntity::new)
//		.instance(() -> ElevatorPulleyInstance::new, false)
			.validBlocks(AllBlocks.ELEVATOR_PULLEY)
			.renderer(() -> ElevatorPulleyRenderer::new)
			.register();

	public static final BlockEntityEntry<ElevatorContactBlockEntity> ELEVATOR_CONTACT =
		REGISTRATE.blockEntity("elevator_contact", ElevatorContactBlockEntity::new)
			.validBlocks(AllBlocks.ELEVATOR_CONTACT)
			.register();

	public static final BlockEntityEntry<ChassisBlockEntity> CHASSIS = REGISTRATE
		.blockEntity("chassis", ChassisBlockEntity::new)
		.validBlocks(AllBlocks.RADIAL_CHASSIS, AllBlocks.LINEAR_CHASSIS, AllBlocks.SECONDARY_LINEAR_CHASSIS)
		// .renderer(() -> renderer)
		.register();

	public static final BlockEntityEntry<StickerBlockEntity> STICKER = REGISTRATE
		.blockEntity("sticker", StickerBlockEntity::new)
		.instance(() -> StickerInstance::new, false)
		.validBlocks(AllBlocks.STICKER)
		.renderer(() -> StickerRenderer::new)
		.register();

	public static final BlockEntityEntry<ContraptionControlsBlockEntity> CONTRAPTION_CONTROLS =
		REGISTRATE.blockEntity("contraption_controls", ContraptionControlsBlockEntity::new)
			.validBlocks(AllBlocks.CONTRAPTION_CONTROLS)
			.renderer(() -> ContraptionControlsRenderer::new)
			.register();

	public static final BlockEntityEntry<DrillBlockEntity> DRILL = REGISTRATE
		.blockEntity("drill", DrillBlockEntity::new)
		.instance(() -> DrillInstance::new, false)
		.validBlocks(AllBlocks.MECHANICAL_DRILL)
		.renderer(() -> DrillRenderer::new)
		.register();

	public static final BlockEntityEntry<SawBlockEntity> SAW = REGISTRATE
		.blockEntity("saw", SawBlockEntity::new)
		.instance(() -> SawInstance::new)
		.validBlocks(AllBlocks.MECHANICAL_SAW)
		.renderer(() -> SawRenderer::new)
		.register();

	public static final BlockEntityEntry<HarvesterBlockEntity> HARVESTER = REGISTRATE
		.blockEntity("harvester", HarvesterBlockEntity::new)
		.validBlocks(AllBlocks.MECHANICAL_HARVESTER)
		.renderer(() -> HarvesterRenderer::new)
		.register();

	public static final BlockEntityEntry<RollerBlockEntity> MECHANICAL_ROLLER =
		REGISTRATE.blockEntity("mechanical_roller", RollerBlockEntity::new)
			.validBlocks(AllBlocks.MECHANICAL_ROLLER)
			.renderer(() -> RollerRenderer::new)
			.register();

	public static final BlockEntityEntry<PortableItemInterfaceBlockEntity> PORTABLE_STORAGE_INTERFACE =
		REGISTRATE
			.blockEntity("portable_storage_interface", PortableItemInterfaceBlockEntity::new)
			.instance(() -> PSIInstance::new)
			.validBlocks(AllBlocks.PORTABLE_STORAGE_INTERFACE)
			.renderer(() -> PortableStorageInterfaceRenderer::new)
			.register();

	public static final BlockEntityEntry<PortableFluidInterfaceBlockEntity> PORTABLE_FLUID_INTERFACE =
		REGISTRATE
			.blockEntity("portable_fluid_interface", PortableFluidInterfaceBlockEntity::new)
			.instance(() -> PSIInstance::new)
			.validBlocks(AllBlocks.PORTABLE_FLUID_INTERFACE)
			.renderer(() -> PortableStorageInterfaceRenderer::new)
			.register();

	public static final BlockEntityEntry<SteamEngineBlockEntity> STEAM_ENGINE = REGISTRATE
		.blockEntity("steam_engine", SteamEngineBlockEntity::new)
		.instance(() -> SteamEngineInstance::new, false)
		.validBlocks(AllBlocks.STEAM_ENGINE)
		.renderer(() -> SteamEngineRenderer::new)
		.register();

	public static final BlockEntityEntry<WhistleBlockEntity> STEAM_WHISTLE = REGISTRATE
		.blockEntity("steam_whistle", WhistleBlockEntity::new)
		.validBlocks(AllBlocks.STEAM_WHISTLE)
		.renderer(() -> WhistleRenderer::new)
		.register();

	public static final BlockEntityEntry<PoweredShaftBlockEntity> POWERED_SHAFT = REGISTRATE
		.blockEntity("powered_shaft", PoweredShaftBlockEntity::new)
		.instance(() -> SingleRotatingInstance::new, false)
		.validBlocks(AllBlocks.POWERED_SHAFT)
		.renderer(() -> KineticBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<FlywheelBlockEntity> FLYWHEEL = REGISTRATE
		.blockEntity("flywheel", FlywheelBlockEntity::new)
		.instance(() -> FlywheelInstance::new, false)
		.validBlocks(AllBlocks.FLYWHEEL)
		.renderer(() -> FlywheelRenderer::new)
		.register();

	public static final BlockEntityEntry<MillstoneBlockEntity> MILLSTONE = REGISTRATE
		.blockEntity("millstone", MillstoneBlockEntity::new)
		.instance(() -> MillstoneCogInstance::new, false)
		.validBlocks(AllBlocks.MILLSTONE)
		.renderer(() -> MillstoneRenderer::new)
		.register();

	public static final BlockEntityEntry<CrushingWheelBlockEntity> CRUSHING_WHEEL = REGISTRATE
		.blockEntity("crushing_wheel", CrushingWheelBlockEntity::new)
		.instance(() -> CutoutRotatingInstance::new, false)
		.validBlocks(AllBlocks.CRUSHING_WHEEL)
		.renderer(() -> KineticBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<CrushingWheelControllerBlockEntity> CRUSHING_WHEEL_CONTROLLER =
		REGISTRATE
			.blockEntity("crushing_wheel_controller", CrushingWheelControllerBlockEntity::new)
			.validBlocks(AllBlocks.CRUSHING_WHEEL_CONTROLLER)
			// .renderer(() -> renderer)
			.register();

	public static final BlockEntityEntry<WaterWheelBlockEntity> WATER_WHEEL = REGISTRATE
		.blockEntity("water_wheel", WaterWheelBlockEntity::new)
		.instance(() -> WaterWheelInstance::standard, false)
		.validBlocks(AllBlocks.WATER_WHEEL)
		.renderer(() -> WaterWheelRenderer::standard)
		.register();

	public static final BlockEntityEntry<LargeWaterWheelBlockEntity> LARGE_WATER_WHEEL = REGISTRATE
		.blockEntity("large_water_wheel", LargeWaterWheelBlockEntity::new)
		.instance(() -> WaterWheelInstance::large, false)
		.validBlocks(AllBlocks.LARGE_WATER_WHEEL)
		.renderer(() -> WaterWheelRenderer::large)
		.register();

	public static final BlockEntityEntry<MechanicalPressBlockEntity> MECHANICAL_PRESS = REGISTRATE
		.blockEntity("mechanical_press", MechanicalPressBlockEntity::new)
		.instance(() -> PressInstance::new)
		.validBlocks(AllBlocks.MECHANICAL_PRESS)
		.renderer(() -> MechanicalPressRenderer::new)
		.register();

	public static final BlockEntityEntry<MechanicalMixerBlockEntity> MECHANICAL_MIXER = REGISTRATE
		.blockEntity("mechanical_mixer", MechanicalMixerBlockEntity::new)
		.instance(() -> MixerInstance::new)
		.validBlocks(AllBlocks.MECHANICAL_MIXER)
		.renderer(() -> MechanicalMixerRenderer::new)
		.register();

	public static final BlockEntityEntry<DeployerBlockEntity> DEPLOYER = REGISTRATE
		.blockEntity("deployer", DeployerBlockEntity::new)
		.instance(() -> DeployerInstance::new)
		.validBlocks(AllBlocks.DEPLOYER)
		.renderer(() -> DeployerRenderer::new)
		.register();

	public static final BlockEntityEntry<BasinBlockEntity> BASIN = REGISTRATE
		.blockEntity("basin", BasinBlockEntity::new)
		.validBlocks(AllBlocks.BASIN)
		.renderer(() -> BasinRenderer::new)
		.register();

	public static final BlockEntityEntry<BlazeBurnerBlockEntity> HEATER = REGISTRATE
		.blockEntity("blaze_heater", BlazeBurnerBlockEntity::new)
		.validBlocks(AllBlocks.BLAZE_BURNER)
		.renderer(() -> BlazeBurnerRenderer::new)
		.register();

	public static final BlockEntityEntry<MechanicalCrafterBlockEntity> MECHANICAL_CRAFTER = REGISTRATE
		.blockEntity("mechanical_crafter", MechanicalCrafterBlockEntity::new)
		.instance(() -> ShaftlessCogwheelInstance::new)
		.validBlocks(AllBlocks.MECHANICAL_CRAFTER)
		.renderer(() -> MechanicalCrafterRenderer::new)
		.register();

	public static final BlockEntityEntry<SequencedGearshiftBlockEntity> SEQUENCED_GEARSHIFT = REGISTRATE
		.blockEntity("sequenced_gearshift", SequencedGearshiftBlockEntity::new)
		.instance(() -> SplitShaftInstance::new, false)
		.validBlocks(AllBlocks.SEQUENCED_GEARSHIFT)
		.renderer(() -> SplitShaftRenderer::new)
		.register();

	public static final BlockEntityEntry<SpeedControllerBlockEntity> ROTATION_SPEED_CONTROLLER = REGISTRATE
		.blockEntity("rotation_speed_controller", SpeedControllerBlockEntity::new)
		.instance(() -> ShaftInstance::new)
		.validBlocks(AllBlocks.ROTATION_SPEED_CONTROLLER)
		.renderer(() -> SpeedControllerRenderer::new)
		.register();

	public static final BlockEntityEntry<SpeedGaugeBlockEntity> SPEEDOMETER = REGISTRATE
		.blockEntity("speedometer", SpeedGaugeBlockEntity::new)
		.instance(() -> GaugeInstance.Speed::new)
		.validBlocks(AllBlocks.SPEEDOMETER)
		.renderer(() -> GaugeRenderer::speed)
		.register();

	public static final BlockEntityEntry<StressGaugeBlockEntity> STRESSOMETER = REGISTRATE
		.blockEntity("stressometer", StressGaugeBlockEntity::new)
		.instance(() -> GaugeInstance.Stress::new)
		.validBlocks(AllBlocks.STRESSOMETER)
		.renderer(() -> GaugeRenderer::stress)
		.register();

	public static final BlockEntityEntry<AnalogLeverBlockEntity> ANALOG_LEVER = REGISTRATE
		.blockEntity("analog_lever", AnalogLeverBlockEntity::new)
		.instance(() -> AnalogLeverInstance::new, false)
		.validBlocks(AllBlocks.ANALOG_LEVER)
		.renderer(() -> AnalogLeverRenderer::new)
		.register();

	public static final BlockEntityEntry<PlacardBlockEntity> PLACARD = REGISTRATE
		.blockEntity("placard", PlacardBlockEntity::new)
		.validBlocks(AllBlocks.PLACARD)
		.renderer(() -> PlacardRenderer::new)
		.register();

	public static final BlockEntityEntry<CartAssemblerBlockEntity> CART_ASSEMBLER = REGISTRATE
		.blockEntity("cart_assembler", CartAssemblerBlockEntity::new)
		.validBlocks(AllBlocks.CART_ASSEMBLER)
		// .renderer(() -> renderer)
		.register();

	// Logistics
	public static final BlockEntityEntry<RedstoneLinkBlockEntity> REDSTONE_LINK = REGISTRATE
		.blockEntity("redstone_link", RedstoneLinkBlockEntity::new)
		.validBlocks(AllBlocks.REDSTONE_LINK)
		.renderer(() -> SmartBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<NixieTubeBlockEntity> NIXIE_TUBE = REGISTRATE
		.blockEntity("nixie_tube", NixieTubeBlockEntity::new)
		.validBlocks(AllBlocks.ORANGE_NIXIE_TUBE)
		.validBlocks(AllBlocks.NIXIE_TUBES.toArray())
		.renderer(() -> NixieTubeRenderer::new)
		.onRegister(assignDataBehaviourBE(new NixieTubeDisplayTarget()))
		.onRegister(assignDataBehaviourBE(new NixieTubeDisplaySource()))
		.register();

	public static final BlockEntityEntry<DisplayLinkBlockEntity> DISPLAY_LINK = REGISTRATE
		.blockEntity("display_link", DisplayLinkBlockEntity::new)
		.validBlocks(AllBlocks.DISPLAY_LINK)
		.renderer(() -> DisplayLinkRenderer::new)
		.register();

	public static final BlockEntityEntry<ThresholdSwitchBlockEntity> THRESHOLD_SWITCH = REGISTRATE
		.blockEntity("stockpile_switch", ThresholdSwitchBlockEntity::new)
		.validBlocks(AllBlocks.THRESHOLD_SWITCH)
		.renderer(() -> SmartBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<CreativeCrateBlockEntity> CREATIVE_CRATE = REGISTRATE
		.blockEntity("creative_crate", CreativeCrateBlockEntity::new)
		.validBlocks(AllBlocks.CREATIVE_CRATE)
		.renderer(() -> SmartBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<DepotBlockEntity> DEPOT = REGISTRATE
		.blockEntity("depot", DepotBlockEntity::new)
		.validBlocks(AllBlocks.DEPOT)
		.renderer(() -> DepotRenderer::new)
		.register();

	public static final BlockEntityEntry<EjectorBlockEntity> WEIGHTED_EJECTOR = REGISTRATE
		.blockEntity("weighted_ejector", EjectorBlockEntity::new)
		.instance(() -> EjectorInstance::new)
		.validBlocks(AllBlocks.WEIGHTED_EJECTOR)
		.renderer(() -> EjectorRenderer::new)
		.register();

	public static final BlockEntityEntry<FunnelBlockEntity> FUNNEL = REGISTRATE
		.blockEntity("funnel", FunnelBlockEntity::new)
		.instance(() -> FunnelInstance::new)
		.validBlocks(AllBlocks.BRASS_FUNNEL, AllBlocks.BRASS_BELT_FUNNEL, AllBlocks.ANDESITE_FUNNEL,
			AllBlocks.ANDESITE_BELT_FUNNEL)
		.renderer(() -> FunnelRenderer::new)
		.register();

	public static final BlockEntityEntry<SmartObserverBlockEntity> SMART_OBSERVER = REGISTRATE
		.blockEntity("content_observer", SmartObserverBlockEntity::new)
		.validBlocks(AllBlocks.SMART_OBSERVER)
		.renderer(() -> SmartBlockEntityRenderer::new)
		.register();

	public static final BlockEntityEntry<PulseExtenderBlockEntity> PULSE_EXTENDER = REGISTRATE
		.blockEntity("pulse_extender", PulseExtenderBlockEntity::new)
		.instance(() -> BrassDiodeInstance::new, false)
		.validBlocks(AllBlocks.PULSE_EXTENDER)
		.renderer(() -> BrassDiodeRenderer::new)
		.register();

	public static final BlockEntityEntry<PulseRepeaterBlockEntity> PULSE_REPEATER = REGISTRATE
		.blockEntity("pulse_repeater", PulseRepeaterBlockEntity::new)
		.instance(() -> BrassDiodeInstance::new, false)
		.validBlocks(AllBlocks.PULSE_REPEATER)
		.renderer(() -> BrassDiodeRenderer::new)
		.register();

	public static final BlockEntityEntry<LecternControllerBlockEntity> LECTERN_CONTROLLER = REGISTRATE
		.blockEntity("lectern_controller", LecternControllerBlockEntity::new)
		.validBlocks(AllBlocks.LECTERN_CONTROLLER)
		.renderer(() -> LecternControllerRenderer::new)
		.register();

	// Curiosities
	public static final BlockEntityEntry<BacktankBlockEntity> BACKTANK = REGISTRATE
		.blockEntity("backtank", BacktankBlockEntity::new)
		.instance(() -> BacktankInstance::new)
		.validBlocks(AllBlocks.COPPER_BACKTANK, AllBlocks.NETHERITE_BACKTANK)
		.renderer(() -> BacktankRenderer::new)
		.register();

	public static final BlockEntityEntry<PeculiarBellBlockEntity> PECULIAR_BELL = REGISTRATE
		.blockEntity("peculiar_bell", PeculiarBellBlockEntity::new)
		.validBlocks(AllBlocks.PECULIAR_BELL)
		.renderer(() -> BellRenderer::new)
		.register();

	public static final BlockEntityEntry<HauntedBellBlockEntity> HAUNTED_BELL = REGISTRATE
		.blockEntity("cursed_bell", HauntedBellBlockEntity::new)
		.validBlocks(AllBlocks.HAUNTED_BELL)
		.renderer(() -> BellRenderer::new)
		.register();

	public static final BlockEntityEntry<ToolboxBlockEntity> TOOLBOX = REGISTRATE
		.blockEntity("toolbox", ToolboxBlockEntity::new)
		.instance(() -> ToolBoxInstance::new, false)
		.validBlocks(AllBlocks.TOOLBOXES.toArray())
		.renderer(() -> ToolboxRenderer::new)
		.register();

	public static final BlockEntityEntry<TrackBlockEntity> TRACK = REGISTRATE
		.blockEntity("track", TrackBlockEntity::new)
		.instance(() -> TrackInstance::new)
		.validBlocksDeferred(TrackMaterial::allBlocks)
		.renderer(() -> TrackRenderer::new)
		.register();

	public static final BlockEntityEntry<FakeTrackBlockEntity> FAKE_TRACK = REGISTRATE
		.blockEntity("fake_track", FakeTrackBlockEntity::new)
		.validBlocks(AllBlocks.FAKE_TRACK)
		.register();

	public static final BlockEntityEntry<StandardBogeyBlockEntity> BOGEY = REGISTRATE
		.blockEntity("bogey", StandardBogeyBlockEntity::new)
		.renderer(() -> BogeyBlockEntityRenderer::new)
		.validBlocks(AllBlocks.SMALL_BOGEY, AllBlocks.LARGE_BOGEY)
		.register();

	public static final BlockEntityEntry<StationBlockEntity> TRACK_STATION = REGISTRATE
		.blockEntity("track_station", StationBlockEntity::new)
		.renderer(() -> StationRenderer::new)
		.validBlocks(AllBlocks.TRACK_STATION)
		.register();

	public static final BlockEntityEntry<SlidingDoorBlockEntity> SLIDING_DOOR =
		REGISTRATE.blockEntity("sliding_door", SlidingDoorBlockEntity::new)
			.renderer(() -> SlidingDoorRenderer::new)
			.validBlocks(AllBlocks.TRAIN_DOOR, AllBlocks.FRAMED_GLASS_DOOR, AllBlocks.ANDESITE_DOOR,
				AllBlocks.BRASS_DOOR, AllBlocks.COPPER_DOOR)
			.register();

	public static final BlockEntityEntry<CopycatBlockEntity> COPYCAT =
		REGISTRATE.blockEntity("copycat", CopycatBlockEntity::new)
			.validBlocks(AllBlocks.COPYCAT_PANEL, AllBlocks.COPYCAT_STEP)
			.register();

	public static final BlockEntityEntry<FlapDisplayBlockEntity> FLAP_DISPLAY = REGISTRATE
		.blockEntity("flap_display", FlapDisplayBlockEntity::new)
		.instance(() -> ShaftlessCogwheelInstance::new)
		.renderer(() -> FlapDisplayRenderer::new)
		.validBlocks(AllBlocks.DISPLAY_BOARD)
		.register();

	public static final BlockEntityEntry<SignalBlockEntity> TRACK_SIGNAL = REGISTRATE
		.blockEntity("track_signal", SignalBlockEntity::new)
		.renderer(() -> SignalRenderer::new)
		.validBlocks(AllBlocks.TRACK_SIGNAL)
		.register();

	public static final BlockEntityEntry<TrackObserverBlockEntity> TRACK_OBSERVER = REGISTRATE
		.blockEntity("track_observer", TrackObserverBlockEntity::new)
		.renderer(() -> TrackObserverRenderer::new)
		.validBlocks(AllBlocks.TRACK_OBSERVER)
		.register();

	public static final BlockEntityEntry<ClipboardBlockEntity> CLIPBOARD = REGISTRATE
		.blockEntity("clipboard", ClipboardBlockEntity::new)
		.validBlocks(AllBlocks.CLIPBOARD)
		.register();

	public static void register() {}
}
