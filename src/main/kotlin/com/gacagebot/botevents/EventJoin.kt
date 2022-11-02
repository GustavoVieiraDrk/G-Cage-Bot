package com.gacagebot.botevents

import com.gacagebot.constants.Constants
import com.gacagebot.recources.values.strings.Strings
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventJoin : ListenerAdapter() {

    override fun onGuildJoin(event: GuildJoinEvent) {
        super.onGuildJoin(event)
        event
            .guild
            .getTextChannelById(Constants.CHANNEL_ID)
            ?.sendMessage(Strings.first_join_server_message)
    }

}