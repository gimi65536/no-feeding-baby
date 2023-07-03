package xyz.gimi65536.fabric.nofeedingbaby

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import xyz.gimi65536.fabric.nofeedingbaby.config.NoFeedingBabyConfig

object NoFeedingBabyMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("no-feeding-baby")

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
			NoFeedingBabyConfig.load()
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
				// Check identifier
				val identifier = EntityType.getId(animal.getType()).toString()
				// If not contained in the whilelist
				// or contained in the blacklist
				return if (NoFeedingBabyConfig.whitelistMode xor NoFeedingBabyConfig.list.contains(identifier)) ActionResult.FAIL
					else ActionResult.PASS
			}
		}
		return ActionResult.PASS
	}
}