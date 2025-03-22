package com.johan.create.content.kinetics.base;

import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.johan.create.content.kinetics.base.flwdata.RotatingData;
import com.johan.create.foundation.render.AllMaterialSpecs;

public class CutoutRotatingInstance<T extends KineticBlockEntity> extends SingleRotatingInstance<T> {
	public CutoutRotatingInstance(MaterialManager materialManager, T blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	protected Material<RotatingData> getRotatingMaterial() {
		return materialManager.defaultCutout()
				.material(AllMaterialSpecs.ROTATING);
	}
}
