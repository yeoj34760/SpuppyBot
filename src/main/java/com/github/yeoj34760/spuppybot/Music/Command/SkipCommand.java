package com.github.yeoj34760.spuppybot.Music.Command;

import com.github.yeoj34760.spuppybot.Music.MyGuild;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;

public class SkipCommand extends Command {
	final MyGuild myGuild;

	public SkipCommand(MyGuild myGuild) {
		this.myGuild = myGuild;
		this.name = "스킵";
		this.aliases = new String[] { "skip", "스킵"};
		this.help = "다음 음악으로 재생합니다.";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		myGuild.getGuildAudioPlayer(event.getGuild()).scheduler.nextTrack();
		event.reply(new EmbedBuilder().setAuthor("SpuppyBot", "https://github.com/yeoj34760", "https://yeoj34760.github.io/Spuppybot/Spuppybotlogo_128.png").setTitle("스킵합니다.").build());
	}
}
