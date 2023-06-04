package com.gacagebot.listeners

import com.gacagebot.commandmanager.BotCommandManager
import com.gacagebot.constants.PrefixCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EventListenerAdapter : ListenerAdapter() {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(Companion::class.java)
    }

    private val commandManager = BotCommandManager()

    override fun onReady(event: ReadyEvent) {
        super.onReady(event)
        LOGGER.info("{} is ready", event.jda.selfUser.asTag)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        super.onMessageReceived(event)
        val user = event.author
        val commandPrefix = PrefixCommand.PREFIX.command
        val raw = event.message.contentRaw

        if (user.isBot || event.isWebhookMessage) return

        if (raw.equals(commandPrefix.plus("kill"), true) && event.author.id == "OWNER_ID") {
            LOGGER.info("Shutting down")
            event.jda.shutdown()
//            BotCommons.shutdown()
            return
        }

        if (raw.startsWith(PrefixCommand.PREFIX.command)) {
            commandManager.handle(event)
        }

    }

}