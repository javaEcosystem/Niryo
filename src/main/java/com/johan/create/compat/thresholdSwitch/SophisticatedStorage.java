package com.johan.create.compat.thresholdSwitch;

import com.johan.create.compat.Mods;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;

public class SophisticatedStorage implements ThresholdSwitchCompat {

	@Override
	public boolean isFromThisMod(BlockEntity be) {
		if (be == null)
			return false;

		String namespace = be.getType()
			.getRegistryName()
			.getNamespace();

		return
			Mods.SOPHISTICATEDSTORAGE.id().equals(namespace)
			|| Mods.SOPHISTICATEDBACKPACKS.id().equals(namespace);
	}

	@Override
	public long getSpaceInSlot(IItemHandler inv, int slot) {
		return ((long) inv.getSlotLimit(slot) * inv.getStackInSlot(slot).getMaxStackSize()) / 64;
	}

}
