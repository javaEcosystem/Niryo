package com.johan.create.infrastructure.data;

import java.util.Map.Entry;
import java.util.function.BiConsumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.johan.create.AllSoundEvents;
import com.johan.create.Create;
import com.johan.create.foundation.advancement.AllAdvancements;
import com.johan.create.foundation.data.recipe.MechanicalCraftingRecipeGen;
import com.johan.create.foundation.data.recipe.ProcessingRecipeGen;
import com.johan.create.foundation.data.recipe.SequencedAssemblyRecipeGen;
import com.johan.create.foundation.data.recipe.StandardRecipeGen;
import com.johan.create.foundation.ponder.PonderLocalization;
import com.johan.create.foundation.utility.FilesHelper;
import com.johan.create.infrastructure.ponder.AllPonderTags;
import com.johan.create.infrastructure.ponder.GeneralText;
import com.johan.create.infrastructure.ponder.PonderIndex;
import com.johan.create.infrastructure.ponder.SharedText;
import com.tterrag.registrate.providers.ProviderType;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class CreateDatagen {
	public static void gatherData(GatherDataEvent event) {
		addExtraRegistrateData();

		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeClient()) {
			generator.addProvider(AllSoundEvents.provider(generator));
		}

		if (event.includeServer()) {
			generator.addProvider(new CreateRecipeSerializerTagsProvider(generator, existingFileHelper));

			generator.addProvider(new AllAdvancements(generator));

			generator.addProvider(new StandardRecipeGen(generator));
			generator.addProvider(new MechanicalCraftingRecipeGen(generator));
			generator.addProvider(new SequencedAssemblyRecipeGen(generator));
			ProcessingRecipeGen.registerAll(generator);

//			AllOreFeatureConfigEntries.gatherData(event);
		}
	}

	private static void addExtraRegistrateData() {
		CreateRegistrateTags.addGenerators();

		Create.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
			BiConsumer<String, String> langConsumer = provider::add;

			provideDefaultLang("interface", langConsumer);
			provideDefaultLang("tooltips", langConsumer);
			AllAdvancements.provideLang(langConsumer);
			AllSoundEvents.provideLang(langConsumer);
			providePonderLang(langConsumer);
		});
	}

	private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
		String path = "assets/create/lang/default/" + fileName + ".json";
		JsonElement jsonElement = FilesHelper.loadJsonResource(path);
		if (jsonElement == null) {
			throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
		}
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().getAsString();
			consumer.accept(key, value);
		}
	}

	private static void providePonderLang(BiConsumer<String, String> consumer) {
		// Register these since FMLClientSetupEvent does not run during datagen
		AllPonderTags.register();
		PonderIndex.register();

		SharedText.gatherText();
		PonderLocalization.generateSceneLang();

		GeneralText.provideLang(consumer);
		PonderLocalization.provideLang(Create.ID, consumer);
	}
}
