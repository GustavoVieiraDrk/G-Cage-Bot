package com.gacagebot.botcommands

import com.gacagebot.commandmanager.BotCommand
import com.gacagebot.commandmanager.BotCommandContextImpl
import com.gacagebot.constants.PrefixCommand
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class LeaveCommand : BotCommand {

    private fun onLeaveChannelAction(event: MessageReceivedEvent) {
        event.guildChannel.sendMessage(LocalizeString.get(StringId.LEAVE_FROM_VOICE_CHANNEL.id)).queue()
        event.guild.audioManager.closeAudioConnection()
    }
    override fun handle(commandContext: BotCommandContextImpl) {
        val txtChannel = commandContext.getChannel()
        val selfVoiceSate = commandContext.getSelfMember()?.voiceState ?: return
        val memberVoiceState = commandContext.getMember()?.voiceState ?: return

        if (!selfVoiceSate.inAudioChannel()) {
            displayMessage(txtChannel, LocalizeString.get(StringId.FAILED_LEAVE_CHANNEL_BOT_IS_NOT_IN_ANY_VOICE_CHANNEL.id))
            return
        }

        if (!memberVoiceState.inAudioChannel()) {
            displayMessage(txtChannel, LocalizeString.get(StringId.FAILED_COMMAND_YOU_NOT_IN_VOICE_CHANNEL.id))
            return
        }

        onLeaveChannelAction(commandContext.getEvent())

    }

    override fun getCommandName(): String {
        return PrefixCommand.LEAVE.command
    }

    override fun getHelpMessage(): String {
        return LocalizeString.get(StringId.HELP_LEAVE_MESSAGE.id)
    }

    override fun displayMessage(txtChannel: MessageChannelUnion, message: String) {
        txtChannel.sendMessage(message).queue()
    }

}
