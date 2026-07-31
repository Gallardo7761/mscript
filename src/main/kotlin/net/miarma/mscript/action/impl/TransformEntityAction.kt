package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.entity.EntityType

class TransformEntityAction(private val entityType: EntityType) : Action {
    override fun execute(context: ScriptContext) {
        context.targetEntity?.let {
            it.world.spawnEntity(it.location, entityType)
            it.remove()
        }
    }
}
