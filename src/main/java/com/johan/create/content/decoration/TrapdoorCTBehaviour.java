package com.johan.create.content.decoration;

import org.jetbrains.annotations.Nullable;

import com.johan.create.AllSpriteShifts;
import com.johan.create.foundation.block.connected.CTSpriteShiftEntry;
import com.johan.create.foundation.block.connected.ConnectedTextureBehaviour;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class TrapdoorCTBehaviour extends ConnectedTextureBehaviour.Base {

	@Override
	public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
		return AllSpriteShifts.FRAMED_GLASS;
	}

	@Override
	public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos,
		BlockPos otherPos, Direction face, Direction primaryOffset, Direction secondaryOffset) {
		return state.getBlock() == other.getBlock()
			&& TrainTrapdoorBlock.isConnected(state, other, primaryOffset == null ? secondaryOffset : primaryOffset);
	}

}
