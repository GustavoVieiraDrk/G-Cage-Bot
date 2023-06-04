package com.gacagebot.main

import com.gacagebot.botcommands.*
import com.gacagebot.botevents.EventJoin
import com.gacagebot.constants.Secrets
import com.gacagebot.gatewaybotintents.BotIntents
import com.gacagebot.listeners.EventListenerAdapter
import com.gacagebot.localizestrings.LocalizeString
import com.gacagebot.localizestrings.StringId
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.util.*

fun main(args: Array<String>) {

    JDABuilder.create(Secrets.BOT_TOKEN.getString, BotIntents.INTENTS)
        .setActivity(Activity.playing(LocalizeString.get(StringId.ACTIVITY_DJ_G_CAGE.id)))
        .setStatus(OnlineStatus.ONLINE)
        .disableCache(EnumSet.of(
            CacheFlag.ACTIVITY,
            CacheFlag.EMOJI
        ))
        .enableCache(CacheFlag.VOICE_STATE)
        .addEventListeners(EventListenerAdapter())
        .addEventListeners(EventJoin())
        .build()

}
