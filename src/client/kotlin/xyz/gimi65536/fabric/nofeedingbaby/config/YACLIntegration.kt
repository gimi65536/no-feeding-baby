package xyz.gimi65536.fabric.nofeedingbaby.config

import dev.isxander.yacl3.api.YetAnotherConfigLib
import net.minecraft.client.gui.screen.Screen
import com.terraformersmc.modmenu.api.ConfigScreenFactory

object YACLIntegration{
	fun getModConfigScreen(parent: Screen): Screen {
		return YetAnotherConfigLib.createBuilder()
			.build()
			.generateScreen(parent)
	}
}