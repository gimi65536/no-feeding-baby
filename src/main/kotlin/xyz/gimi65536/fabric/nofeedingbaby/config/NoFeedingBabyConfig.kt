package xyz.gimi65536.fabric.nofeedingbaby.config

import com.electronwill.nightconfig.core.file.FileConfig
import java.io.File
import kotlin.io.path.exists
import org.slf4j.LoggerFactory
import net.fabricmc.loader.api.FabricLoader

object NoFeedingBabyConfig{
    private val logger = LoggerFactory.getLogger("no-feeding-baby")
	val filename = "no-feeding-baby.yml"
	val DEFAULT_WHITELISTMODE: Boolean = true

	var whitelistMode: Boolean = DEFAULT_WHITELISTMODE
		private set
	var list = LinkedHashSet<String>()
		private set

	/* True if the animal is added into the list, false if dropped from the list */
	fun toggleAnimal(identifier: String): Boolean{
		if(list.contains(identifier)){
			list.remove(identifier)
			return false
		}else{
			list.add(identifier)
			return true
		}
	}

	/* True if the list is modified, false if not modified */
	fun addAnimal(identifier: String): Boolean{
		val result = !list.contains(identifier)
		list.add(identifier)
		return result
	}

	/* True if the list is modified, false if not modified */
	fun removeAnimal(identifier: String): Boolean{
		val result = list.contains(identifier)
		list.remove(identifier)
		return result
	}

	fun toggleMode(){
		whitelistMode = !whitelistMode
	}

	fun setListMode(isWhitelist: Boolean){
		whitelistMode = isWhitelist
	}

	fun setWhitelistMode(){
		whitelistMode = true
	}

	fun setBlacklistMode(){
		whitelistMode = false
	}

	fun setList(l: List<String>){
		list = LinkedHashSet(l)
	}

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
			logger.info("Failed to load configuration.")
		}else{
			list = LinkedHashSet(loadedlist)
		}
		fileconfig.close()
	}
}
