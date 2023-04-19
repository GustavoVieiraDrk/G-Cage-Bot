package com.gacagebot.botevents

import com.gacagebot.constants.Secrets
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventJoin : ListenerAdapter() {

    override fun onGuildJoin(event: GuildJoinEvent) {
        super.onGuildJoin(event)
        event
            .guild
            .getTextChannelById(Secrets.CHANNEL_ID.getString)
            ?.sendMessage(LocalizeString.get(StringId.MEETINGS_FIRST_JOIN_IN_THE_SERVER.id))
    }

}