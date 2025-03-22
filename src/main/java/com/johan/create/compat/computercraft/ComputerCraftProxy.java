package com.johan.create.compat.computercraft;

import java.util.function.Function;

import com.johan.create.compat.Mods;
import com.johan.create.compat.computercraft.implementation.ComputerBehaviour;
import com.johan.create.foundation.blockEntity.SmartBlockEntity;

public class ComputerCraftProxy {

	public static void register() {
		fallbackFactory = FallbackComputerBehaviour::new;
		Mods.COMPUTERCRAFT.executeIfInstalled(() -> ComputerCraftProxy::registerWithDependency);
	}

	private static void registerWithDependency() {
		/* Comment if computercraft.implementation is not in the source set */
		computerFactory = ComputerBehaviour::new;
	}

	private static Function<SmartBlockEntity, ? extends AbstractComputerBehaviour> fallbackFactory;
	private static Function<SmartBlockEntity, ? extends AbstractComputerBehaviour> computerFactory;

	public static AbstractComputerBehaviour behaviour(SmartBlockEntity sbe) {
		if (computerFactory == null)
			return fallbackFactory.apply(sbe);
		return computerFactory.apply(sbe);
	}

}
