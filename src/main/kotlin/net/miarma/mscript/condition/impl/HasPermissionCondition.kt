package net.miarma.mscript.condition.impl

import net.miarma.mscript.condition.Condition
import net.miarma.mscript.context.ScriptContext

class HasPermissionCondition(private val permission: String) : Condition {
    override fun test(context: ScriptContext): Boolean {
        return context.player?.hasPermission(permission) ?: false
    }
}
