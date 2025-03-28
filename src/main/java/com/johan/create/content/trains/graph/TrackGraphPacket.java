package com.johan.create.content.trains.graph;

import java.util.UUID;

import com.johan.create.CreateClient;
import com.johan.create.content.trains.GlobalRailwayManager;
import com.johan.create.foundation.networking.SimplePacketBase;

import net.minecraftforge.network.NetworkEvent.Context;

public abstract class TrackGraphPacket extends SimplePacketBase {

	public UUID graphId;
	public int netId;
	public boolean packetDeletesGraph;

	@Override
	public boolean handle(Context context) {
		context.enqueueWork(() -> handle(CreateClient.RAILWAYS, CreateClient.RAILWAYS.getOrCreateGraph(graphId, netId)));
		return true;
	}

	protected abstract void handle(GlobalRailwayManager manager, TrackGraph graph);

}
