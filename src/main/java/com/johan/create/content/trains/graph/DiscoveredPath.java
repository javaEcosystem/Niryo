package com.johan.create.content.trains.graph;

import com.johan.create.content.trains.station.GlobalStation;
import com.johan.create.foundation.utility.Couple;

import java.util.List;

public class DiscoveredPath {
	public List<Couple<TrackNode>> path;
	public GlobalStation destination;
	public double distance;
	public double cost;

	public DiscoveredPath(double distance, double cost, List<Couple<TrackNode>> path, GlobalStation destination) {
		this.distance = distance;
		this.cost = cost;
		this.path = path;
		this.destination = destination;
	}
}
