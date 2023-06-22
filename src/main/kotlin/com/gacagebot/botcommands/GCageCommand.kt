package com.gacagebot.botcommands

import com.gacagebot.commandmanager.BotCommand
import com.gacagebot.commandmanager.BotCommandContextImpl
import com.gacagebot.constants.PrefixCommand
import com.gacagebot.domain.datasource.TenorApiDataSource
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import io.reactivex.rxjava3.schedulers.Schedulers
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion

class GCageCommand : BotCommand {

    override fun handle(commandContext: BotCommandContextImpl) {
        val tenorApiRepository by lazy { TenorApiDataSource }
        val txtChannel = commandContext.getChannel()
        val stringErrorMessage = String.format(
            LocalizeString.get(StringId.FAILED_GCAGE_FIND_GIF_WITH.id),
            commandContext.getArgs().joinToString(" ")
        )
        val querySearch =
            if (commandContext.getArgs().isNotEmpty()) commandContext.getArgs().joinToString(" ") else "nicolas cage"

        tenorApiRepository.fetchTenorGif(querySearch, 1, true)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .doOnSubscribe { txtChannel.sendTyping().queue() }
            .doOnTerminate { txtChannel.sendTyping().complete() }
            .subscribe(
                {
                    displayMessage(txtChannel, it.gif.url)
                }, {
                    displayMessage(txtChannel, stringErrorMessage)
                }
            )

    }

    override fun getCommandName(): String {
        return PrefixCommand.CAGE.command
    }

    override fun getHelpMessage(): String {
        return LocalizeString.get(StringId.HELP_COMMAND_GCAGE_MESSAGE.id)
    }

    override fun displayMessage(txtChannel: MessageChannelUnion, message: String) {
        txtChannel.sendMessage(message).queue()
    }
}
