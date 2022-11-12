package com.gacagebot.main

import com.gacagebot.botcommands.*
import com.gacagebot.botevents.EventJoin
import com.gacagebot.constants.Constants
import com.gacagebot.gatewaybotintents.BotIntents
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.util.*

fun main(args: Array<String>) {

    JDABuilder.create(Constants.BOT_TOKEN, BotIntents.INTENTS)
        .setActivity(Activity.playing(Constants.DJ_GCAGE))
        .setStatus(OnlineStatus.ONLINE)
        .disableCache(EnumSet.of(
            CacheFlag.ACTIVITY,
            CacheFlag.EMOJI
        ))
        .enableCache(CacheFlag.VOICE_STATE)
        .addEventListeners(EventJoin())
        .addEventListeners(PlayCommand())
        .addEventListeners(JoinCommand())
        .addEventListeners(LeaveCommand())
        .addEventListeners(StopCommand())
        .addEventListeners(SkipCommand())
        .build()

}
