package com.johan.create.foundation.ponder.instruction;

public class DelayInstruction extends TickingInstruction {

	public DelayInstruction(int ticks) {
		super(true, ticks);
	}

}
