package com.example.pluginName.command

import com.example.pluginName.PluginName
import com.example.pluginName.command.sub.*
import com.example.pluginName.type.language.Message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil
import java.util.*
import java.util.stream.Collectors

class TemplateCommand(pluginName: PluginName) : CommandExecutor, TabCompleter {
    private val subcommands = HashMap<SubCommandType, SubCommand>().apply {
        put(SubCommandType.HELP, HelpSubCommand())
        put(SubCommandType.Test, TestSubCommand(pluginName))
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        var type = if (args.isEmpty()) SubCommandType.HELP else null
        if (args.isNotEmpty()) {
            type = SubCommandType.values().find { args[0].equals(it.name, true) || args[0].equals(it.alias, true) }
            if (type == null) {
                sender.sendMessage(Message.illegalArguments)
                return false
            }
        }

        if (!sender.hasPermission(type!!.permission)) {
            sender.sendMessage(Message.noPermission)
            return false
        }

        subcommands[type]!!.execute(sender, args)
        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.size == 1) {
            return StringUtil.copyPartialMatches(
                args[0],
                Arrays.stream(SubCommandType.values())
                    .map { subCommandEnum -> subCommandEnum.command.lowercase(Locale.getDefault()) }
                    .collect(Collectors.toList()),
                ArrayList())
        }

        val type = SubCommandType.values().find { args[0].equals(it.name, true) || args[0].equals(it.alias, true) }
            ?: return null
        if (!sender.hasPermission(type.permission)) {
            return null
        }

        return subcommands[type]!!.tabComplete(sender, args)
    }
}