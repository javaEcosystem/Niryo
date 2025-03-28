package com.johan.create.foundation.ponder.instruction;

import com.johan.create.foundation.ponder.PonderScene;

public class MarkAsFinishedInstruction extends PonderInstruction {

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public void tick(PonderScene scene) {
		scene.setFinished(true);
	}

	@Override
	public void onScheduled(PonderScene scene) {
		scene.stopCounting();
	}

}
