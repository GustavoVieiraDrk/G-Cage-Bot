package com.gacagebot.commandmanager

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class BotCommandContextImpl(
    private val event: MessageReceivedEvent,
    private val args: List<String>
) : BotCommandContext {

    override fun getEvent(): MessageReceivedEvent {
        return this.event
    }

    override fun getGuild(): Guild {
        return this.getEvent().guild
    }

    fun getArgs(): List<String> {
        return this.args
    }

}
