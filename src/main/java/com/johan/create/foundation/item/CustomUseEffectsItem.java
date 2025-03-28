package com.johan.create.foundation.item;

import java.util.Random;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface CustomUseEffectsItem {
	/**
	 * Called to determine if use effects should be applied for this item.
	 *
	 * @param stack The ItemStack being used.
	 * @param entity The LivingEntity using the item.
	 * @return null for default behavior, or boolean to override default behavior
	 */
	default Boolean shouldTriggerUseEffects(ItemStack stack, LivingEntity entity) {
		return null;
	}

	/**
	 * Called when use effects should be applied for this item.
	 *
	 * @param stack The ItemStack being used.
	 * @param entity The LivingEntity using the item.
	 * @param count The amount of times effects should be applied. Can safely be ignored.
	 * @param random The LivingEntity's Random.
	 * @return if the default behavior should be cancelled or not
	 */
	boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int count, Random random);
}
