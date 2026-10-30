package es.killobyte.nscript.condition.impl

import es.killobyte.nscript.condition.Condition
import es.killobyte.nscript.context.ScriptContext

class HasPermissionCondition(private val permission: String) : Condition {
    override fun test(context: ScriptContext): Boolean {
        return context.player?.hasPermission(permission) ?: false
    }
}
