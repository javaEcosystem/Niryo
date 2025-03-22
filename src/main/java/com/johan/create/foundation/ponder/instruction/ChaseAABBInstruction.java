package com.johan.create.foundation.ponder.instruction;

import com.johan.create.foundation.ponder.PonderPalette;
import com.johan.create.foundation.ponder.PonderScene;

import net.minecraft.world.phys.AABB;

public class ChaseAABBInstruction extends TickingInstruction {

	private AABB bb;
	private Object slot;
	private PonderPalette color;

	public ChaseAABBInstruction(PonderPalette color, Object slot, AABB bb, int ticks) {
		super(false, ticks);
		this.color = color;
		this.slot = slot;
		this.bb = bb;
	}

	@Override
	public void tick(PonderScene scene) {
		super.tick(scene);
		scene.getOutliner()
			.chaseAABB(slot, bb)
			.lineWidth(1 / 16f)
			.colored(color.getColor());
	}

}
