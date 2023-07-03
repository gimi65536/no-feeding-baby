package xyz.gimi65536.fabric.nofeedingbaby.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.ListOption
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.gui.controllers.BooleanController
import dev.isxander.yacl3.gui.controllers.string.StringController
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import xyz.gimi65536.fabric.nofeedingbaby.NoFeedingBabyMod
import xyz.gimi65536.fabric.nofeedingbaby.config.NoFeedingBabyConfig

object YACLIntegration{
	fun getModConfigScreen(parent: Screen): Screen {
		return YetAnotherConfigLib.createBuilder()
			.title(Text.literal("No Feeding Baby Configuration"))
			.category(ConfigCategory.createBuilder()
				.name(Text.literal("General"))
				.option(Option.createBuilder<Boolean>()
					.name(Text.literal("Mode"))
					.description({whitelistMode -> when(whitelistMode){
						true -> OptionDescription.of(
							Text.literal("Whitelist mode\nONLY baby animals listed CAN be fed"))
						false -> OptionDescription.of(
							Text.literal("Blacklist mode\nONLY baby animals listed CANNOT be fed"))
					}})
					.binding(
						NoFeedingBabyConfig.DEFAULT_WHITELISTMODE,
						{NoFeedingBabyConfig.whitelistMode},
						{value -> NoFeedingBabyConfig.whitelistMode = value}
					)
					.customController({opt -> BooleanController(opt, BooleanController.YES_NO_FORMATTER, true)})
					.build()
				)
				.group(ListOption.createBuilder<String>()
					.name(Text.literal("List"))
					.collapsed(false)
					.binding(
						listOf(),
						{NoFeedingBabyConfig.list.toList()},
						{newList -> NoFeedingBabyConfig.list = LinkedHashSet(newList)}
					)
					.customController({opt -> StringController(opt)})
					.initial("")
					.build()
				)
				.build()
			)
			.save(NoFeedingBabyConfig::save)
			.build()
			.generateScreen(parent)
	}
}