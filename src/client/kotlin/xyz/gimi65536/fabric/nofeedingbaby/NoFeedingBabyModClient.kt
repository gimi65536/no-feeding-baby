package xyz.gimi65536.fabric.nofeedingbaby

import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.util.ActionResult
import net.minecraft.text.Text
import net.minecraft.text.Style
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import xyz.gimi65536.fabric.nofeedingbaby.config.NoFeedingBabyConfig

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
	private val toggleModeKey = KeyBinding(
		"no-feeding-baby.key.toggleMode",
		InputUtil.UNKNOWN_KEY.code,
		"no-feeding-baby.key.category"
	)

	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		KeyBindingHelper.registerKeyBinding(toggleModifier)
		KeyBindingHelper.registerKeyBinding(bypassModifier)
		KeyBindingHelper.registerKeyBinding(toggleModeKey)

		ClientTickEvents.END_CLIENT_TICK.register(
			@Suppress("UNUSED_ANONYMOUS_PARAMETER")
			fun(client){
				if(toggleModeKey.wasPressed()){
					NoFeedingBabyConfig.toggleMode()
					MinecraftClient.getInstance().inGameHud.setOverlayMessage(
						Text.translatable("no-feeding-baby.togglemode-1")
						.append(
							if(NoFeedingBabyConfig.whitelistMode){
								Text.translatable("no-feeding-baby.whitelist")
								.setStyle(NoFeedingBabyMod.WHITELIST_STYLE)
							}else{
								Text.translatable("no-feeding-baby.blacklist")
								.setStyle(NoFeedingBabyMod.BLACKLIST_STYLE)
							}
						)
						.append(Text.translatable("no-feeding-baby.togglemode-2"))
					, false)
					NoFeedingBabyConfig.trySave()
				}
			}
		)

		@Suppress("UNUSED_ANONYMOUS_PARAMETER")
		UseEntityCallback.EVENT.register(
			fun(player, world, hand, entity, hitResult): ActionResult {
				if(NoFeedingBabyMod.checkValid(player, hand, entity)){
					if(toggleModifier.isPressed()){
						// Do toggling
						val identifier = EntityType.getId(entity.getType())
						val toggleResult = NoFeedingBabyConfig.toggleAnimal(identifier.toString())
						val entityTranskey = "entity.%s.%s".format(identifier.namespace, identifier.path)
						val resultKey = if (toggleResult) "added" else "removed"
						MinecraftClient.getInstance().inGameHud.setOverlayMessage(
							Text.translatable("no-feeding-baby.%s-1".format(resultKey))
							.append(Text.translatable(entityTranskey).setStyle(Style.EMPTY.withBold(true)))
							.append(Text.translatable("no-feeding-baby.%s-2".format(resultKey)))
							.append(
								if(NoFeedingBabyConfig.whitelistMode){
									Text.translatable("no-feeding-baby.whitelist")
									.setStyle(NoFeedingBabyMod.WHITELIST_STYLE)
								}else{
									Text.translatable("no-feeding-baby.blacklist")
									.setStyle(NoFeedingBabyMod.BLACKLIST_STYLE)
								}
							)
							.append(Text.translatable("no-feeding-baby.%s-3".format(resultKey)))
						, false)
						NoFeedingBabyConfig.trySave()
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