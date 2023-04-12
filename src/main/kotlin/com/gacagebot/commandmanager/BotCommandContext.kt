package com.gacagebot.commandmanager

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.sharding.ShardManager

interface BotCommandContext {

    fun getGuild(): Guild {
        return getEvent().guild
    }

    fun getEvent(): MessageReceivedEvent

    fun getChannel(): MessageChannelUnion {
        return getEvent().channel
    }

    fun getMessage(): Message? {
        return getEvent().message
    }

    fun getAuthor(): User? {
        return getEvent().author
    }

    fun getMember(): Member? {
        return getEvent().member
    }

    fun getJDA(): JDA {
        return getEvent().jda
    }

    fun getShardManager(): ShardManager? {
        return getJDA().shardManager
    }

    fun getSelfUser(): User? {
        return getJDA().selfUser
    }

    fun getSelfMember(): Member? {
        return getGuild().selfMember
    }

}
