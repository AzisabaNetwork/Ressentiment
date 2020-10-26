package azisaba.ressentiment.spigot.listener;

import amata1219.redis.plugin.messages.common.RedisPublisher;
import amata1219.redis.plugin.messages.common.io.ByteIOStreams;
import azisaba.ressentiment.Channels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final RedisPublisher publisher;

    public PlayerJoinListener(RedisPublisher publisher) {
        this.publisher = publisher;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PlayerJoinEvent event) {
        publisher.sendRedisMessage(Channels.REGISTER, ByteIOStreams.newDataOutput());
    }

}
