package com.johan.create.content.kinetics.drill;

import javax.annotation.Nullable;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.johan.create.AllTags;
import com.johan.create.content.contraptions.behaviour.MovementContext;
import com.johan.create.content.contraptions.render.ActorInstance;
import com.johan.create.content.contraptions.render.ContraptionMatrices;
import com.johan.create.content.contraptions.render.ContraptionRenderDispatcher;
import com.johan.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.johan.create.foundation.utility.VecHelper;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DrillMovementBehaviour extends BlockBreakingMovementBehaviour {

	@Override
	public boolean isActive(MovementContext context) {
		return super.isActive(context)
			&& !VecHelper.isVecPointingTowards(context.relativeMotion, context.state.getValue(DrillBlock.FACING)
				.getOpposite());
	}

	@Override
	public Vec3 getActiveAreaOffset(MovementContext context) {
		return Vec3.atLowerCornerOf(context.state.getValue(DrillBlock.FACING)
			.getNormal()).scale(.65f);
	}

	@Override
	@OnlyIn(value = Dist.CLIENT)
	public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
		ContraptionMatrices matrices, MultiBufferSource buffer) {
        if (!ContraptionRenderDispatcher.canInstance())
			DrillRenderer.renderInContraption(context, renderWorld, matrices, buffer);
	}

	@Override
	public boolean hasSpecialInstancedRendering() {
		return true;
	}

	@Nullable
	@Override
	public ActorInstance createInstance(MaterialManager materialManager, VirtualRenderWorld simulationWorld, MovementContext context) {
		return new DrillActorInstance(materialManager, simulationWorld, context);
	}

	@Override
	protected DamageSource getDamageSource() {
		return DrillBlock.damageSourceDrill;
	}

	@Override
	public boolean canBreak(Level world, BlockPos breakingPos, BlockState state) {
		return super.canBreak(world, breakingPos, state) && !state.getCollisionShape(world, breakingPos)
			.isEmpty() && !AllTags.AllBlockTags.TRACKS.matches(state);
	}

}
