package com.johan.create.content.processing.basin;

import com.johan.create.foundation.data.AssetLookup;
import com.johan.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

public class BasinGenerator extends SpecialBlockStateGen {

	@Override
	protected int getXRotation(BlockState state) {
		return 0;
	}

	@Override
	protected int getYRotation(BlockState state) {
		return horizontalAngle(state.getValue(BasinBlock.FACING));
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
		BlockState state) {
		if (state.getValue(BasinBlock.FACING).getAxis().isVertical())
			return AssetLookup.partialBaseModel(ctx, prov);
		return AssetLookup.partialBaseModel(ctx, prov, "directional");
	}

}
