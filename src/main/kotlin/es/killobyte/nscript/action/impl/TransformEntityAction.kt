package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.entity.EntityType

class TransformEntityAction(private val entityType: EntityType) : Action {
    override fun execute(context: ScriptContext) {
        context.targetEntity?.let {
            it.world.spawnEntity(it.location, entityType)
            it.remove()
        }
    }
}
