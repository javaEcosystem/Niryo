package com.johan.create.content.trains.track;

import net.minecraft.core.BlockPos;

public record BezierTrackPointLocation(BlockPos curveTarget, int segment) {
}
