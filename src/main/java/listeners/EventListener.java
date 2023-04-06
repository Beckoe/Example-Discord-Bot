package listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class EventListener extends ListenerAdapter
{
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        //Respond to users messages.

        TextChannel textChannel = event.getChannel().asTextChannel();

        if (!event.getAuthor().isBot())
        {
            textChannel.sendMessage("Message recieved.").queue();
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event)
    {
        //Welcome members to the server.

        String user = event.getMember().getAsMention();
        UserSnowflake userRole = event.getMember();
        TextChannel textChannel = event.getGuild().getTextChannelsByName("welcome",false).get(0);
        Role member = event.getGuild().getRolesByName("Member", false).get(0);

        textChannel.sendMessage("Welcome to the server " + user + ". Enjoy your stay!").queue();

        event.getGuild().addRoleToMember(userRole, member).queue();
    }

    @Override
    public void onGuildMemberUpdateBoostTime(@NotNull GuildMemberUpdateBoostTimeEvent event)
    {
        //Thank users for boosting server.

        String user = event.getMember().getAsMention();
        TextChannel textChannel = event.getGuild().getTextChannelsByName("boosts", false).get(0);

        textChannel.sendMessage("Thank you for boosting The Bonfire " + user + ". We appreciate it!").queue();
    }
}
