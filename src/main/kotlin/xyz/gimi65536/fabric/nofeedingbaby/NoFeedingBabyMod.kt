package xyz.gimi65536.fabric.nofeedingbaby

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.Entity
import net.fabricmc.fabric.api.event.player.UseEntityCallback

object NoFeedingBabyMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("no-feeding-baby")

	// Put config here...

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		@Suppress("UNUSED_ANONYMOUS_PARAMETER")
		UseEntityCallback.EVENT.register(fun(player, world, hand, entity, hitResult): ActionResult {
			// Here we handle logical server
			if(world.isClient()){
				// In logical client, see NoFeedingBabyModClient
				return ActionResult.PASS
			}

			return checkAction(player, hand, entity)
		})
		logger.info("Baby animals cannot eat food now.")
	}

	fun checkAction(player: PlayerEntity, hand: Hand, entity: Entity): ActionResult{
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
	}
}