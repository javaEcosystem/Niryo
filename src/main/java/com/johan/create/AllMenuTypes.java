package com.johan.create;

import com.johan.create.content.equipment.blueprint.BlueprintMenu;
import com.johan.create.content.equipment.blueprint.BlueprintScreen;
import com.johan.create.content.equipment.toolbox.ToolboxMenu;
import com.johan.create.content.equipment.toolbox.ToolboxScreen;
import com.johan.create.content.logistics.filter.AttributeFilterMenu;
import com.johan.create.content.logistics.filter.AttributeFilterScreen;
import com.johan.create.content.logistics.filter.FilterMenu;
import com.johan.create.content.logistics.filter.FilterScreen;
import com.johan.create.content.redstone.link.controller.LinkedControllerMenu;
import com.johan.create.content.redstone.link.controller.LinkedControllerScreen;
import com.johan.create.content.schematics.cannon.SchematicannonMenu;
import com.johan.create.content.schematics.cannon.SchematicannonScreen;
import com.johan.create.content.schematics.table.SchematicTableMenu;
import com.johan.create.content.schematics.table.SchematicTableScreen;
import com.johan.create.content.trains.schedule.ScheduleMenu;
import com.johan.create.content.trains.schedule.ScheduleScreen;
import com.tterrag.registrate.builders.MenuBuilder.ForgeMenuFactory;
import com.tterrag.registrate.builders.MenuBuilder.ScreenFactory;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class AllMenuTypes {

	public static final MenuEntry<SchematicTableMenu> SCHEMATIC_TABLE =
		register("schematic_table", SchematicTableMenu::new, () -> SchematicTableScreen::new);

	public static final MenuEntry<SchematicannonMenu> SCHEMATICANNON =
		register("schematicannon", SchematicannonMenu::new, () -> SchematicannonScreen::new);

	public static final MenuEntry<FilterMenu> FILTER =
		register("filter", FilterMenu::new, () -> FilterScreen::new);

	public static final MenuEntry<AttributeFilterMenu> ATTRIBUTE_FILTER =
		register("attribute_filter", AttributeFilterMenu::new, () -> AttributeFilterScreen::new);

	public static final MenuEntry<BlueprintMenu> CRAFTING_BLUEPRINT =
		register("crafting_blueprint", BlueprintMenu::new, () -> BlueprintScreen::new);

	public static final MenuEntry<LinkedControllerMenu> LINKED_CONTROLLER =
		register("linked_controller", LinkedControllerMenu::new, () -> LinkedControllerScreen::new);

	public static final MenuEntry<ToolboxMenu> TOOLBOX =
		register("toolbox", ToolboxMenu::new, () -> ToolboxScreen::new);

	public static final MenuEntry<ScheduleMenu> SCHEDULE =
		register("schedule", ScheduleMenu::new, () -> ScheduleScreen::new);

	private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
		String name, ForgeMenuFactory<C> factory, NonNullSupplier<ScreenFactory<C, S>> screenFactory) {
		return Create.REGISTRATE
			.menu(name, factory, screenFactory)
			.register();
	}

	public static void register() {}

}
