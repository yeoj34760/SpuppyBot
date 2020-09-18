package com.github.yeoj34760.spuppybot.commands.music


import com.github.yeoj34760.spuppy.command.Command
import com.github.yeoj34760.spuppy.command.CommandEvent
import com.github.yeoj34760.spuppy.command.CommandSettings
import com.github.yeoj34760.spuppybot.music.GuildManager.playerControls
import com.github.yeoj34760.spuppybot.other.DiscordColor
import com.github.yeoj34760.spuppybot.other.MusicListBook
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User


@CommandSettings(name = "list")
object List : Command() {

    override fun execute(event: CommandEvent) {
        val id = event.guild.idLong

        if (playerControls[id] == null || !playerControls[id]!!.isPlayed()) {
            event.channel.sendMessage("뭔가 엄청난 걸 보여드리고 싶었지만 아쉽게도 아무 음악이 없네요").queue()
            return
        }

        //입력한 args가 없을 경우 1로 지정합니다.
        val pageNumber = if (event.args.isEmpty()) 1 else event.args[0].toInt()
        val playerControl = playerControls[id]
        val book = MusicListBook(playerControl!!.trackQueue.toTypedArray())
        val playingTrack = playerControl.playingTrack()
        val nextMusic = if (playerControl.isLooped) "무한 루프" else if (playerControl.playingTrack().info.isStream) "LIVE" else "${(playingTrack.duration - playingTrack.position) / 1000}초 남음"
        val list = if (playerControl.trackQueue.isEmpty()) "썰렁... 대기열에 아무 것도 없네요." else pageToString(book, pageNumber - 1)
        val pageContent = if (playerControl.trackQueue.isEmpty()) "page : 백지" else "page : ${pageNumber}/${book.count()}"


        val embed = EmbedBuilder()
                .setAuthor(event.author.name, null, event.author.avatarUrl)
                .setTitle("대기열 목록들 (${playerControl.trackQueue.size}개)")
                .setDescription(list)
                .addField("재생 중..", "[${playingTrack.info.title}](${playingTrack.info.uri})", true)
                .addField("다음 음악까지", nextMusic, true)
                .setFooter(pageContent)
                .setColor(DiscordColor.GREEN)
                .build()
        event.channel.sendMessage(embed).queue()


    }

    private fun pageToString(book: MusicListBook, pageNumber: Int): String {
        val temp: StringBuilder = StringBuilder()
        var number: Int = pageNumber * book.MAX_PAGE
        for (trackNumber in 0 until book[pageNumber]!!.size) {
            val track = book.pageTrack(pageNumber, trackNumber)
            temp.append("${++number} > [${track!!.info.title}](${track.info.uri}) `${(track.userData as User).name}`\n\n")
        }
        return temp.toString()
    }
}