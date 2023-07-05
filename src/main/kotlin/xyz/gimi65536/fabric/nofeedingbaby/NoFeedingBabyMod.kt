package xyz.gimi65536.fabric.nofeedingbaby

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.server.command.CommandManager.*
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import xyz.gimi65536.fabric.nofeedingbaby.config.NoFeedingBabyConfig

object NoFeedingBabyMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("no-feeding-baby")
	val WHITELIST_STYLE = Style.EMPTY.withColor(0xffffff).withBold(true).withUnderline(true)
	val BLACKLIST_STYLE = Style.EMPTY.withColor(0x666666).withBold(true).withUnderline(true)

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
			if(checkValid(player, hand, entity)){
				return getAction(entity as AnimalEntity)
			}
			return ActionResult.PASS
		})
		// Server-side command for configuration (I think this is much useless, though)
		CommandRegistrationCallback.EVENT.register(
			@Suppress("UNUSED_ANONYMOUS_PARAMETER")
			fun(dispatcher, registryAccess, environment){
				dispatcher.register(literal("nofeedingbaby")
					.requires({source -> source.hasPermissionLevel(2)})
					// In "mode" sub-command, if whitelist mode after command then 1, otherwise 0
					.then(literal("mode")
						.then(literal("whitelist")
							.executes({context ->
								NoFeedingBabyConfig.setWhitelistMode()
								NoFeedingBabyConfig.trySave()
								context.getSource().sendMessage(Text.of("[No Feeding Baby] Whitelist mode"))
								1
							})
						)
						.then(literal("blacklist")
							.executes({context ->
								NoFeedingBabyConfig.setBlacklistMode()
								NoFeedingBabyConfig.trySave()
								context.getSource().sendMessage(Text.of("[No Feeding Baby] Blacklist mode"))
								0
							})
						)
						.then(literal("toggle")
							.executes({context ->
								NoFeedingBabyConfig.toggleMode()
								NoFeedingBabyConfig.trySave()
								if(NoFeedingBabyConfig.whitelistMode){
									context.getSource().sendMessage(Text.of("[No Feeding Baby] Whitelist mode"))
									1
								}else{
									context.getSource().sendMessage(Text.of("[No Feeding Baby] Blacklist mode"))
									0
								}
							})
						)
					)
					.then(literal("animal")
						/*
							In add/remove sub-commands,
							1: animal not in list -> animal in list
							-1: animal in list -> animal not in list
							0: animal in list before iff animal in list after (unmodified)
						*/
						.then(literal("add").then(argument("id", IdentifierArgumentType())
							.executes({context ->
								val identifier = context.getArgument("id", Identifier::class.java)
								val result = NoFeedingBabyConfig.addAnimal(identifier.toString())
								NoFeedingBabyConfig.trySave()
								if(result){
									context.getSource().sendMessage(Text.of("[No Feeding Baby] + %s".format(identifier.toString())))
									1
								}else{
									0
								}
							})
						))
						.then(literal("remove").then(argument("id", IdentifierArgumentType())
							.executes({context ->
								val identifier = context.getArgument("id", Identifier::class.java)
								val result = NoFeedingBabyConfig.removeAnimal(identifier.toString())
								NoFeedingBabyConfig.trySave()
								if(result){
									context.getSource().sendMessage(Text.of("[No Feeding Baby] - %s".format(identifier.toString())))
									-1
								}else{
									0
								}
							})
						))
						/*
							In "toggle" sub-command,
							1: animal not in list -> animal in list
							0: animal in list -> animal not in list
						*/
						.then(literal("toggle").then(argument("id", IdentifierArgumentType())
							.executes({context ->
								val identifier = context.getArgument("id", Identifier::class.java)
								val result = NoFeedingBabyConfig.toggleAnimal(identifier.toString())
								NoFeedingBabyConfig.trySave()
								if(result){
									context.getSource().sendMessage(Text.of("[No Feeding Baby] + %s".format(identifier.toString())))
									1
								}else{
									context.getSource().sendMessage(Text.of("[No Feeding Baby] - %s".format(identifier.toString())))
									0
								}
							})
						))
						/*
							In "status" sub-command,
							1: animal in list
							0: animal not in list
						*/
						.then(literal("status").then(argument("id", IdentifierArgumentType())
							.executes({context ->
								val identifier = context.getArgument("id", Identifier::class.java)
								val result = NoFeedingBabyConfig.list.contains(identifier.toString())
								if(result){
									context.getSource().sendMessage(Text.of("[No Feeding Baby] In list: %s".format(identifier.toString())))
									1
								}else{
									context.getSource().sendMessage(Text.of("[No Feeding Baby] NOT in list: %s".format(identifier.toString())))
									0
								}
							})
						))
					)
				)
			}
		)
		NoFeedingBabyConfig.load()
		logger.info("Baby animals cannot eat food now.")
	}

	/* Check if this is a valid condition to do more */
	fun checkValid(player: PlayerEntity, hand: Hand, entity: Entity): Boolean{
		if(player.isSpectator()){
			return false
		}
		if(entity is AnimalEntity){
			val animal: AnimalEntity = entity
			if(animal.isBaby && animal.isBreedingItem(player.getStackInHand(hand))){
				return true
			}
		}
		return false
	}

	fun getAction(animal: AnimalEntity): ActionResult{
		// Check identifier
		val identifier = EntityType.getId(animal.getType()).toString()
		// If not contained in the whilelist
		// or contained in the blacklist
		return if (NoFeedingBabyConfig.whitelistMode xor NoFeedingBabyConfig.list.contains(identifier))
				ActionResult.FAIL
		else ActionResult.PASS
	}
}