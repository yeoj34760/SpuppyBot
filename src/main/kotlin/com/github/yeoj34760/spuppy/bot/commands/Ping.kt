package com.github.yeoj34760.spuppy.bot.commands

import com.github.yeoj34760.spuppy.command.Command
import com.github.yeoj34760.spuppy.command.CommandEvent

object Ping : Command(name = "ping", alias = listOf("ping")) {
    override suspend fun execute(event: CommandEvent) {
        event.channel.sendMessage("`${event.jda.gatewayPing}ms` pong!").complete()
    }
}