package com.gacagebot.botcommands

import com.gacagebot.commandmanager.BotCommand
import com.gacagebot.commandmanager.BotCommandContextImpl
import com.gacagebot.constants.PrefixCommand
import com.gacagebot.lavaplayer.PlayerManager
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction

class SkipCommand : BotCommand {

    override fun handle(commandContext: BotCommandContextImpl) {
        val txtChannel = commandContext.getChannel()
        val event = commandContext.getEvent()
        val memberVoiceStater = commandContext.getMember()?.voiceState ?: return
        val musicManager = PlayerManager.newInstance()?.getGuildMusicManger(event.guild) ?: return
        val audioPlayer = musicManager.player

        if (!event.isFromGuild) return
        if (!memberVoiceStater.inAudioChannel()) {
            displayMessage(txtChannel, LocalizeString.get(StringId.FAILED_COMMAND_YOU_NOT_IN_VOICE_CHANNEL.id))
            return
        }
        if (audioPlayer.playingTrack == null) {
            displayMessage(txtChannel, LocalizeString.get(StringId.FAILED_SKIP_NO_TRACK_PLAYING.id))
            return
        }

        musicManager.scheduler.nextTrack()
        displayMessage(txtChannel, LocalizeString.get(StringId.SUCCESS_SKIP_TRACK_MESSAGE.id))
    }

    override fun getCommandName(): String {
        return PrefixCommand.SKIP.command
    }

    override fun getHelpMessage(): String {
        return LocalizeString.get(StringId.HELP_COMMAND_SKIP_MESSAGE.id)
    }

    override fun displayMessage(txtChannel: MessageChannelUnion, message: String) {
        txtChannel.sendMessage(message).queue()
    }

}
