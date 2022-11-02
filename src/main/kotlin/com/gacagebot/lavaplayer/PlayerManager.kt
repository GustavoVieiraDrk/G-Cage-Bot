package com.gacagebot.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

class PlayerManager {
    private val musicManagers: MutableMap<Long, GuildMusicManager>
    private val audioPlayerManager: AudioPlayerManager

    init {
        musicManagers = HashMap()
        audioPlayerManager = DefaultAudioPlayerManager()
        AudioSourceManagers.registerRemoteSources(audioPlayerManager)
        AudioSourceManagers.registerLocalSource(audioPlayerManager)
    }

    companion object {
        private var INSTANCE: PlayerManager? = null
        fun newInstance(): PlayerManager? {
            if (INSTANCE == null) {
                INSTANCE = PlayerManager()
            }
            return INSTANCE
        }
    }

    fun getGuildMusicManger(guild: Guild): GuildMusicManager {
        return musicManagers.computeIfAbsent(
            guild.idLong
        ) {
            val guildMusicManager = GuildMusicManager(audioPlayerManager)
            guild.audioManager.sendingHandler = guildMusicManager.sendHandler
            guildMusicManager
        }
    }

    fun loadAndPlay(txtChannel: TextChannel, trackUrl: String?) {
        val musicManager = getGuildMusicManger(txtChannel.guild)
        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                musicManager.scheduler.queue(track)
                txtChannel.sendMessage("ColoCAGE mais uma na fila " + track.info.title).queue()
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                val trackList = playlist.tracks
                if (trackList.isNotEmpty()) {
                    musicManager.scheduler.queue(trackList[0])
                    txtChannel.sendMessage("ColoCAGE mais uma na fila " + trackList[0].info.title).queue()
                }
            }

            override fun noMatches() {
                txtChannel.sendMessage("Consegui achar nada relacionado: " + trackUrl.toString()).queue()
            }

            override fun loadFailed(exception: FriendlyException) { }

        })
    }

}