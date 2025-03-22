package com.johan.create.compat.computercraft.implementation;

import com.johan.create.compat.computercraft.AbstractComputerBehaviour;
import com.johan.create.compat.computercraft.implementation.peripherals.DisplayLinkPeripheral;
import com.johan.create.compat.computercraft.implementation.peripherals.SequencedGearshiftPeripheral;
import com.johan.create.compat.computercraft.implementation.peripherals.SpeedControllerPeripheral;
import com.johan.create.compat.computercraft.implementation.peripherals.SpeedGaugePeripheral;
import com.johan.create.compat.computercraft.implementation.peripherals.StationPeripheral;
import com.johan.create.compat.computercraft.implementation.peripherals.StressGaugePeripheral;
import com.johan.create.content.kinetics.gauge.SpeedGaugeBlockEntity;
import com.johan.create.content.kinetics.gauge.StressGaugeBlockEntity;
import com.johan.create.content.kinetics.speedController.SpeedControllerBlockEntity;
import com.johan.create.content.kinetics.transmission.sequencer.SequencedGearshiftBlockEntity;
import com.johan.create.content.redstone.displayLink.DisplayLinkBlockEntity;
import com.johan.create.content.trains.station.StationBlockEntity;
import com.johan.create.foundation.blockEntity.SmartBlockEntity;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

public class ComputerBehaviour extends AbstractComputerBehaviour {

	protected static final Capability<IPeripheral> PERIPHERAL_CAPABILITY =
		CapabilityManager.get(new CapabilityToken<>() {
		});
	LazyOptional<IPeripheral> peripheral;
	NonNullSupplier<IPeripheral> peripheralSupplier;

	public ComputerBehaviour(SmartBlockEntity te) {
		super(te);
		this.peripheralSupplier = getPeripheralFor(te);
	}

	public static NonNullSupplier<IPeripheral> getPeripheralFor(SmartBlockEntity be) {
		if (be instanceof SpeedControllerBlockEntity scbe)
			return () -> new SpeedControllerPeripheral(scbe, scbe.targetSpeed);
		if (be instanceof DisplayLinkBlockEntity dlbe)
			return () -> new DisplayLinkPeripheral(dlbe);
		if (be instanceof SequencedGearshiftBlockEntity sgbe)
			return () -> new SequencedGearshiftPeripheral(sgbe);
		if (be instanceof SpeedGaugeBlockEntity sgbe)
			return () -> new SpeedGaugePeripheral(sgbe);
		if (be instanceof StressGaugeBlockEntity sgbe)
			return () -> new StressGaugePeripheral(sgbe);
		if (be instanceof StationBlockEntity sbe)
			return () -> new StationPeripheral(sbe);

		throw new IllegalArgumentException("No peripheral available for " + be.getType()
			.getRegistryName());
	}

	@Override
	public <T> boolean isPeripheralCap(Capability<T> cap) {
		return cap == PERIPHERAL_CAPABILITY;
	}

	@Override
	public <T> LazyOptional<T> getPeripheralCapability() {
		if (peripheral == null || !peripheral.isPresent())
			peripheral = LazyOptional.of(peripheralSupplier);
		return peripheral.cast();
	}

	@Override
	public void removePeripheral() {
		if (peripheral != null)
			peripheral.invalidate();
	}

}
