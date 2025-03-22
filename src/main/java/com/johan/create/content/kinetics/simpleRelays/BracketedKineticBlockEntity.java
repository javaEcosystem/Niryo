package com.johan.create.content.kinetics.simpleRelays;

import java.util.List;

import com.johan.create.content.contraptions.ITransformableBlockEntity;
import com.johan.create.content.contraptions.StructureTransform;
import com.johan.create.content.decoration.bracket.BracketedBlockEntityBehaviour;
import com.johan.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BracketedKineticBlockEntity extends SimpleKineticBlockEntity implements ITransformableBlockEntity {

	public BracketedKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours
			.add(new BracketedBlockEntityBehaviour(this, state -> state.getBlock() instanceof AbstractSimpleShaftBlock));
		super.addBehaviours(behaviours);
	}

	@Override
	public void transform(StructureTransform transform) {
		BracketedBlockEntityBehaviour bracketBehaviour = getBehaviour(BracketedBlockEntityBehaviour.TYPE);
		if (bracketBehaviour != null) {
			bracketBehaviour.transformBracket(transform);
		}
	}

}
