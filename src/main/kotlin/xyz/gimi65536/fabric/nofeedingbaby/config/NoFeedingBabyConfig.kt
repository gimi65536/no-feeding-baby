package xyz.gimi65536.fabric.nofeedingbaby.config

import kotlin.collections.LinkedHashSet
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen

class NoFeedingBabyConfig{
	protected var whitelistMode: Boolean = true
	protected var list = LinkedHashSet<String>()

	/* Translate config into serializable data structure */
	fun serialize(){
		//
	}

	/* Translate serialization date into config */
	fun deserialize(){
		//
	}

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
