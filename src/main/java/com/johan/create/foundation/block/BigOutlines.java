package com.johan.create.foundation.block;

import com.johan.create.foundation.utility.AnimationTickHolder;
import com.johan.create.foundation.utility.RaycastHelper;
import com.johan.create.foundation.utility.VecHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;


/**
 * For mods wanting to use this take a look at {@link IHaveBigOutline}
 */
public class BigOutlines {
	static BlockHitResult result = null;

	public static void pick() {
		Minecraft mc = Minecraft.getInstance();
		if (!(mc.cameraEntity instanceof LocalPlayer player))
			return;
		if (mc.level == null)
			return;

		result = null;

		Vec3 origin = player.getEyePosition(AnimationTickHolder.getPartialTicks(mc.level));

		double maxRange = mc.hitResult == null ? Double.MAX_VALUE
			: mc.hitResult.getLocation()
				.distanceToSqr(origin);

		AttributeInstance range = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
		Vec3 target = RaycastHelper.getTraceTarget(player, Math.min(maxRange, range.getValue()) + 1, origin);

		RaycastHelper.rayTraceUntil(origin, target, pos -> {
			MutableBlockPos p = BlockPos.ZERO.mutable();

			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						p.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
						BlockState blockState = mc.level.getBlockState(p);

						if (!(blockState.getBlock() instanceof IHaveBigOutline))
							continue;

						BlockHitResult hit = blockState.getInteractionShape(mc.level, p)
								.clip(origin, target, p.immutable());
						if (hit == null)
							continue;

						if (result != null && Vec3.atCenterOf(p)
								.distanceToSqr(origin) >= Vec3.atCenterOf(result.getBlockPos())
								.distanceToSqr(origin))
							continue;

						Vec3 vec = hit.getLocation();
						double interactionDist = vec.distanceToSqr(origin);
						if (interactionDist >= maxRange)
							continue;

						BlockPos hitPos = hit.getBlockPos();

						// pacifies ServerGamePacketListenerImpl.handleUseItemOn
						vec = vec.subtract(Vec3.atCenterOf(hitPos));
						vec = VecHelper.clampComponentWise(vec, 1);
						vec = vec.add(Vec3.atCenterOf(hitPos));

						result = new BlockHitResult(vec, hit.getDirection(), hitPos, hit.isInside());
					}
				}
			}

			return result != null;
		});

		if (result != null)
			mc.hitResult = result;
	}
}
