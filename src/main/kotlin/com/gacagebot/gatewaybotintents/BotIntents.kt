package com.gacagebot.gatewaybotintents

import net.dv8tion.jda.api.requests.GatewayIntent

class BotIntents {

    companion object {
        val INTENTS = arrayListOf(
            GatewayIntent.DIRECT_MESSAGES,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
            GatewayIntent.SCHEDULED_EVENTS
        )
    }

}
