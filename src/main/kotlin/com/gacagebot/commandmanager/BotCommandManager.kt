package com.gacagebot.commandmanager

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.annotations.Nullable
import java.util.Locale

class BotCommandManager {
    private val commandList: ArrayList<BotCommand> = ArrayList()

    fun handle(event: MessageReceivedEvent) {
        val split = event.message.contentRaw
            .replaceFirst(Regex("g"), "")
            .split(Regex("\\s+"))

        val invoke = split[0].lowercase(Locale.getDefault())
        val command: BotCommand? = getCommand(invoke)

        if (command != null) {
            val args = split.subList(1, split.size)
            val commandContext = BotCommandContextImpl(event, args)
            command.handle(commandContext)
        }
    }

    fun getCommandList() : List<BotCommand> {
        return commandList
    }

    private fun addCommand(command: BotCommand) {
        val commandNameAlreadyExist = this.commandList.stream().anyMatch {
            it.getCommandName() == command.getCommandName()
        }

        if (commandNameAlreadyExist) {
            throw IllegalArgumentException("A command with this name is already present")
        }
        commandList.add(command)
    }

    @Nullable
    fun getCommand(search: String): BotCommand? {
        val searchLower = search.lowercase(Locale.getDefault())
        for (command in this.commandList) {
            if (command.getCommandName() == searchLower || command.getAliases()!!.contains(searchLower)) {
                return command
            }
        }
        return null
    }
}
