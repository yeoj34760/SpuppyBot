package com.github.yeoj34760.spuppybot.commands.music

import com.github.yeoj34760.spuppy.command.Command
import com.github.yeoj34760.spuppy.command.CommandEvent
import com.github.yeoj34760.spuppy.command.CommandSettings

import com.github.yeoj34760.spuppybot.music.GuildManager


@CommandSettings(name = "pause")
object Pause : Command() {

    override fun execute(event: CommandEvent) {
        val id = event.guild.idLong
        if (GuildManager.playerControls[id] == null || !GuildManager.playerControls[id]!!.isPlayed()) {
            event.channel.sendMessage("현재 재생되어 있지 않네요").queue()
            return
        }
        val trackScheduler = GuildManager.playerControls[id]!!
        if (trackScheduler.isPaused()) {
            trackScheduler.resume()
            event.channel.sendMessage("다시 시작했어요.").queue()
        } else {
            trackScheduler.pause()
            event.channel.sendMessage("일시정지를 했어요.").queue()
        }
    }
}