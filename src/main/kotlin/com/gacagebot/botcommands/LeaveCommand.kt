package com.gacagebot.botcommands

import com.gacagebot.constants.PrefixCommand
import com.gacagebot.recources.values.strings.Strings
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class LeaveCommand : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        super.onMessageReceived(event)
        if (event.author.isBot) return
        if (!event.message.contentRaw.startsWith(PrefixCommand.GLEAVE)) return
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
        event.guildChannel.sendMessage(Strings.leave_from_voice_channel_message).queue()
        event.guild.audioManager.closeAudioConnection()
    }

}