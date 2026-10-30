package es.killobyte.nscript.parser

import dev.dejvokep.boostedyaml.YamlDocument
import dev.dejvokep.boostedyaml.block.implementation.Section
import es.killobyte.nscript.Script
import es.killobyte.nscript.action.Action
import es.killobyte.nscript.action.impl.*
import es.killobyte.nscript.condition.Condition
import es.killobyte.nscript.condition.impl.*
import es.killobyte.nscript.util.SoundUtil
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.potion.PotionEffectType
import java.util.ArrayList

class ScriptParser(val config: YamlDocument) {

    fun parseScript(id: String, section: Section): Script {
        val conditions = section.getStringList("conditions").mapNotNull { parseCondition(it, id) }
        val actions = section.getStringList("actions").mapNotNull { parseAction(it, id) }
        return Script(id, conditions, actions)
    }

    fun parseCondition(conditionString: String, scriptId: String): Condition? {
        if (conditionString.equals("always", ignoreCase = true)) {
            return AlwaysCondition()
        }

        val parts = conditionString.split(":", limit = 2).takeIf { it.size >= 2 }
            ?: conditionString.split(" ", limit = 2)

        if (parts.size < 2) return null

        val type = parts[0]
        val value = parts[1]

        return when (type.lowercase()) {
            "has_permission" -> HasPermissionCondition(value)
            "block_type" -> Material.matchMaterial(value)?.let { BlockTypeCondition(it) }
            "target_is" -> runCatching { EntityType.valueOf(value.uppercase()) }.getOrNull()?.let { TargetIsCondition(it) }
            "chance_from_item" -> config.getSection("scripts.$scriptId")?.getInt("properties.$value")?.let { ChanceCondition(it) }
            "chance" -> value.toIntOrNull()?.let { ChanceCondition(it) }
            else -> null
        }
    }

    fun parseAction(actionString: String, scriptId: String): Action? {
        val actionName: String
        val args: List<String>

        if (actionString.contains("(") && actionString.endsWith(")")) {
            actionName = actionString.substringBefore("(").trim()
            val argsRaw = actionString.substringAfter("(").removeSuffix(")")
            args = if (argsRaw.isNotEmpty()) {
                argsRaw.split(",(?![^\\[]*])(?![^(]*\\))").map { it.trim() }
            } else {
                emptyList()
            }
        } else {
            val parts = actionString.split(" ", limit = 2)
            actionName = parts[0]
            args = parts.getOrNull(1)?.split(" ") ?: emptyList()
        }

        return when (actionName.lowercase()) {
            "send_message" -> SendMessageAction(args.joinToString(" "))
            "give_item" -> {
                val material = Material.matchMaterial(args.getOrElse(0) { "" })
                val amount = args.getOrNull(1)?.toIntOrNull() ?: 1
                material?.let { GiveItemAction(it, amount) }
            }
            "dispatch_command" -> DispatchCommandAction(args.joinToString(" "), false)
            "dispatch_console_command" -> DispatchCommandAction(args.joinToString(" "), true)
            "cancel_event" -> CancelEventAction()
            "kill_entity" -> KillEntityAction()
            "clear_hand" -> ClearHandAction()
            "sound" -> {
                val sound = SoundUtil.getSound(args.getOrElse(0) { "" })
                val vol = args.getOrNull(1)?.toFloatOrNull()
                val pitch = args.getOrNull(2)?.toFloatOrNull()
                if (sound != null && vol != null && pitch != null) {
                    PlaySoundAction(sound, vol, pitch)
                } else {
                    null
                }
            }
            "drop_spawner" -> DropSpawnerAction()
            "break_block" -> BreakBlockAction()
            "drop_item" -> {
                val material = Material.matchMaterial(args.getOrElse(0) { "" })
                val amount = args.getOrNull(1)?.toIntOrNull() ?: 1
                material?.let { DropItemAction(it, amount) }
            }
            "set_baby" -> SetBabyAction()
            "transform_entity" -> {
                val entityType = runCatching { EntityType.valueOf(args.getOrElse(0) { "" }.uppercase()) }.getOrNull()
                entityType?.let { TransformEntityAction(it) }
            }
            "damage_item" -> args.getOrNull(0)?.toIntOrNull()?.let { DamageItemAction(it) }
            "firework" -> {
                val color = when (args.getOrNull(0)?.lowercase()) {
                    "lime" -> Color.LIME
                    else -> Color.WHITE
                }
                val type = runCatching { FireworkEffect.Type.valueOf(args.getOrNull(1)?.uppercase() ?: "") }.getOrNull()
                type?.let { FireworkAction(color, it) }
            }
            "action_if_chance" -> {
                val chance = args.getOrNull(0)?.toIntOrNull() ?: config.getSection("scripts.$scriptId")?.getInt("properties.${args.getOrNull(0)}") ?: 100
                val successActions = args.getOrNull(1)?.removeSurrounding("[", "]")?.split(",(?![^(]*\\))")?.mapNotNull { parseAction(it.trim(), scriptId) } ?: emptyList()
                val failActions = args.getOrNull(2)?.removeSurrounding("[", "]")?.split(",(?![^(]*\\))")?.mapNotNull { parseAction(it.trim(), scriptId) } ?: emptyList()
                ActionIfChanceAction(chance, successActions, failActions)
            }
            "zombify" -> ZombifyAction()
            "add_potion_effect" -> {
                val key = NamespacedKey.minecraft(args.getOrElse(0) { "" }.lowercase())
                val type = Registry.EFFECT.get(key) ?: PotionEffectType.getByName(args.getOrElse(0) { "" }.uppercase())
                val duration = args.getOrNull(1)?.toIntOrNull()
                val amplifier = args.getOrNull(2)?.toIntOrNull()
                if (type != null && duration != null && amplifier != null) {
                    AddPotionEffectAction(type, duration, amplifier)
                } else {
                    null
                }
            }
            "for_each_affected_entity" -> {
                val loopActions = args.getOrNull(0)?.removeSurrounding("[", "]")?.split(",(?![^(]*\\))")?.mapNotNull { parseAction(it.trim(), scriptId) } ?: emptyList()
                ForEachAffectedEntityAction(loopActions)
            }
            "if" -> {
                val condition = parseCondition(args.getOrElse(0) { "" }, scriptId)
                val successActions = args.getOrNull(1)?.removeSurrounding("[", "]")?.split(",(?![^(]*\\))")?.mapNotNull { parseAction(it.trim(), scriptId) } ?: emptyList()
                val failActions = args.getOrNull(2)?.removeSurrounding("[", "]")?.split(",(?![^(]*\\))")?.mapNotNull { parseAction(it.trim(), scriptId) } ?: emptyList()
                condition?.let { ConditionAction(it, successActions, failActions) }
            }
            else -> null
        }
    }
}
