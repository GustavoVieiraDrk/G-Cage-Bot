package com.gacagebot.botcommands

import com.gacagebot.constants.PrefixCommand
import com.gacagebot.lavaplayer.PlayerManager
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class StopCommand : ListenerAdapter() {

    private lateinit var txtChannel: MessageChannelUnion

    override fun onMessageReceived(event: MessageReceivedEvent) {
        super.onMessageReceived(event)
        txtChannel = event.channel
        if (!event.isFromGuild) return
        if (event.author.isBot) return
        if (!event.message.contentRaw.startsWith(PrefixCommand.GSTOP)) return

        if (!event.member!!.voiceState!!.inAudioChannel()) {
            displayBotMessage("Vc não está em um canal de voz")
            return
        }

        val musicManager = PlayerManager.newInstance()?.getGuildMusicManger(event.guild)
        musicManager?.scheduler?.player?.stopTrack()
        musicManager?.scheduler?.queue?.clear()

        displayBotMessage("Parei as musicas que estavam tocando e limpei a playlist")
    }

    private fun displayBotMessage(message: String) {
        txtChannel.sendMessage(message).queue()
    }

}