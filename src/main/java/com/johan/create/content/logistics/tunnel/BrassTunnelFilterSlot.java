package com.johan.create.content.logistics.tunnel;

import com.johan.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.johan.create.foundation.utility.VecHelper;

import net.minecraft.world.phys.Vec3;

public class BrassTunnelFilterSlot extends ValueBoxTransform.Sided {

	@Override
	protected Vec3 getSouthLocation() {
		return VecHelper.voxelSpace(8, 13, 15.5f);
	}

}
