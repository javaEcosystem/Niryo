package com.johan.create.content.logistics.funnel;

import com.johan.create.foundation.networking.BlockEntityDataPacket;

import net.minecraft.network.FriendlyByteBuf;

public class FunnelFlapPacket extends BlockEntityDataPacket<FunnelBlockEntity> {

    private final boolean inwards;

    public FunnelFlapPacket(FriendlyByteBuf buffer) {
        super(buffer);

        inwards = buffer.readBoolean();
    }

    public FunnelFlapPacket(FunnelBlockEntity blockEntity, boolean inwards) {
        super(blockEntity.getBlockPos());
        this.inwards = inwards;
    }

    @Override
    protected void writeData(FriendlyByteBuf buffer) {
        buffer.writeBoolean(inwards);
    }

    @Override
    protected void handlePacket(FunnelBlockEntity blockEntity) {
        blockEntity.flap(inwards);
    }
}
