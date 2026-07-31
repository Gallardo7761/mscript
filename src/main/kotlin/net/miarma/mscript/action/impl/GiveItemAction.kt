package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class GiveItemAction(
    private val material: Material,
    private val amount: Int
) : Action {
    override fun execute(context: ScriptContext) {
        context.player?.inventory?.addItem(ItemStack(material, amount))
    }
}
