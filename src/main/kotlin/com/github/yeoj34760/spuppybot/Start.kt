package com.github.yeoj34760.spuppybot

import com.github.yeoj34760.spuppybot.commands.*
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

val token = ""
val ownerId = ""

val playerManager = DefaultAudioPlayerManager()
fun main() {
    //플레이어매니저 설정
    playerManager.registerSourceManager(YoutubeAudioSourceManager())
    AudioSourceManagers.registerRemoteSources(playerManager)

    val commandClient = CommandClientBuilder()
            .setPrefix("?")
            .addCommands(Ping,
                    Play,
                    Stop,
                    Pause,
                    Skip,
                    Volume,
                    List)
            .setOwnerId(ownerId)
            .setHelpConsumer { }
            .setActivity(Activity.playing("fuck"))
            .build()

    var jda = JDABuilder
            .createDefault(token)
            .addEventListeners(commandClient)
            .build()

}