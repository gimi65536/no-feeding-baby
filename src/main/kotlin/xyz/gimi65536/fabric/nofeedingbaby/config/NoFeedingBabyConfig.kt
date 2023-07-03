package xyz.gimi65536.fabric.nofeedingbaby.config

import com.electronwill.nightconfig.core.Config
import com.electronwill.nightconfig.core.file.FileConfig
import java.io.File
import kotlin.collections.LinkedHashSet
import kotlin.io.path.exists
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen

val filename = "no-feeding-baby.yml"
val DEFAULT_WHITELISTMODE: Boolean = true

fun newConfig(): NoFeedingBabyConfig {
	var config: NoFeedingBabyConfig
	if(FabricLoader.getInstance().isModLoaded("modmenu") && FabricLoader.getInstance().isModLoaded("yet-another-config-lib")){
		config = NoFeedingBabyYACLConfig()
	}else{
		config = NoFeedingBabyConfig()
	}
	config.load()
	return config
}

open class NoFeedingBabyConfig{
	var whitelistMode: Boolean = DEFAULT_WHITELISTMODE
	var list = LinkedHashSet<String>()

	fun save(){
		val fileconfig = FileConfig
			.builder(FabricLoader.getInstance().configDir.resolve(filename))
			.concurrent()
			.build()

		fileconfig.set<Any>("whitelistMode", whitelistMode)
		fileconfig.set<Any>("list", list.toList())
		fileconfig.save()
		fileconfig.close()
	}

	fun load(){
		val path = FabricLoader.getInstance().configDir.resolve(filename)
		if(!path.exists()){
			return save() // Save a default file
		}
		val fileconfig = FileConfig.builder(path).concurrent().build()
		fileconfig.load()

		whitelistMode = fileconfig.getOrElse("whitelistMode", DEFAULT_WHITELISTMODE)
		val loadedlist: List<String>? = fileconfig.getOrElse("list", null)
		if(loadedlist == null){
			// Empty list
			list.clear()
		}else{
			list = LinkedHashSet(loadedlist)
		}
		fileconfig.close()
	}

	@Suppress("UNUSED_PARAMETER")
	open fun gui(parent: Screen? = null): Screen? {
		return null
	}
}
