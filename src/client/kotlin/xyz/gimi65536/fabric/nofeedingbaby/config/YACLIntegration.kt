package xyz.gimi65536.fabric.nofeedingbaby.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.gui.controllers.BooleanController
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import xyz.gimi65536.fabric.nofeedingbaby.NoFeedingBabyMod
import xyz.gimi65536.fabric.nofeedingbaby.config.DEFAULT_WHITELISTMODE

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
						DEFAULT_WHITELISTMODE,
						{NoFeedingBabyMod.config.whitelistMode},
						{value -> NoFeedingBabyMod.config.whitelistMode = value}
					)
					.customController({opt -> BooleanController(opt, BooleanController.YES_NO_FORMATTER, true)})
					.build()
				)
				.build()
			)
			.save(NoFeedingBabyMod.config::save)
			.build()
			.generateScreen(parent)
	}
}