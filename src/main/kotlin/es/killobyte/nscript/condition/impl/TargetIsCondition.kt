package es.killobyte.nscript.condition.impl

import es.killobyte.nscript.condition.Condition
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.entity.EntityType

class TargetIsCondition(private val entityType: EntityType) : Condition {
    override fun test(context: ScriptContext): Boolean {
        return context.targetEntity?.type == entityType
    }
}
