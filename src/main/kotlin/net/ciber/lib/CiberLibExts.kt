package net.ciber.lib

import kotlinx.serialization.ExperimentalSerializationApi
import net.ciber.lib.gui.ButtonFunction
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties

@ExperimentalSerializationApi
fun <T : Enum<T>> CiberLIB.registerFunctions(functions: Array<T>) {
    functions.forEach { function ->
        if (!functionsMap.containsKey(function.name)) functionsMap[function.name] = function
    }
}

fun CiberPlugin.command(name: String, onExecute: (sender: CommandSender, args: Array<out String>) -> Unit) {
    getCommand(name)?.setExecutor { sender, _, _, args ->
        onExecute(sender, args)
        false
    }
}

fun CiberPlugin.readArgsText(init: Int, args: Array<out String>): String {
    val text = StringBuilder()
    var id = 0
    for (arg in args) {
        if (id < init) {
            id++
            continue
        }
        if (id > init) {
            text.append(" ")
        }
        text.append(arg.replace("&", "§"))
        id++
    }
    return text.toString()
}

fun CiberConfig.reloadMessages(messageClass: Any) {
    if (this.exists()) {
        messageClass::class.declaredMemberProperties.forEach { member ->
            if (member !is KMutableProperty<*>) return@forEach

            member.setter.call(
                messageClass,
                this.get<Any?>(member.name).toString().replace("&", "§")
            )
        }
        return
    }
    messageClass::class.declaredMemberProperties.forEach { member ->
        if (member !is KMutableProperty<*>) return@forEach
        this[member.name] = member.getter.call(messageClass)
            .toString().replace("§", "&")
    }
    this.save()
}

fun consoleMessage(message: String) {
    Bukkit.getConsoleSender().sendMessage(message)
}