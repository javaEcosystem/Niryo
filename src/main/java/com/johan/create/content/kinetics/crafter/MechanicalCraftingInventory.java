package com.johan.create.content.kinetics.crafter;

import org.apache.commons.lang3.tuple.Pair;

import com.johan.create.content.kinetics.crafter.RecipeGridHandler.GroupedItems;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

public class MechanicalCraftingInventory extends CraftingContainer {

	private static final AbstractContainerMenu dummyContainer = new AbstractContainerMenu(null, -1) {
		public boolean stillValid(Player playerIn) {
			return false;
		}
	};

	public MechanicalCraftingInventory(GroupedItems items) {
		super(dummyContainer, items.width, items.height);
		for (int y = 0; y < items.height; y++) {
			for (int x = 0; x < items.width; x++) {
				ItemStack stack = items.grid.get(Pair.of(x + items.minX, y + items.minY));
				setItem(x + (items.height - y - 1) * items.width,
						stack == null ? ItemStack.EMPTY : stack.copy());
			}
		}
	}

}
