package com.johan.create.foundation.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.johan.create.content.trains.track.TrackBlock;
import com.johan.create.content.decoration.slidingDoor.SlidingDoorBlock;

/**
 * Implementing this interface will allow you to have bigger outlines when overriding {@link BlockBehaviour#getInteractionShape(BlockState, BlockGetter, BlockPos)}
 * <p>
 * For examples look at {@link TrackBlock} and {@link SlidingDoorBlock}
 */
public interface IHaveBigOutline { }
