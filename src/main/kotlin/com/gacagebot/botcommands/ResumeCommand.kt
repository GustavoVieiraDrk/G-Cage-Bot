package com.gacagebot.botcommands

import com.gacagebot.commandmanager.BotCommand
import com.gacagebot.commandmanager.BotCommandContextImpl
import com.gacagebot.constants.PrefixCommand
import com.gacagebot.lavaplayer.PlayerManager
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion

class ResumeCommand : BotCommand {
    override fun handle(commandContext: BotCommandContextImpl) {
        val txtChannel = commandContext.getChannel()
        val event = commandContext.getEvent()
        val voiceStateMember = commandContext.getMember()?.voiceState ?: return
        val musicManager = PlayerManager.newInstance()?.getGuildMusicManger(event.guild) ?: return

        if (!event.isFromGuild) return

        if (!voiceStateMember.inAudioChannel()) {
            displayMessage(txtChannel, LocalizeString.get(StringId.FAILED_COMMAND_YOU_NOT_IN_VOICE_CHANNEL.id))
            return
        }

        musicManager.scheduler.player.isPaused = false

        displayMessage(txtChannel,LocalizeString.get(StringId.SUCCESS_RESUME_TRACK_MESSAGE.id))
    }

    override fun getCommandName(): String {
        return PrefixCommand.RESUME.command
    }

    override fun getHelpMessage(): String {
        return LocalizeString.get(StringId.HELP_COMMAND_RESUME_MESSAGE.id)
    }

    override fun displayMessage(txtChannel: MessageChannelUnion, message: String) {
        txtChannel.sendMessage(message).queue()
    }
}