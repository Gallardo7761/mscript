package es.killobyte.nscript

import dev.dejvokep.boostedyaml.YamlDocument
import es.killobyte.nscript.action.Action
import es.killobyte.nscript.action.impl.ConditionAction
import es.killobyte.nscript.condition.impl.AlwaysCondition
import es.killobyte.nscript.parser.ScriptParser
import es.killobyte.nscript.trigger.TriggerManager
import es.killobyte.nscript.trigger.TriggerType
import org.bukkit.plugin.java.JavaPlugin

class NScriptEngine(
    private val plugin: JavaPlugin,
    config: YamlDocument
) {
    val triggerManager = TriggerManager()
    val scriptParser = ScriptParser(config)

    fun onEnable() {
        plugin.server.pluginManager.registerEvents(triggerManager, plugin)
        loadScripts()
        plugin.logger.info("nscript engine initialized")
    }

    fun onDisable() {
        triggerManager.unregisterAll()
        plugin.logger.info("nscript engine shutdown")
    }

    fun loadScripts() {
        triggerManager.unregisterAll()
        val scriptsSection = scriptParser.config.getSection("scripts") ?: return

        for (key in scriptsSection.keys) {
            val scriptId = key.toString()
            val scriptSection = scriptsSection.getSection(scriptId) ?: continue

            if (scriptSection.contains("events")) {
                val eventsSection = scriptSection.getSection("events") ?: continue
                for (triggerTypeKey in eventsSection.keys) {
                    parseAndRegisterTrigger(scriptId, eventsSection, triggerTypeKey.toString())
                }
            } else {
                val triggerTypeStr = scriptSection.getString("trigger")
                if (triggerTypeStr != null) {
                    runCatching {
                        val triggerType = TriggerType.valueOf(triggerTypeStr.uppercase())
                        val script = scriptParser.parseScript(scriptId, scriptSection)
                        triggerManager.registerScript(triggerType, script)
                    }.onFailure {
                        plugin.logger.warning("Invalid trigger type '$triggerTypeStr' in script: $scriptId")
                    }
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseAndRegisterTrigger(scriptId: String, eventsSection: dev.dejvokep.boostedyaml.block.implementation.Section, triggerTypeStr: String) {
        runCatching {
            val triggerType = TriggerType.valueOf(triggerTypeStr.uppercase())
            val allBlockActions = mutableListOf<Action>()

            (eventsSection.getList(triggerTypeStr) as? List<Map<*, *>>)?.forEach { rawBlock ->
                val conditionStr = rawBlock["condition"]?.toString() ?: "always"
                val actionsObj = rawBlock["actions"]

                val condition = scriptParser.parseCondition(conditionStr, scriptId) ?: AlwaysCondition()

                val actions = (actionsObj as? List<*>)?.mapNotNull {
                    scriptParser.parseAction(it.toString(), scriptId)
                } ?: emptyList()

                allBlockActions.add(ConditionAction(condition, actions, emptyList()))
            }

            val script = Script(scriptId, emptyList(), allBlockActions)
            triggerManager.registerScript(triggerType, script)
            plugin.logger.info("Loaded script: $scriptId on trigger ${triggerType.name}")
        }.onFailure {
            plugin.logger.warning("Invalid trigger type '$triggerTypeStr' in script: $scriptId")
        }
    }
}
