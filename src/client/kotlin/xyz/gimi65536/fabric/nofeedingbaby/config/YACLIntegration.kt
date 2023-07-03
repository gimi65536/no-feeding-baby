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
import net.minecraft.text.MutableText
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.text.TextContent
import xyz.gimi65536.fabric.nofeedingbaby.NoFeedingBabyMod
import xyz.gimi65536.fabric.nofeedingbaby.config.NoFeedingBabyConfig

object YACLIntegration{
	fun MODE_LIST_FORMATTER(whitelistMode: Boolean): Text {
		return when(whitelistMode){
			true -> Text.literal("Whitelist")
			false -> Text.literal("Blacklist")
		}
	}
	private val WHITELIST_TEXT: Text = MutableText.of(TextContent.EMPTY)
		.append("Whitelist")
		.setStyle(Style.EMPTY
			.withColor(0xffffff)
			.withBold(true)
			.withUnderline(true)
		)
	private val BLACKLIST_TEXT: Text = MutableText.of(TextContent.EMPTY)
		.append("Blacklist")
		.setStyle(Style.EMPTY
			.withColor(0x222222)
			.withBold(true)
			.withUnderline(true)
		)
	fun MODE_DESCRIPTION(whitelistMode: Boolean): OptionDescription {
		return when (whitelistMode) {
			true -> OptionDescription.createBuilder()
				.text(MutableText.of(TextContent.EMPTY).append(WHITELIST_TEXT).append(" mode"))
				.text(Text.literal("ONLY baby animals listed CAN be fed"))
				.build()
			false -> OptionDescription.createBuilder()
				.text(MutableText.of(TextContent.EMPTY).append(BLACKLIST_TEXT).append(" mode"))
				.text(Text.literal("ONLY baby animals listed CANNOT be fed"))
				.build()
		}
	}

	fun getModConfigScreen(parent: Screen): Screen {
		return YetAnotherConfigLib.createBuilder()
			.title(Text.literal("No Feeding Baby Configuration"))
			.category(ConfigCategory.createBuilder()
				.name(Text.literal("General"))
				.option(Option.createBuilder<Boolean>()
					.name(Text.literal("Mode"))
					.description(::MODE_DESCRIPTION)
					.binding(
						NoFeedingBabyConfig.DEFAULT_WHITELISTMODE,
						{NoFeedingBabyConfig.whitelistMode},
						{value -> NoFeedingBabyConfig.whitelistMode = value}
					)
					.customController({opt -> BooleanController(opt, ::MODE_LIST_FORMATTER, false)})
					.build()
				)
				.group(ListOption.createBuilder<String>()
					.name(Text.literal("Animal")) // Since the ListOptionEntry shows the group name redundantly
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