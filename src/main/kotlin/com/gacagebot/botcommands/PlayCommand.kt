package com.gacagebot.botcommands

import com.gacagebot.constants.PrefixCommand
import com.gacagebot.lavaplayer.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.managers.AudioManager
import java.net.URI
import java.net.URISyntaxException

class PlayCommand : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        super.onMessageReceived(event)
        val audioManager: AudioManager = event.guild.audioManager

        if (!event.isFromGuild) return
        if (event.author.isBot) return
        if (!event.message.contentRaw.startsWith(PrefixCommand.GPLAY)) return

        if (!event.member!!.voiceState!!.inAudioChannel()) {
            event.channel.sendMessage("Vc Não está em um canal de voz dua sua mula").queue()
            return
        }

        var link = event.message.contentRaw.split(" ", ignoreCase = false, limit = 2).last()

        if (!isUrl(link)) {
            link = "ytsearch: ".plus(link).plus(" audio")
        }

        val memberVoiceChannel = event.member?.voiceState?.channel
        audioManager.openAudioConnection(memberVoiceChannel)

        PlayerManager.newInstance()?.loadAndPlay(event.channel.asTextChannel(), link)
    }

    private fun isUrl(url: String) : Boolean {
        return try {
            URI(url)
            true
        } catch (e: URISyntaxException) {
            false
        }
    }

}
