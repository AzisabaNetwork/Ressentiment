package azisaba.ressentiment.spigot.listener;

import amata1219.redis.plugin.messages.common.RedisPublisher;
import amata1219.redis.plugin.messages.common.io.ByteIO;
import azisaba.ressentiment.Channels;
import azisaba.ressentiment.spigot.Ressentiment;
import com.google.common.io.ByteArrayDataOutput;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final Ressentiment plugin;
    private final RedisPublisher publisher;

    public PlayerQuitListener(Ressentiment plugin, RedisPublisher publisher) {
        this.publisher = publisher;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(PlayerQuitEvent event) {
        ByteArrayDataOutput out = ByteIO.newDataOutput();
        out.writeUTF(event.getPlayer().getName());

        int delayTicks = PlayerQuitEvent.getHandlerList().getRegisteredListeners().length;

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> publisher.sendRedisMessage(Channels.CONTROL, out), delayTicks);
    }

}
