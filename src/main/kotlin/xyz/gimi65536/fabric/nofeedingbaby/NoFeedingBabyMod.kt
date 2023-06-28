package xyz.gimi65536.fabric.nofeedingbaby

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import net.minecraft.util.ActionResult;
import net.minecraft.entity.passive.CowEntity
import net.minecraft.item.Items
import net.fabricmc.fabric.api.event.player.UseEntityCallback

object NoFeedingBabyMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("no-feeding-baby")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		UseEntityCallback.EVENT.register(fun(player, world, hand, entity, hitResult): ActionResult {
			if(player.isSpectator()){
				return ActionResult.PASS
			}
			if(entity is CowEntity && entity.isBaby() && player.getStackInHand(hand).item.equals(Items.WHEAT)){
				return ActionResult.FAIL
			}
			return ActionResult.PASS
		})
		logger.info("Cow babies cannot eat wheat now.")
	}
}