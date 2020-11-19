package com.github.yeoj34760.spuppy.bot.commands

import com.github.yeoj34760.spuppy.bot.Bot
import com.github.yeoj34760.spuppy.command.Command
import com.github.yeoj34760.spuppy.command.CommandEvent

object Test : Command(name = "test", alias = listOf("t")) {
    override suspend fun execute(event: CommandEvent) {
        if (event.author.idLong != Bot.info.ownerId)
            return

        event.send("""
            voiceState: ${event.member?.voiceState}
            chennal: ${event.member?.voiceState?.channel}
          permissions:  ${event.member?.voiceState?.channel?.permissionOverrides}
        """.trimIndent())
    }

}