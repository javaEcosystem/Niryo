package com.johan.create.content.kinetics.waterwheel;

import net.minecraft.world.level.block.state.BlockState;

public record WaterWheelModelKey(boolean large, BlockState state, BlockState material) {
}
