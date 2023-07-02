package xyz.gimi65536.fabric.nofeedingbaby.config

import com.electronwill.nightconfig.core.Config
import com.electronwill.nightconfig.core.file.FileConfig
import java.io.File
import kotlin.collections.LinkedHashSet
import kotlin.io.path.exists
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen

class NoFeedingBabyYACLConfig: NoFeedingBabyConfig() {
	override fun gui(parent: Screen?): Screen? {
		return null
	}
}
