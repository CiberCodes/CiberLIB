package net.ciber.lib

import kotlinx.serialization.ExperimentalSerializationApi
import net.ciber.lib.gui.ButtonFunction
import net.ciber.lib.gui.GuiConfiguration
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@ExperimentalSerializationApi
class CiberLIB: CiberPlugin() {
    val functionsMap = mutableMapOf<String, Enum<*>>()

    val guiConfiguration = GuiConfiguration(mutableMapOf(), mutableMapOf())

    private  val libModules = module {
        single { this@CiberLIB }
    }

    override fun onEnable() {
        startKoin {
            modules(libModules)
        }

        registerFunctions(ButtonFunction.values())

        consoleMessage("§3[CiberLIB] §aBiblioteca habilitada")
    }

    override fun onDisable() {
        stopKoin()
        consoleMessage("§3[CiberLIB] §cBiblioteca desabilitada")
    }
}