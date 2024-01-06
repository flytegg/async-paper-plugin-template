package gg.flyte.template

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import gg.flyte.template.command.PingCommand
import gg.flyte.template.listener.JoinListener
import gg.flyte.template.listener.SuspendingJoinListener
import gg.flyte.twilight.twilight
import io.papermc.lib.PaperLib
import org.bukkit.Bukkit
import revxrsal.commands.bukkit.BukkitCommandHandler

class PluginTemplate : SuspendingJavaPlugin() {

    companion object {
        lateinit var instance: PluginTemplate
    }

    override suspend fun onEnableAsync() {
        instance = this
        twilight(this) { }

        BukkitCommandHandler.create(this).apply {
            enableAdventure()
            register(PingCommand())
            registerBrigadier()
        }

        server.pluginManager.registerSuspendingEvents(SuspendingJoinListener(), this)

        JoinListener()

        PaperLib.suggestPaper(this)
    }

    override suspend fun onDisableAsync() {

    }
}