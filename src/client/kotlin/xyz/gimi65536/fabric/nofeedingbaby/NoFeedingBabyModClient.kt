package xyz.gimi65536.fabric.nofeedingbaby

import net.fabricmc.api.ClientModInitializer
import net.minecraft.util.ActionResult;
import net.minecraft.entity.passive.AnimalEntity
import net.fabricmc.fabric.api.event.player.UseEntityCallback

object NoFeedingBabyModClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		@Suppress("UNUSED_ANONYMOUS_PARAMETER")
		UseEntityCallback.EVENT.register(
			fun(player, world, hand, entity, hitResult): ActionResult {
				return NoFeedingBabyMod.checkAction(player, hand, entity)
			}
		)
	}
}