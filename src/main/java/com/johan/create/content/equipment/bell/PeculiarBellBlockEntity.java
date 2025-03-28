package com.johan.create.content.equipment.bell;

import com.jozufozu.flywheel.core.PartialModel;
import com.johan.create.AllPartialModels;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PeculiarBellBlockEntity extends AbstractBellBlockEntity {

	public PeculiarBellBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public PartialModel getBellModel() {
		return AllPartialModels.PECULIAR_BELL;
	}

}
