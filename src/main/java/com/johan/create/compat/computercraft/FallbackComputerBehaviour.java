package com.johan.create.compat.computercraft;

import com.johan.create.foundation.blockEntity.SmartBlockEntity;

public class FallbackComputerBehaviour extends AbstractComputerBehaviour {

	public FallbackComputerBehaviour(SmartBlockEntity te) {
		super(te);
	}

	@Override
	public boolean hasAttachedComputer() {
		return false;
	}

}
