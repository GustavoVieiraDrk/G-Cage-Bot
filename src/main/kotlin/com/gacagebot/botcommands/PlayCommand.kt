package com.gacagebot.botcommands

import com.gacagebot.commandmanager.BotCommand
import com.gacagebot.commandmanager.BotCommandContextImpl
import com.gacagebot.constants.PrefixCommand
import com.gacagebot.lavaplayer.PlayerManager
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import java.net.URI
import java.net.URISyntaxException

class PlayCommand : BotCommand {

    override fun handle(commandContext: BotCommandContextImpl) {
        val txtChannel = commandContext.getChannel()
        val selfVoiceState = commandContext.getSelfMember()?.voiceState ?: return
        val memberVoiceState = commandContext.getMember()?.voiceState ?: return
        val audioManager = commandContext.getGuild().audioManager

        if (commandContext.getArgs().isEmpty()) {
            displayMessage(txtChannel, LocalizeString.get(StringId.HELP_COMMAND_PLAY_MESSAGE.id))
            return
        }

        if (!memberVoiceState.inAudioChannel()) {
            displayMessage(txtChannel, LocalizeString.get(StringId.FAILED_COMMAND_YOU_NOT_IN_VOICE_CHANNEL.id))
            return
        }

        var link = commandContext.getArgs().joinToString(" ")

        if (!isUrl(link)) {
            link = "ytsearch: ".plus(link)
            audioManager.openAudioConnection(memberVoiceState.channel)
            PlayerManager.newInstance()?.loadAndPlay(txtChannel.asTextChannel(), link, false)
            return
        }

        audioManager.openAudioConnection(memberVoiceState.channel)
        PlayerManager.newInstance()?.loadAndPlay(txtChannel.asTextChannel(), link, true)
    }

    override fun getCommandName(): String {
        return PrefixCommand.PLAY.command
    }

    override fun getHelpMessage(): String {
        return LocalizeString.get(StringId.HELP_COMMAND_PLAY_MESSAGE.id)
    }

    override fun displayMessage(txtChannel: MessageChannelUnion, message: String) {
        txtChannel.sendMessage(message).queue()
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
