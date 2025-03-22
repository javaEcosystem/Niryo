package com.johan.create.content.contraptions.elevator;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.light.TickingLightListener;
import com.johan.create.content.kinetics.base.ShaftInstance;

// TODO
public class ElevatorPulleyInstance extends ShaftInstance<ElevatorPulleyBlockEntity> implements DynamicInstance, TickingLightListener {

	public ElevatorPulleyInstance(MaterialManager materialManager, ElevatorPulleyBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	public boolean tickLightListener() {
		return false;
	}

	@Override
	public void beginFrame() {
	}

}
