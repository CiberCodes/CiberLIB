package net.ciber.lib

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

class CiberConfig(plugin: Plugin, name: String) {
    private val file = File(plugin.dataFolder.path, name)
    lateinit var config: YamlConfiguration

    init {
        reloadConfig()
    }

    private fun reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun exists(): Boolean {
        return file.exists()
    }

    fun save() {
        config.save(file)
    }

    fun contains(path: String): Boolean {
        return config.contains(path)
    }

    fun getSection(path: String, keys: Boolean): MutableSet<String>? {
        return config.getConfigurationSection(path)?.getKeys(keys)
    }

    fun writeText(text: String) {
        file.writeText(text, Charsets.UTF_8)
    }

    fun readText(): String {
        return file.readText()
    }

    operator fun <T> set(path: String, value: T) {
        config[path] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(path: String): T {
        return config[path] as T
    }
}