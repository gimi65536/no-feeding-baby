package xyz.gimi65536.fabric.nofeedingbaby

import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.util.ActionResult
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper

object NoFeedingBabyModClient : ClientModInitializer {
	private val toggleModifier = KeyBinding(
		"no-feeding-baby.key.toggle",
		InputUtil.GLFW_KEY_LEFT_ALT,
		"no-feeding-baby.key.category"
	)
	private val bypassModifier = KeyBinding(
		"no-feeding-baby.key.bypass",
		InputUtil.UNKNOWN_KEY.code,
		"no-feeding-baby.key.category"
	)

	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		KeyBindingHelper.registerKeyBinding(toggleModifier)
		KeyBindingHelper.registerKeyBinding(bypassModifier)

		@Suppress("UNUSED_ANONYMOUS_PARAMETER")
		UseEntityCallback.EVENT.register(
			fun(player, world, hand, entity, hitResult): ActionResult {
				if(NoFeedingBabyMod.checkValid(player, hand, entity)){
					if(toggleModifier.isPressed()){
						// Do toggling
						// Do not send feeding packet
						return ActionResult.FAIL
					}
					if(bypassModifier.isPressed()){
						return ActionResult.PASS
					}
					return NoFeedingBabyMod.getAction(entity as AnimalEntity)
				}
				return ActionResult.PASS
			}
		)
	}
}