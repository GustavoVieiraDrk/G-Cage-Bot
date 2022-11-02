package com.gacagebot.botcommands

import com.gacagebot.constants.PrefixCommand
import com.gacagebot.recources.values.strings.Strings
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel
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
            channel.sendMessage(Strings.voice_channel_join_denied_message).queue()
            return
        }
        val connectedChannel = event.guild.selfMember.voiceState
        if (connectedChannel == null) {
            channel.sendMessage(Strings.not_connected_voice_channel_message).queue()
            return
        }
        val audioManager = event.guild.audioManager
        if (audioManager.isConnected) {
            channel.sendMessage(Strings.already_joined_in_voice_channel_message).queue()
            return
        }
        val memberVoiceChannel = event.member?.voiceState?.channel
        audioManager.openAudioConnection(memberVoiceChannel)
        channel.sendMessage(Strings.connected_voice_channel_message).queue()
    }

}
