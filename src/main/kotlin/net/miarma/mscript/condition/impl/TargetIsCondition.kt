package net.miarma.mscript.condition.impl

import net.miarma.mscript.condition.Condition
import net.miarma.mscript.context.ScriptContext
import org.bukkit.entity.EntityType

class TargetIsCondition(private val entityType: EntityType) : Condition {
    override fun test(context: ScriptContext): Boolean {
        return context.targetEntity?.type == entityType
    }
}
