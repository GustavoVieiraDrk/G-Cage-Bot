package com.gacagebot.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

class GuildMusicManager(manager: AudioPlayerManager) {
    val player: AudioPlayer
    val scheduler: TrackScheduler

    init {
        player = manager.createPlayer()
        scheduler = TrackScheduler(player)
        player.addListener(scheduler)
    }

    val sendHandler: AudioPlayerSendHandler
        get() = AudioPlayerSendHandler(player)
}