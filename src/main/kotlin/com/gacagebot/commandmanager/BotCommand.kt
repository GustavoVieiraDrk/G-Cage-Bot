package com.gacagebot.commandmanager

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion

interface BotCommand {
    fun handle(commandContext: BotCommandContextImpl)

    fun getCommandName(): String

    fun getHelpMessage() : String

    fun displayMessage(txtChannel: MessageChannelUnion, message: String)

    fun getAliases(): List<String?>? {
        return listOf()
    }
}
