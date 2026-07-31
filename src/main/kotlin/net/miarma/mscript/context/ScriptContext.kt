package net.miarma.mscript.context

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import java.util.HashMap

/**
 * Represents the execution context of a script.
 * Holds references to entities, players, locations, etc., involved in an event.
 */
class ScriptContext {
    var event: Event? = null
    var player: Player? = null
    var targetEntity: Entity? = null
    var entity: Entity? = null
    var location: Location? = null
    var block: Block? = null
    private val variables: MutableMap<String, Any> = HashMap()

    fun setVariable(key: String, value: Any) {
        variables[key] = value
    }

    fun getVariable(key: String): Any? {
        return variables[key]
    }
}
