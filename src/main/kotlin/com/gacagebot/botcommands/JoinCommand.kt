package com.gacagebot.botcommands

import com.gacagebot.constants.PrefixCommand
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class JoinCommand : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        super.onMessageReceived(event)

        val channel = event.guildChannel
        if (!event.isFromGuild) return
        if (event.author.isBot) return
        if (!event.message.contentRaw.startsWith(PrefixCommand.GJOIN)) return

        if (!event.guild.selfMember.hasPermission(channel, Permission.VOICE_CONNECT)) {
            channel.sendMessage(LocalizeString.get(StringId.FAILED_JOIN_CHANNEL_UNABLE_TO_JOIN_THE_VOICE_CHANNEL.id)).queue()
            return
        }
        val connectedChannel = event.guild.selfMember.voiceState
        if (connectedChannel == null) {
            channel.sendMessage(LocalizeString.get(StringId.FAILED_JOIN_CHANNEL_YOU_NOT_IN_VOICE_CHANNEL.id)).queue()
            return
        }
        val audioManager = event.guild.audioManager
        if (audioManager.isConnected) {
            channel.sendMessage(LocalizeString.get(StringId.FAILED_JOIN_CHANNEL_BOT_IS_ALREADY_IN_THIS_CHANNEL.id)).queue()
            return
        }
        val memberVoiceChannel = event.member?.voiceState?.channel
        audioManager.openAudioConnection(memberVoiceChannel)
        channel.sendMessage(LocalizeString.get(StringId.JOIN_VOICE_CHANNEL.id)).queue()
    }

}
