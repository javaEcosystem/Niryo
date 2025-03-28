package com.johan.create.content.trains.graph;

import com.johan.create.Create;
import com.johan.create.foundation.networking.SimplePacketBase;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class TrackGraphRequestPacket extends SimplePacketBase {

	private int netId;

	public TrackGraphRequestPacket(int netId) {
		this.netId = netId;
	}

	public TrackGraphRequestPacket(FriendlyByteBuf buffer) {
		netId = buffer.readInt();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeInt(netId);
	}

	@Override
	public boolean handle(Context context) {
		context.enqueueWork(() -> {
				for (TrackGraph trackGraph : Create.RAILWAYS.trackNetworks.values()) {
				if (trackGraph.netId == netId) {
					Create.RAILWAYS.sync.sendFullGraphTo(trackGraph, context.getSender());
					break;
				}
			}
		});
		return true;
	}

}
