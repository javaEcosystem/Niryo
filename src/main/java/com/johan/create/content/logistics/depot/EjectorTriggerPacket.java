package com.johan.create.content.logistics.depot;

import com.johan.create.foundation.networking.BlockEntityConfigurationPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class EjectorTriggerPacket extends BlockEntityConfigurationPacket<EjectorBlockEntity> {

	public EjectorTriggerPacket(BlockPos pos) {
		super(pos);
	}

	public EjectorTriggerPacket(FriendlyByteBuf buffer) {
		super(buffer);
	}

	@Override
	protected void writeSettings(FriendlyByteBuf buffer) {}

	@Override
	protected void readSettings(FriendlyByteBuf buffer) {}

	@Override
	protected void applySettings(EjectorBlockEntity be) {
		be.activate();
	}

}
