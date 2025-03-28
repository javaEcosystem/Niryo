package com.johan.create.foundation.render;

import com.jozufozu.flywheel.api.struct.StructType;
import com.johan.create.Create;
import com.johan.create.content.contraptions.actors.flwdata.ActorData;
import com.johan.create.content.contraptions.actors.flwdata.ActorType;
import com.johan.create.content.kinetics.base.flwdata.BeltData;
import com.johan.create.content.kinetics.base.flwdata.BeltType;
import com.johan.create.content.kinetics.base.flwdata.RotatingData;
import com.johan.create.content.kinetics.base.flwdata.RotatingType;
import com.johan.create.content.logistics.flwdata.FlapData;
import com.johan.create.content.logistics.flwdata.FlapType;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AllMaterialSpecs {

	public static final StructType<RotatingData> ROTATING = new RotatingType();
	public static final StructType<BeltData> BELTS = new BeltType();
	public static final StructType<ActorData> ACTORS = new ActorType();
	public static final StructType<FlapData> FLAPS = new FlapType();

	public static class Locations {
		public static final ResourceLocation ROTATING = Create.asResource("rotating");
		public static final ResourceLocation BELTS = Create.asResource("belts");
		public static final ResourceLocation ACTORS = Create.asResource("actors");
		public static final ResourceLocation FLAPS = Create.asResource("flaps");
	}
}
