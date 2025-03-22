package com.johan.create.content.contraptions.pulley;

import com.jozufozu.flywheel.util.box.GridAlignedBB;
import com.johan.create.AllBlocks;
import com.johan.create.content.contraptions.render.ContraptionLighter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class PulleyLighter extends ContraptionLighter<PulleyContraption> {
    public PulleyLighter(PulleyContraption contraption) {
        super(contraption);
    }

    @Override
    public GridAlignedBB getContraptionBounds() {

        GridAlignedBB bounds = GridAlignedBB.from(contraption.bounds);

        Level world = contraption.entity.level;

        BlockPos.MutableBlockPos pos = contraption.anchor.mutable();
        while (!AllBlocks.ROPE_PULLEY.has(world.getBlockState(pos)) && pos.getY() < world.getMaxBuildHeight())
            pos.move(0, 1, 0);

        bounds.translate(pos);
        bounds.setMinY(world.getMinBuildHeight());
        return bounds;
    }
}
