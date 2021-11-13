package net.ciber.lib.gui

import kotlinx.serialization.ExperimentalSerializationApi
import net.ciber.lib.CiberLIB
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@ExperimentalSerializationApi
enum class ButtonFunction : ButtonImpl, KoinComponent {
    OPEN_GUI {
        override fun onClick(
            plugin: Plugin,
            player: Player,
            button: Button,
            newTitle: String?,
            newButtons: Map<Int, Button>?
        ) {
            if ((newButtons != null) && (newTitle != null)) {
                instance.guiConfiguration.changeAndOpenGui(
                    plugin,
                    button.gui,
                    player,
                    newTitle,
                    newButtons
                )
                return
            }
            instance.guiConfiguration.openGui(plugin, button.gui, player)
        }
    },
    CLOSE_GUI {
        override fun onClick(
            plugin: Plugin,
            player: Player,
            button: Button,
            newTitle: String?,
            newButtons: Map<Int, Button>?
        ) {
            player.closeInventory()
        }
    },
    IGNORE {
        override fun onClick(
            plugin: Plugin,
            player: Player,
            button: Button,
            newTitle: String?,
            newButtons: Map<Int, Button>?
        ) {
            player.sendMessage("teste")
        }
    };

    val instance by inject<CiberLIB>()
}