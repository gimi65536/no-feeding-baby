package xyz.gimi65536.fabric.nofeedingbaby.config

import com.electronwill.nightconfig.core.file.FileConfig
import kotlin.collections.LinkedHashSet
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen

class NoFeedingBabyConfig{
	protected var whitelistMode: Boolean = true
	protected var list = LinkedHashSet<String>()

	fun save(){
		//
	}

	fun load(){
		//
	}

	@Suppress("UNUSED_PARAMETER")
	fun gui(parent: Screen? = null): Screen? {
		return null
	}
}
