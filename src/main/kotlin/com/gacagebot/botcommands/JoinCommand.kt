package com.gacagebot.botcommands

import com.gacagebot.commandmanager.BotCommand
import com.gacagebot.commandmanager.BotCommandContextImpl
import com.gacagebot.constants.PrefixCommand
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion

class JoinCommand : BotCommand {

    override fun handle(commandContext: BotCommandContextImpl) {
        val txtChannel = commandContext.getChannel()
        val selfMember = commandContext.getSelfMember()
        val selfVoiceState = selfMember?.voiceState ?: return

        if (selfVoiceState.inAudioChannel()) {
            displayMessage(txtChannel, LocalizeString.get(StringId.FAILED_JOIN_CHANNEL_BOT_IS_ALREADY_IN_THIS_CHANNEL.id))
            return
        }

        val member = commandContext.getMember()
        val memberVoiceState = member?.voiceState ?: return

        if (!memberVoiceState.inAudioChannel()) {
            displayMessage(txtChannel, LocalizeString.get(StringId.FAILED_COMMAND_YOU_NOT_IN_VOICE_CHANNEL.id))
            return
        }

        val audioManager = commandContext.getGuild().audioManager
        val memberVoiceChannel = memberVoiceState.channel

        audioManager.openAudioConnection(memberVoiceChannel)
        displayMessage(txtChannel, LocalizeString.get(StringId.JOIN_VOICE_CHANNEL.id))

    }

    override fun getCommandName(): String {
        return PrefixCommand.JOIN.command
    }

    override fun getHelpMessage(): String {
        return LocalizeString.get(StringId.HELP_JOIN_MESSAGE.id)
    }

    override fun displayMessage(txtChannel: MessageChannelUnion, message: String) {
        txtChannel.sendMessage(message).queue()
    }

}
