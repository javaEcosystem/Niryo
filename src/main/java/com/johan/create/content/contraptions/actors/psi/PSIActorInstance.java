package com.johan.create.content.contraptions.actors.psi;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.johan.create.content.contraptions.behaviour.MovementContext;
import com.johan.create.content.contraptions.render.ActorInstance;
import com.johan.create.foundation.utility.AnimationTickHolder;
import com.johan.create.foundation.utility.animation.LerpedFloat;

public class PSIActorInstance extends ActorInstance {

	private final PIInstance instance;

	public PSIActorInstance(MaterialManager materialManager, VirtualRenderWorld world, MovementContext context) {
		super(materialManager, world, context);

		instance = new PIInstance(materialManager, context.state, context.localPos);

		instance.init(false);
		instance.middle.setBlockLight(localBlockLight());
		instance.top.setBlockLight(localBlockLight());
	}

	@Override
	public void beginFrame() {
		LerpedFloat lf = PortableStorageInterfaceMovement.getAnimation(context);
		instance.tick(lf.settled());
		instance.beginFrame(lf.getValue(AnimationTickHolder.getPartialTicks()));
	}

}
