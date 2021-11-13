package net.ciber.lib.gui

import kotlinx.serialization.*
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

@ExperimentalSerializationApi
interface ButtonImpl {
    fun onClick(
        plugin: Plugin,
        player: Player,
        button: Button,
        newTitle: String?,
        newButtons: Map<Int, Button>?
    )
}