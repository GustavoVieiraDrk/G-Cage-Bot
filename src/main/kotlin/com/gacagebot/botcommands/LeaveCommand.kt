package com.gacagebot.botcommands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class LeaveCommand : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        super.onMessageReceived(event)
        if (event.author.isBot) return
        if (!event.message.contentRaw.startsWith("gleave")) return
        if (!event.member!!.voiceState!!.inAudioChannel()) {
            showWarningLeaveMessage(event)
            return
        }
        onLeaveChannelAction(event)
    }

    private fun showWarningLeaveMessage(event: MessageReceivedEvent) {
        event.guildChannel.sendMessage("Vc tem que estar no canal de voz que eu to pra me tirar!!!").queue()
    }

    private fun onLeaveChannelAction(event: MessageReceivedEvent) {
        event.guild.audioManager.closeAudioConnection()
    }

}