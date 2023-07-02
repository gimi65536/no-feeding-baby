package xyz.gimi65536.fabric.nofeedingbaby

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import net.minecraft.util.ActionResult;
import net.minecraft.entity.passive.AnimalEntity
import net.fabricmc.fabric.api.event.player.UseEntityCallback

object NoFeedingBabyMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("no-feeding-baby")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		@Suppress("UNUSED_ANONYMOUS_PARAMETER")
		UseEntityCallback.EVENT.register(fun(player, world, hand, entity, hitResult): ActionResult {
			if(player.isSpectator()){
				return ActionResult.PASS
			}
			if(entity is AnimalEntity){
				val animal: AnimalEntity = entity
				if(animal.isBaby && animal.isBreedingItem(player.getStackInHand(hand))){
					return ActionResult.FAIL
				}
			}
			return ActionResult.PASS
		})
		logger.info("Baby animals cannot eat food now.")
	}
}