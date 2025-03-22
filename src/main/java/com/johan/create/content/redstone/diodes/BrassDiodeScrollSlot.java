package com.johan.create.content.redstone.diodes;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.johan.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.johan.create.foundation.utility.AngleHelper;
import com.johan.create.foundation.utility.VecHelper;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class BrassDiodeScrollSlot extends ValueBoxTransform {

	@Override
	public Vec3 getLocalOffset(BlockState state) {
		return VecHelper.voxelSpace(8, 2.6f, 8);
	}

	@Override
	public void rotate(BlockState state, PoseStack ms) {
		float yRot = AngleHelper.horizontalAngle(state.getValue(BlockStateProperties.HORIZONTAL_FACING)) + 180;
		TransformStack.cast(ms)
			.rotateY(yRot)
			.rotateX(90);
	}

	@Override
	public int getOverrideColor() {
		return 0x592424;
	}

}
