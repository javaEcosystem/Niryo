package com.johan.create.content.contraptions.actors.roller;

import java.util.function.Predicate;

import com.johan.create.AllBlockEntityTypes;
import com.johan.create.AllBlocks;
import com.johan.create.content.contraptions.actors.AttachedActorBlock;
import com.johan.create.foundation.block.IBE;
import com.johan.create.foundation.placement.IPlacementHelper;
import com.johan.create.foundation.placement.PlacementHelpers;
import com.johan.create.foundation.placement.PoleHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RollerBlock extends AttachedActorBlock implements IBE<RollerBlockEntity> {

	public static DamageSource damageSourceRoller = new DamageSource("create.mechanical_roller");

	private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());

	public RollerBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return withWater(defaultBlockState().setValue(FACING, context.getHorizontalDirection()
			.getOpposite()), context);
	}

	@Override
	public Class<RollerBlockEntity> getBlockEntityClass() {
		return RollerBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends RollerBlockEntity> getBlockEntityType() {
		return AllBlockEntityTypes.MECHANICAL_ROLLER.get();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return Shapes.block();
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		withBlockEntityDo(pLevel, pPos, RollerBlockEntity::searchForSharedValues);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
		BlockHitResult ray) {
		ItemStack heldItem = player.getItemInHand(hand);

		IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
		if (!player.isShiftKeyDown() && player.mayBuild()) {
			if (placementHelper.matchesItem(heldItem)) {
				placementHelper.getOffset(player, world, state, pos, ray)
					.placeInWorld(world, (BlockItem) heldItem.getItem(), player, hand, ray);
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	private static class PlacementHelper extends PoleHelper<Direction> {

		public PlacementHelper() {
			super(AllBlocks.MECHANICAL_ROLLER::has, state -> state.getValue(FACING)
				.getClockWise()
				.getAxis(), FACING);
		}

		@Override
		public Predicate<ItemStack> getItemPredicate() {
			return AllBlocks.MECHANICAL_ROLLER::isIn;
		}

	}

}
