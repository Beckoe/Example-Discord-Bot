import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import commands.CommandManager;
import commands.HelpCommand;
import listeners.EventListener;

import javax.security.auth.login.LoginException;

public class DiscordBot
{

    private final Dotenv config = Dotenv.configure().load();

    private final ShardManager shardManager;

    public DiscordBot() throws LoginException
    {
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.get("TOKEN"));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Something Fun"));
        builder.setMemberCachePolicy(MemberCachePolicy.ONLINE);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.ROLE_TAGS);
        shardManager = builder.build();

        //Listeners
        shardManager.addEventListener(new EventListener(), new CommandManager(), new HelpCommand());
    }

    public ShardManager getShardManager()
    {
        return shardManager;
    }

    public static void main(String[] args)
    {
        try
        {
            DiscordBot bot = new DiscordBot();
        }
        catch (LoginException e)
        {
            System.out.println("ERROR: Invalid bot token.");
        }
    }
}
