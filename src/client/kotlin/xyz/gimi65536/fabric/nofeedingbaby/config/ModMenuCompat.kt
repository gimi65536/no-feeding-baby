package xyz.gimi65536.fabric.nofeedingbaby.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.fabricmc.loader.api.FabricLoader

object ModMenuCompat: ModMenuApi{
	override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
		return ConfigScreenFactory({parent ->
			// Add supported mods here
			if(FabricLoader.getInstance().isModLoaded("yet-another-config-lib"))
				YACLIntegration.getModConfigScreen(parent)
			else
				null
		})
	}
}