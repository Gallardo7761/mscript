package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.Bukkit

class DispatchCommandAction(
    private val command: String,
    private val asConsole: Boolean
) : Action {
    override fun execute(context: ScriptContext) {
        var parsedCommand = command
        context.player?.let {
            parsedCommand = parsedCommand.replace("%player%", it.name)
        }

        if (asConsole) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parsedCommand)
        } else {
            context.player?.let {
                Bukkit.dispatchCommand(it, parsedCommand)
            }
        }
    }
}
