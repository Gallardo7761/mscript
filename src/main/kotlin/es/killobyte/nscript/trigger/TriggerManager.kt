package es.killobyte.nscript.trigger

import de.tr7zw.nbtapi.NBT
import es.killobyte.nscript.Script
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PotionSplashEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class TriggerManager : Listener {
    private val triggerScripts: MutableMap<TriggerType, MutableList<Script>> = mutableMapOf()

    companion object {
        private const val SPECIAL_ITEM_TAG = "special_item_tag"
    }

    fun registerScript(triggerType: TriggerType, script: Script) {
        triggerScripts.computeIfAbsent(triggerType) { mutableListOf() }.add(script)
    }

    fun unregisterAll() {
        triggerScripts.clear()
    }

    private fun executeScripts(type: TriggerType, context: ScriptContext) {
        triggerScripts[type]?.forEach { script ->
            if (!requiresItemValidation(type) || matchesSpecialItem(context.player, script.id)) {
                script.execute(context)
            }
        }
    }

    private fun requiresItemValidation(type: TriggerType): Boolean {
        return when (type) {
            TriggerType.BLOCK_BREAK, TriggerType.BLOCK_PLACE, TriggerType.PLAYER_INTERACT,
            TriggerType.LEFT_CLICK_ENTITY, TriggerType.RIGHT_CLICK_ENTITY -> true
            else -> false
        }
    }

    private fun matchesSpecialItem(player: Player?, scriptId: String): Boolean {
        player ?: return false
        val hand = player.inventory.itemInMainHand
        if (hand.type.isAir) return false

        return runCatching {
            NBT.get<String>(hand) { nbt -> nbt.getString(SPECIAL_ITEM_TAG) }.equals(scriptId.lowercase())
        }.getOrDefault(false)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val context = ScriptContext().apply {
            this.event = event
            this.player = event.player
        }
        executeScripts(TriggerType.PLAYER_JOIN, context)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val context = ScriptContext().apply {
            this.event = event
            this.player = event.player
        }
        executeScripts(TriggerType.PLAYER_QUIT, context)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val context = ScriptContext().apply {
            this.event = event
            this.player = event.player
            this.block = event.block
        }
        executeScripts(TriggerType.BLOCK_BREAK, context)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val context = ScriptContext().apply {
            this.event = event
            this.player = event.player
            this.block = event.block
        }
        executeScripts(TriggerType.BLOCK_PLACE, context)
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val context = ScriptContext().apply {
            this.event = event
            this.player = event.player
            this.block = event.clickedBlock
        }
        executeScripts(TriggerType.PLAYER_INTERACT, context)
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        (event.damager as? Player)?.let { player ->
            val context = ScriptContext().apply {
                this.event = event
                this.player = player
                this.targetEntity = event.entity
            }
            executeScripts(TriggerType.LEFT_CLICK_ENTITY, context)
        }
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val context = ScriptContext().apply {
            this.event = event
            this.player = event.player
            this.targetEntity = event.rightClicked
        }
        executeScripts(TriggerType.RIGHT_CLICK_ENTITY, context)
    }

    @EventHandler
    fun onPotionSplash(event: PotionSplashEvent) {
        val tag = runCatching {
            NBT.get<String>(event.potion.item) { it.getString(SPECIAL_ITEM_TAG) }
        }.getOrNull() ?: return

        val shooter = event.entity.shooter as? Player

        event.affectedEntities.forEach { affected ->
            val entityContext = ScriptContext().apply {
                this.event = event
                this.entity = event.entity
                this.player = shooter
                this.targetEntity = affected
            }

            triggerScripts[TriggerType.POTION_SPLASH]?.forEach { script ->
                if (script.id.equals(tag, ignoreCase = true)) {
                    script.execute(entityContext)
                }
            }
        }
    }
}
