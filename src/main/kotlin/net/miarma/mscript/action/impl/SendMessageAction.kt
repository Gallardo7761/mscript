package net.miarma.mscript.action.impl

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext

class SendMessageAction(private val message: String) : Action {
    override fun execute(context: ScriptContext) {
        context.player?.let {
            val component = LegacyComponentSerializer.legacyAmpersand().deserialize(message)
            it.sendMessage(component)
        }
    }
}
