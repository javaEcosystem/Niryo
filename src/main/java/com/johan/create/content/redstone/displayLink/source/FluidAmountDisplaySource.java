package com.johan.create.content.redstone.displayLink.source;

import com.johan.create.content.redstone.displayLink.DisplayLinkContext;
import com.johan.create.content.redstone.displayLink.target.DisplayTargetStats;
import com.johan.create.content.redstone.smartObserver.SmartObserverBlockEntity;
import com.johan.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.johan.create.foundation.blockEntity.behaviour.inventory.TankManipulationBehaviour;
import com.johan.create.foundation.utility.Components;
import com.johan.create.foundation.utility.FluidFormatter;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidAmountDisplaySource extends SingleLineDisplaySource {

	@Override
	protected MutableComponent provideLine(DisplayLinkContext context, DisplayTargetStats stats) {
		BlockEntity sourceBE = context.getSourceBlockEntity();
		if (!(sourceBE instanceof SmartObserverBlockEntity cobe))
			return EMPTY_LINE;

		TankManipulationBehaviour tankManipulationBehaviour = cobe.getBehaviour(TankManipulationBehaviour.OBSERVE);
		FilteringBehaviour filteringBehaviour = cobe.getBehaviour(FilteringBehaviour.TYPE);
		IFluidHandler handler = tankManipulationBehaviour.getInventory();

		if (handler == null)
			return EMPTY_LINE;

		long collected = 0;
		for (int i = 0; i < handler.getTanks(); i++) {
			FluidStack stack = handler.getFluidInTank(i);
			if (stack.isEmpty())
				continue;
			if (!filteringBehaviour.test(stack))
				continue;
			collected += stack.getAmount();
		}

		return Components.literal(FluidFormatter.asString(collected, false));
	}

	@Override
	protected String getTranslationKey() {
		return "fluid_amount";
	}

	@Override
	protected boolean allowsLabeling(DisplayLinkContext context) {
		return true;
	}
}
