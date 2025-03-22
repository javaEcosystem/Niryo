package com.johan.create.content.logistics.crate;

import java.util.List;

import com.johan.create.foundation.blockEntity.SmartBlockEntity;
import com.johan.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CrateBlockEntity extends SmartBlockEntity {

	public CrateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

}
