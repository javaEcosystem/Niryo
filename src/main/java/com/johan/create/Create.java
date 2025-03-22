package com.johan.create;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.PlayerCloudParticle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.johan.create.api.behaviour.BlockSpoutingBehaviour;
import com.johan.create.compat.Mods;
import com.johan.create.compat.computercraft.ComputerCraftProxy;
import com.johan.create.compat.curios.Curios;
import com.johan.create.content.contraptions.ContraptionMovementSetting;
import com.johan.create.content.decoration.palettes.AllPaletteBlocks;
import com.johan.create.content.decoration.slidingDoor.SlidingDoorBlock;
import com.johan.create.content.equipment.potatoCannon.BuiltinPotatoProjectileTypes;
import com.johan.create.content.fluids.tank.BoilerHeaters;
import com.johan.create.content.kinetics.TorquePropagator;
import com.johan.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.johan.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.johan.create.content.redstone.displayLink.AllDisplayBehaviours;
import com.johan.create.content.redstone.link.RedstoneLinkNetworkHandler;
import com.johan.create.content.schematics.ServerSchematicLoader;
import com.johan.create.content.trains.GlobalRailwayManager;
import com.johan.create.content.trains.bogey.BogeySizes;
import com.johan.create.foundation.advancement.AllAdvancements;
import com.johan.create.foundation.advancement.AllTriggers;
import com.johan.create.foundation.block.CopperRegistries;
import com.johan.create.foundation.data.CreateRegistrate;
import com.johan.create.foundation.item.ItemDescription;
import com.johan.create.foundation.item.KineticStats;
import com.johan.create.foundation.item.TooltipHelper.Palette;
import com.johan.create.foundation.item.TooltipModifier;
import com.johan.create.foundation.utility.AttachedRegistry;
import com.johan.create.infrastructure.command.ServerLagger;
import com.johan.create.infrastructure.config.AllConfigs;
import com.johan.create.infrastructure.data.CreateDatagen;
import com.johan.create.infrastructure.worldgen.AllFeatures;
import com.johan.create.infrastructure.worldgen.AllOreFeatureConfigEntries;
import com.johan.create.infrastructure.worldgen.AllPlacementModifiers;
import com.johan.create.infrastructure.worldgen.BuiltinRegistration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Create.ID)
public class Create {

	//Allez directement à la ligne 197

	public static final String ID = "create";
	public static final String NAME = "Create";
	public static final String VERSION = "0.5.1i";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	/** Use the {@link Random} of a local {@link Level} or {@link Entity} or create one */
	@Deprecated
	public static final Random RANDOM = new Random();

	/**
	 * <b>Other mods should not use this field!</b> If you are an addon developer, create your own instance of
	 * {@link CreateRegistrate}.
	 */
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	static {
		REGISTRATE.setTooltipModifierFactory(item -> {
			return new ItemDescription.Modifier(item, Palette.STANDARD_CREATE)
				.andThen(TooltipModifier.mapNull(KineticStats.create(item)));
		});
	}

	public static final ServerSchematicLoader SCHEMATIC_RECEIVER = new ServerSchematicLoader();
	public static final RedstoneLinkNetworkHandler REDSTONE_LINK_NETWORK_HANDLER = new RedstoneLinkNetworkHandler();
	public static final TorquePropagator TORQUE_PROPAGATOR = new TorquePropagator();
	public static final GlobalRailwayManager RAILWAYS = new GlobalRailwayManager();
	public static final ServerLagger LAGGER = new ServerLagger();

	public Create() {
		MinecraftForge.EVENT_BUS.register(this);
		onCtor();
	}

	public static void onCtor() {
		ModLoadingContext modLoadingContext = ModLoadingContext.get();

		IEventBus modEventBus = FMLJavaModLoadingContext.get()
			.getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

		REGISTRATE.registerEventListeners(modEventBus);

		AllSoundEvents.prepare();
		AllTags.init();
		AllCreativeModeTabs.init();
		AllBlocks.register();
		AllItems.register();
		AllFluids.register();
		AllPaletteBlocks.register();
		AllMenuTypes.register();
		AllEntityTypes.register();
		AllBlockEntityTypes.register();
		AllEnchantments.register();
		AllRecipeTypes.register(modEventBus);
		AllParticleTypes.register(modEventBus);
		AllStructureProcessorTypes.register(modEventBus);
		AllEntityDataSerializers.register(modEventBus);
		AllPackets.registerPackets();
		AllOreFeatureConfigEntries.init();
		AllFeatures.register(modEventBus);
		AllPlacementModifiers.register(modEventBus);
		BuiltinRegistration.register(modEventBus);

		AllConfigs.register(modLoadingContext);

		// FIXME: some of these registrations are not thread-safe
		AllMovementBehaviours.registerDefaults();
		AllInteractionBehaviours.registerDefaults();
		AllDisplayBehaviours.registerDefaults();
		ContraptionMovementSetting.registerDefaults();
		AllArmInteractionPointTypes.register();
		AllFanProcessingTypes.register();
		BlockSpoutingBehaviour.registerDefaults();
		BogeySizes.init();
		AllBogeyStyles.register();
		// ----

		ComputerCraftProxy.register();

		ForgeMod.enableMilkFluid();
		CopperRegistries.inject();

		modEventBus.addListener(Create::init);
		modEventBus.addListener(EventPriority.LOWEST, CreateDatagen::gatherData);
		modEventBus.addGenericListener(SoundEvent.class, AllSoundEvents::register);

		forgeEventBus.addListener(EventPriority.HIGH, SlidingDoorBlock::stopItQuark);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreateClient.onCtorClient(modEventBus, forgeEventBus));

		// FIXME: this is not thread-safe
		Mods.CURIOS.executeIfInstalled(() -> () -> Curios.init(modEventBus, forgeEventBus));
	}

	public static void init(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			// TODO: custom registration should all happen in one place
			// Most registration happens in the constructor.
			// These registrations use Create's registered objects directly so they must run after registration has finished.
			BuiltinPotatoProjectileTypes.register();
			BoilerHeaters.registerDefaults();
			// --

			AttachedRegistry.unwrapAll();
			AllAdvancements.register();
			AllTriggers.register();
		});
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}

	//J'ai seulement ajouté ce qui se trouve en dessous de cette ligne
	//le fichier python (mc.py) est dans le dossier principal du package

	private Player player;
	private Level world;
	private String commandString;
	private final ArrayList<ItemStack> items = new ArrayList<>(); //liste pour stocker les items
	private final String[] names = {"up_left", "up_right", "down_left", "down_right"}; //liste pour les noms des items

	@SubscribeEvent
	public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event){ //cet évènement se déclenche quand le joueur rejoint le monde
		player = event.getPlayer();
		Container inventory = player.getInventory();
		int c;
		for(c=0; c<4; c++){ //on créé 4 items
			ItemStack item = new ItemStack(Items.COMPASS);
			items.add(item); //on ajoute le nouvel item à la liste
		}
		c = 0;
		for(ItemStack i : items){ //on cycle à travers les items de la liste
			i.setHoverName(Component.nullToEmpty(names[c])); //on alloue un nom à chaque item
			inventory.setItem(c, i); //on ajoute chaque item au slot d'inventaire choisi
			c++;
		}
	}

	@SubscribeEvent
	public void onPlayerInteractEvent(PlayerInteractEvent event) { //cet évènement se déclenche quand on click sur un item dans l'inventaire
		world = event.getWorld();
		ItemStack heldItem = player.getItemInHand(player.getUsedItemHand());
		ItemStack itemStack = new ItemStack(Items.DIAMOND, 5);
		try {
			if (heldItem.getDisplayName().getString().equals("[up_left]")) { //on vérifie que l'item est l'un des 4 créés
				ItemEntity itemEntity = new ItemEntity(world, 280, 67, 129, itemStack);
				world.addFreshEntity(itemEntity);

				//Il vous faut mettre le chemin où votre interpréteur python est localisé
				commandString = "cd D:\\IUT\\robotique && echo -2.241005562348989, -0.5807473915146274, -0.6264605325656624, 0.07525771219618926, -0.31455871510634914, 0.05531596195367605 > data.txt && python mc.py";
				/* la commande fait les 3 actions suivantes:
				1- se placer dans le bon répertoire
				2- écrire les coordonnées sur la première ligne du fichier texte généré
				3- lancer l'interpréteur python, qui s'occupe du déplacement du robot, avec la librairie pyniryo */

				//on répète ensuite pour les autres positions
			} else if (heldItem.getDisplayName().getString().equals("[up_right]")) {
				ItemEntity itemEntity = new ItemEntity(world, 280, 67, 125, itemStack);
				world.addFreshEntity(itemEntity);
				commandString = "cd D:\\IUT\\robotique && echo 2.1832365398484446, -0.5216645056761152, -0.7522010331963418, -0.18551902174436963, -0.311490753530578, 0.05378198116579025 > data.txt && python mc.py";
			} else if (heldItem.getDisplayName().getString().equals("[down_left]")) {
				ItemEntity itemEntity = new ItemEntity(world, 284, 67, 129, itemStack);
				world.addFreshEntity(itemEntity);
				commandString = "cd D:\\IUT\\robotique && echo -0.8195256437207115, -0.6792188679121475, -0.4083083387003871, -0.0014413271980928677, -0.46182087074337064, -0.03212094295580581 > data.txt && python mc.py";
			} else if (heldItem.getDisplayName().getString().equals("[down_right]")) {
				ItemEntity itemEntity = new ItemEntity(world, 284, 67, 125, itemStack);
				world.addFreshEntity(itemEntity);
				commandString = "cd D:\\IUT\\robotique && echo 0.808936361560078, -0.5989267410034002, -0.5961616167510408, 0.059917904317333015, -0.3206946382578919, 0.049180038802133286 > data.txt && python mc.py";
			}
			ProcessBuilder commandBuilder = new ProcessBuilder("cmd.exe", "/c", commandString);
			commandBuilder.redirectErrorStream(true);
			commandBuilder.start(); //on exécute la commande
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
