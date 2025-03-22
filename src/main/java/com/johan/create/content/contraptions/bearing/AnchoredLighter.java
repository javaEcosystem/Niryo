package com.johan.create.content.contraptions.bearing;

import com.jozufozu.flywheel.util.box.GridAlignedBB;
import com.johan.create.content.contraptions.Contraption;
import com.johan.create.content.contraptions.render.ContraptionLighter;

public class AnchoredLighter extends ContraptionLighter<Contraption> {

    public AnchoredLighter(Contraption contraption) {
        super(contraption);
    }

    @Override
    public GridAlignedBB getContraptionBounds() {
        GridAlignedBB bb = GridAlignedBB.from(contraption.bounds);
        bb.translate(contraption.anchor);
        return bb;
    }
}
