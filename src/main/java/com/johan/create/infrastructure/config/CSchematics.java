package com.johan.create.infrastructure.config;

import com.johan.create.foundation.config.ConfigBase;

public class CSchematics extends ConfigBase {

	public final ConfigBool creativePrintIncludesAir = b(false, "creativePrintIncludesAir", Comments.creativePrintIncludesAir);
	public final ConfigInt maxSchematics = i(10, 1, "maxSchematics", Comments.maxSchematics);
	public final ConfigInt maxTotalSchematicSize = i(256, 16, "maxTotalSchematicSize", Comments.kb, Comments.maxSize);
	public final ConfigInt maxSchematicPacketSize =
		i(1024, 256, 32767, "maxSchematicPacketSize", Comments.b, Comments.maxPacketSize);
	public final ConfigInt schematicIdleTimeout = i(600, 100, "schematicIdleTimeout", Comments.idleTimeout);

	public final ConfigGroup schematicannon = group(0, "schematicannon", "Schematicannon");
	public final ConfigInt schematicannonDelay = i(10, 1, "schematicannonDelay", Comments.delay);
	public final ConfigInt schematicannonShotsPerGunpowder = i(400, 1, "schematicannonShotsPerGunpowder", Comments.schematicannonShotsPerGunpowder);

	@Override
	public String getName() {
		return "schematics";
	}

	private static class Comments {
		static String kb = "[in KiloBytes]";
		static String b = "[in Bytes]";
		static String maxSchematics =
			"The amount of Schematics a player can upload until previous ones are overwritten.";
		static String maxSize = "The maximum allowed file size of uploaded Schematics.";
		static String maxPacketSize = "The maximum packet size uploaded Schematics are split into.";
		static String idleTimeout =
			"Amount of game ticks without new packets arriving until an active schematic upload process is discarded.";
		static String delay = "Amount of game ticks between shots of the cannon. Higher => Slower";
		static String schematicannonShotsPerGunpowder = "Amount of blocks a Schematicannon can print per Gunpowder item provided.";
		static String creativePrintIncludesAir =
			"Whether placing a Schematic directly in Creative Mode should replace world blocks with Air";
	}

}
