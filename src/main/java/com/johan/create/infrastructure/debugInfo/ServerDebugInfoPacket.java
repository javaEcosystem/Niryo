package com.johan.create.infrastructure.debugInfo;

import java.util.List;
import java.util.Objects;

import com.johan.create.foundation.networking.SimplePacketBase;
import com.johan.create.foundation.utility.DyeHelper;
import com.johan.create.foundation.utility.Lang;
import com.johan.create.infrastructure.debugInfo.element.DebugInfoSection;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ServerDebugInfoPacket extends SimplePacketBase {

	private final List<DebugInfoSection> serverInfo;
	private final Player player;

	public ServerDebugInfoPacket(Player player) {
		this.serverInfo = DebugInformation.getServerInfo();
		this.player = player;
	}

	public ServerDebugInfoPacket(FriendlyByteBuf buffer) {
		this.serverInfo = buffer.readList(DebugInfoSection::readDirect);
		this.player = null;
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeCollection(this.serverInfo, (buf, section) -> section.write(player, buf));
	}

	@Override
	public boolean handle(NetworkEvent.Context context) {
		context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::handleOnClient));
		return true;
	}

	private void printInfo(String side, Player player, List<DebugInfoSection> sections, StringBuilder output) {
		output.append("<details>");
		output.append('\n');
		output.append("<summary>")
			.append(side)
			.append(" Info")
			.append("</summary>");
		output.append('\n')
			.append('\n');
		output.append("```");
		output.append('\n');

		for (int i = 0; i < sections.size(); i++) {
			if (i != 0) {
				output.append('\n');
			}
			sections.get(i)
				.print(player, line -> output.append(line)
					.append('\n'));
		}

		output.append("```");
		output.append('\n')
			.append('\n');
		output.append("</details>");
		output.append('\n');
	}

	@OnlyIn(Dist.CLIENT)
	private void handleOnClient() {
		Player player = Objects.requireNonNull(Minecraft.getInstance().player);
		StringBuilder output = new StringBuilder();
		List<DebugInfoSection> clientInfo = DebugInformation.getClientInfo();

		printInfo("Client", player, clientInfo, output);
		output.append("\n\n");
		printInfo("Server", player, serverInfo, output);

		String text = output.toString();
		Minecraft.getInstance().keyboardHandler.setClipboard(text);
		Lang.translate("command.debuginfo.saved_to_clipboard")
			.color(DyeHelper.DYE_TABLE.get(DyeColor.LIME)
				.getFirst())
			.sendChat(player);
	}
}
