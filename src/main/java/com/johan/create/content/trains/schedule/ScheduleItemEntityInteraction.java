package com.johan.create.content.trains.schedule;

import com.johan.create.AllItems;
import com.johan.create.AllSoundEvents;
import com.johan.create.content.contraptions.Contraption;
import com.johan.create.content.trains.entity.CarriageContraption;
import com.johan.create.content.trains.entity.CarriageContraptionEntity;
import com.johan.create.content.trains.entity.Train;
import com.johan.create.foundation.utility.Couple;
import com.johan.create.foundation.utility.Lang;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ScheduleItemEntityInteraction {

	@SubscribeEvent
	public static void interactWithConductor(EntityInteractSpecific event) {
		Entity entity = event.getTarget();
		Player player = event.getPlayer();
		if (player == null || entity == null)
			return;
		if (player.isSpectator())
			return;

		Entity rootVehicle = entity.getRootVehicle();
		if (!(rootVehicle instanceof CarriageContraptionEntity))
			return;
		if (!(entity instanceof LivingEntity living))
			return;
		if (player.getCooldowns()
			.isOnCooldown(AllItems.SCHEDULE.get()))
			return;

		ItemStack itemStack = event.getItemStack();
		if (itemStack.getItem()instanceof ScheduleItem si) {
			InteractionResult result = si.handScheduleTo(itemStack, player, living, event.getHand());
			if (result.consumesAction()) {
				player.getCooldowns()
					.addCooldown(AllItems.SCHEDULE.get(), 5);
				event.setCancellationResult(result);
				event.setCanceled(true);
				return;
			}
		}

		if (event.getHand() == InteractionHand.OFF_HAND)
			return;

		CarriageContraptionEntity cce = (CarriageContraptionEntity) rootVehicle;
		Contraption contraption = cce.getContraption();
		if (!(contraption instanceof CarriageContraption cc))
			return;

		Train train = cce.getCarriage().train;
		if (train == null)
			return;
		if (train.runtime.getSchedule() == null)
			return;

		Integer seatIndex = contraption.getSeatMapping()
			.get(entity.getUUID());
		if (seatIndex == null)
			return;
		BlockPos seatPos = contraption.getSeats()
			.get(seatIndex);
		Couple<Boolean> directions = cc.conductorSeats.get(seatPos);
		if (directions == null)
			return;

		boolean onServer = !event.getWorld().isClientSide;

		if (train.runtime.paused && !train.runtime.completed) {
			if (onServer) {
				train.runtime.paused = false;
				AllSoundEvents.CONFIRM.playOnServer(player.level, player.blockPosition(), 1, 1);
				player.displayClientMessage(Lang.translateDirect("schedule.continued"), true);
			}

			player.getCooldowns()
				.addCooldown(AllItems.SCHEDULE.get(), 5);
			event.setCancellationResult(InteractionResult.SUCCESS);
			event.setCanceled(true);
			return;
		}

		ItemStack itemInHand = player.getItemInHand(event.getHand());
		if (!itemInHand.isEmpty()) {
			if (onServer) {
				AllSoundEvents.DENY.playOnServer(player.level, player.blockPosition(), 1, 1);
				player.displayClientMessage(Lang.translateDirect("schedule.remove_with_empty_hand"), true);
			}
			event.setCancellationResult(InteractionResult.SUCCESS);
			event.setCanceled(true);
			return;
		}

		if (onServer) {
			AllSoundEvents.playItemPickup(player);
			player.displayClientMessage(
				Lang.translateDirect(
					train.runtime.isAutoSchedule ? "schedule.auto_removed_from_train" : "schedule.removed_from_train"),
				true);

			player.getInventory()
				.placeItemBackInInventory(train.runtime.returnSchedule());
		}

		player.getCooldowns()
			.addCooldown(AllItems.SCHEDULE.get(), 5);
		event.setCancellationResult(InteractionResult.SUCCESS);
		event.setCanceled(true);
		return;
	}

}
