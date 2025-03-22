package com.johan.create.foundation.gui.menu;

import com.johan.create.AllPackets;

public interface IClearableMenu {

	default void sendClearPacket() {
		AllPackets.getChannel().sendToServer(new ClearMenuPacket());
	}

	public void clearContents();

}
