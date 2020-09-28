package com.github.yeoj34760.spuppybot.music.command


import com.github.yeoj34760.spuppybot.music.GuildManager
import com.github.yeoj34760.spuppybot.music.PlayerControl
import com.github.yeoj34760.spuppy.command.Command
import com.github.yeoj34760.spuppy.command.CommandEvent
import com.github.yeoj34760.spuppy.command.CommandSettings
import com.github.yeoj34760.spuppybot.waiter
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.PermissionOverride
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.concurrent.TimeUnit


@CommandSettings(name = "connect")
object Connect : Command() {
    override fun execute(event: CommandEvent) {
        if (event.args.isEmpty() && !event.member?.voiceState!!.inVoiceChannel()) {
            event.channel.sendMessage("음성 방에 들어오시거나 방 이름을 적어주세요. \n").queue()
        }

        //입력한 이름하고 동일한 채널이 있으면 channels에 추가합니다.
        val channels: ArrayList<VoiceChannel> = addChannels(event)
        var playerControl: PlayerControl? = GuildManager.playerControls[event.guild.idLong]

        if (channels.isEmpty()) {
            event.channel.sendMessage("해당 이름을 가진 사용가능한 음성 채널을 못 찾았습니다.").queue()
            return
        }

        if (channels.size == 1) {
            if (!checkPermission(channels[0].permissionOverrides.toTypedArray())) {
                event.channel.sendMessage("해당 음성 채널에 들어가려했으나 권한이 없네요.").queue()
                return
            }
            event.guild.audioManager.openAudioConnection(channels[0])
            checkPause(playerControl)
            event.channel.sendMessage("들어왔습니다.").queue()
            return
        }

        if (channels.isNotEmpty()) {
            var result: String = ""
            var messageId: Long?
            for (x in 0 until channels.size)
                result += "${x + 1}. ${channels[x].name}\n"

            val embed = EmbedBuilder()
                    .setAuthor(event.author.name, null, event.author.avatarUrl)
                    .setTitle("Channels")
                    .setDescription(result)
                    .build()

            event.channel.sendMessage("이름이 중복된 음성 채널들을 발견했습니다.\n아래에 있는 숫자중에 골라주세요.\n").embed(embed).queue { messageId = it.idLong }

            waiter.waitForEvent(MessageReceivedEvent::class.java,
                    { e ->
                        e.author.equals(event.author)
                                && e.channel.equals(event.channel)
                                && !e.message.equals(event.message)
                    },

                    { e ->
                        //숫자아닐 경우 리턴합니다.
                        val number: Int? = e.message.contentRaw.toIntOrNull()
                        //1 ~ channels 최대 사이에 맞지 않을 경우 넘어갑니다.
                        if (number != null && number in 1..channels.size) {
                            if (!checkPermission(channels[number - 1].permissionOverrides.toTypedArray())) {
                                event.channel.sendMessage("해당 음성 채널에 들어가려했으나 권한이 없네요.").queue()
                                return@waitForEvent
                            }
                            checkPause(playerControl)
                            event.guild.audioManager.openAudioConnection(channels[number - 1])
                            event.channel.sendMessage("들어왔어요.").queue()
                            return@waitForEvent
                        }

                    }, 1, TimeUnit.MINUTES) { event.channel.sendMessage("시간 초과됨").queue() }
        }
    }

    /**
     * 입력한 값하고 채널 이름이 동일하면 해당 채널에 리스트추가합니다.
     */
    private fun addChannels(event: CommandEvent): ArrayList<VoiceChannel> {
        val temp: ArrayList<VoiceChannel> = ArrayList()

        for (v in event.guild.voiceChannels)
            if (event.content == v.name)
                temp.add(v)

        return temp
    }


    private fun checkPause(playerControl: PlayerControl?) {
        if (playerControl != null && !playerControl.isPaused())
            playerControl.resume()
    }

    private fun checkPermission(permissionOverrides: Array<PermissionOverride>): Boolean {
        permissionOverrides.forEach {
            if (it.permissionHolder!!.hasPermission(Permission.VOICE_CONNECT))
                return false
        }
        return true
    }
}