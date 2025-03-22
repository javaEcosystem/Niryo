package com.johan.create.content.kinetics.millstone;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.johan.create.AllPartialModels;
import com.johan.create.content.kinetics.base.SingleRotatingInstance;
import com.johan.create.content.kinetics.base.flwdata.RotatingData;

public class MillstoneCogInstance extends SingleRotatingInstance<MillstoneBlockEntity> {

    public MillstoneCogInstance(MaterialManager materialManager, MillstoneBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(AllPartialModels.MILLSTONE_COG, blockEntity.getBlockState());
    }
}
