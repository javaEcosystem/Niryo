package com.johan.create.content.fluids.pipes;

import java.util.List;

import com.johan.create.AllBlocks;
import com.johan.create.content.contraptions.ITransformableBlockEntity;
import com.johan.create.content.contraptions.StructureTransform;
import com.johan.create.content.decoration.bracket.BracketedBlockEntityBehaviour;
import com.johan.create.content.fluids.FluidPropagator;
import com.johan.create.content.fluids.FluidTransportBehaviour;
import com.johan.create.foundation.blockEntity.SmartBlockEntity;
import com.johan.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FluidPipeBlockEntity extends SmartBlockEntity implements ITransformableBlockEntity {

	public FluidPipeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours.add(new StandardPipeFluidTransportBehaviour(this));
		behaviours.add(new BracketedBlockEntityBehaviour(this, this::canHaveBracket));
		registerAwardables(behaviours, FluidPropagator.getSharedTriggers());
	}

	@Override
	public void transform(StructureTransform transform) {
		BracketedBlockEntityBehaviour bracketBehaviour = getBehaviour(BracketedBlockEntityBehaviour.TYPE);
		if (bracketBehaviour != null) {
			bracketBehaviour.transformBracket(transform);
		}
	}

	private boolean canHaveBracket(BlockState state) {
		return !(state.getBlock() instanceof EncasedPipeBlock);
	}

	class StandardPipeFluidTransportBehaviour extends FluidTransportBehaviour {

		public StandardPipeFluidTransportBehaviour(SmartBlockEntity be) {
			super(be);
		}

		@Override
		public boolean canHaveFlowToward(BlockState state, Direction direction) {
			return (FluidPipeBlock.isPipe(state) || state.getBlock() instanceof EncasedPipeBlock)
				&& state.getValue(FluidPipeBlock.PROPERTY_BY_DIRECTION.get(direction));
		}

		@Override
		public AttachmentTypes getRenderedRimAttachment(BlockAndTintGetter world, BlockPos pos, BlockState state,
			Direction direction) {
			AttachmentTypes attachment = super.getRenderedRimAttachment(world, pos, state, direction);

			BlockPos offsetPos = pos.relative(direction);
			BlockState otherState = world.getBlockState(offsetPos);

			if (state.getBlock() instanceof EncasedPipeBlock && attachment != AttachmentTypes.DRAIN)
				return AttachmentTypes.NONE;

			if (attachment == AttachmentTypes.RIM && !FluidPipeBlock.isPipe(otherState)
				&& !AllBlocks.MECHANICAL_PUMP.has(otherState) && !AllBlocks.ENCASED_FLUID_PIPE.has(otherState)) {
				FluidTransportBehaviour pipeBehaviour =
					BlockEntityBehaviour.get(world, offsetPos, FluidTransportBehaviour.TYPE);
				if (pipeBehaviour != null)
					if (pipeBehaviour.canHaveFlowToward(otherState, direction.getOpposite()))
						return AttachmentTypes.CONNECTION;
			}

			if (attachment == AttachmentTypes.RIM && !FluidPipeBlock.shouldDrawRim(world, pos, state, direction))
				return AttachmentTypes.CONNECTION;
			if (attachment == AttachmentTypes.NONE && state.getValue(FluidPipeBlock.PROPERTY_BY_DIRECTION.get(direction)))
				return AttachmentTypes.CONNECTION;
			return attachment;
		}

	}

}
