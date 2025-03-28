package com.johan.create;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.johan.create.compat.jei.ConversionRecipe;
import com.johan.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.johan.create.content.equipment.toolbox.ToolboxDyeingRecipe;
import com.johan.create.content.fluids.transfer.EmptyingRecipe;
import com.johan.create.content.fluids.transfer.FillingRecipe;
import com.johan.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import com.johan.create.content.kinetics.crusher.CrushingRecipe;
import com.johan.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.johan.create.content.kinetics.deployer.ManualApplicationRecipe;
import com.johan.create.content.kinetics.fan.processing.HauntingRecipe;
import com.johan.create.content.kinetics.fan.processing.SplashingRecipe;
import com.johan.create.content.kinetics.millstone.MillingRecipe;
import com.johan.create.content.kinetics.mixer.CompactingRecipe;
import com.johan.create.content.kinetics.mixer.MixingRecipe;
import com.johan.create.content.kinetics.press.PressingRecipe;
import com.johan.create.content.kinetics.saw.CuttingRecipe;
import com.johan.create.content.processing.basin.BasinRecipe;
import com.johan.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeFactory;
import com.johan.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.johan.create.content.processing.sequenced.SequencedAssemblyRecipeSerializer;
import com.johan.create.foundation.recipe.IRecipeTypeInfo;
import com.johan.create.foundation.utility.Lang;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public enum AllRecipeTypes implements IRecipeTypeInfo {

	CONVERSION(ConversionRecipe::new),
	CRUSHING(CrushingRecipe::new),
	CUTTING(CuttingRecipe::new),
	MILLING(MillingRecipe::new),
	BASIN(BasinRecipe::new),
	MIXING(MixingRecipe::new),
	COMPACTING(CompactingRecipe::new),
	PRESSING(PressingRecipe::new),
	SANDPAPER_POLISHING(SandPaperPolishingRecipe::new),
	SPLASHING(SplashingRecipe::new),
	HAUNTING(HauntingRecipe::new),
	DEPLOYING(DeployerApplicationRecipe::new),
	FILLING(FillingRecipe::new),
	EMPTYING(EmptyingRecipe::new),
	ITEM_APPLICATION(ManualApplicationRecipe::new),

	MECHANICAL_CRAFTING(MechanicalCraftingRecipe.Serializer::new),
	SEQUENCED_ASSEMBLY(SequencedAssemblyRecipeSerializer::new),

	TOOLBOX_DYEING(() -> new SimpleRecipeSerializer<>(ToolboxDyeingRecipe::new), () -> RecipeType.CRAFTING, false);

	public static final Predicate<? super Recipe<?>> CAN_BE_AUTOMATED = r -> !r.getId()
		.getPath()
		.endsWith("_manual_only");

	private final ResourceLocation id;
	private final RegistryObject<RecipeSerializer<?>> serializerObject;
	@Nullable
	private final RegistryObject<RecipeType<?>> typeObject;
	private final Supplier<RecipeType<?>> type;

	AllRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
		String name = Lang.asId(name());
		id = Create.asResource(name);
		serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
		if (registerType) {
			typeObject = Registers.TYPE_REGISTER.register(name, typeSupplier);
			type = typeObject;
		} else {
			typeObject = null;
			type = typeSupplier;
		}
	}

	AllRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
		String name = Lang.asId(name());
		id = Create.asResource(name);
		serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
		typeObject = Registers.TYPE_REGISTER.register(name, () -> simpleType(id));
		type = typeObject;
	}

	AllRecipeTypes(ProcessingRecipeFactory<?> processingFactory) {
		this(() -> new ProcessingRecipeSerializer<>(processingFactory));
	}

	public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
		String stringId = id.toString();
		return new RecipeType<T>() {
			@Override
			public String toString() {
				return stringId;
			}
		};
	}

	public static void register(IEventBus modEventBus) {
		ShapedRecipe.setCraftingSize(9, 9);
		Registers.SERIALIZER_REGISTER.register(modEventBus);
		Registers.TYPE_REGISTER.register(modEventBus);
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeSerializer<?>> T getSerializer() {
		return (T) serializerObject.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeType<?>> T getType() {
		return (T) type.get();
	}

	public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
		return world.getRecipeManager()
			.getRecipeFor(getType(), inv, world);
	}

	public static boolean shouldIgnoreInAutomation(Recipe<?> recipe) {
		RecipeSerializer<?> serializer = recipe.getSerializer();
		if (serializer != null && AllTags.AllRecipeSerializerTags.AUTOMATION_IGNORE.matches(serializer))
			return true;
		return !CAN_BE_AUTOMATED.test(recipe);
	}

	private static class Registers {
		private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Create.ID);
		private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Create.ID);
	}

}
