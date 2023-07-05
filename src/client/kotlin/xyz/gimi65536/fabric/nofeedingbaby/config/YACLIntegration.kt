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
import net.minecraft.text.Style
import net.minecraft.text.Text
import xyz.gimi65536.fabric.nofeedingbaby.NoFeedingBabyMod
import xyz.gimi65536.fabric.nofeedingbaby.config.NoFeedingBabyConfig

object YACLIntegration{
	fun MODE_LIST_FORMATTER(whitelistMode: Boolean): Text {
		return when(whitelistMode){
			true -> Text.translatable("no-feeding-baby.whitelist")
			false -> Text.translatable("no-feeding-baby.blacklist")
		}
	}
	fun MODE_DESCRIPTION(whitelistMode: Boolean): OptionDescription {
		return when (whitelistMode) {
			true -> OptionDescription.createBuilder()
				.text(Text.translatable("no-feeding-baby.whitelistmode").setStyle(NoFeedingBabyMod.WHITELIST_STYLE))
				.text(Text.translatable("no-feeding-baby.whitelist-tooltip"))
				.build()
			false -> OptionDescription.createBuilder()
				.text(Text.translatable("no-feeding-baby.blacklistmode").setStyle(NoFeedingBabyMod.BLACKLIST_STYLE))
				.text(Text.translatable("no-feeding-baby.blacklist-tooltip"))
				.build()
		}
	}

	fun getModConfigScreen(parent: Screen): Screen {
		return YetAnotherConfigLib.createBuilder()
			.title(Text.translatable("no-feeding-baby.configtitle"))
			.category(ConfigCategory.createBuilder()
				.name(Text.translatable("no-feeding-baby.general"))
				.option(Option.createBuilder<Boolean>()
					.name(Text.translatable("no-feeding-baby.mode"))
					.description(::MODE_DESCRIPTION)
					.binding(
						NoFeedingBabyConfig.DEFAULT_WHITELISTMODE,
						{NoFeedingBabyConfig.whitelistMode},
						{value -> NoFeedingBabyConfig.setListMode(value)}
					)
					.customController({opt -> BooleanController(opt, ::MODE_LIST_FORMATTER, false)})
					.build()
				)
				.group(ListOption.createBuilder<String>()
					.name(Text.translatable("no-feeding-baby.animal")) // Since the ListOptionEntry shows the group name redundantly
					.collapsed(false)
					.binding(
						listOf(),
						{NoFeedingBabyConfig.list.toList()},
						{newList -> NoFeedingBabyConfig.setList(newList)}
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