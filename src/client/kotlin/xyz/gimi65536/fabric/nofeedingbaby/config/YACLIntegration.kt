package xyz.gimi65536.fabric.nofeedingbaby.config

import dev.isxander.yacl3.api.YetAnotherConfigLib
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import com.terraformersmc.modmenu.api.ConfigScreenFactory

object YACLIntegration{
	fun getModConfigScreen(parent: Screen): Screen {
		return YetAnotherConfigLib.createBuilder()
			.title(Text.literal("No Feeding Baby Configuration"))
			.build()
			.generateScreen(parent)
	}
}