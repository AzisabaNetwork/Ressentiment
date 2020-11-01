package azisaba.ressentiment.spigot;

import amata1219.redis.plugin.messages.common.RedisPluginMessagesAPI;
import amata1219.redis.plugin.messages.common.RedisPublisher;
import amata1219.redis.plugin.messages.common.io.ByteIO;
import azisaba.ressentiment.Channels;
import azisaba.ressentiment.spigot.listener.PlayerQuitListener;
import azisaba.ressentiment.spigot.subscriber.InitSubscriber;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Ressentiment extends JavaPlugin {

    private final PluginManager pluginManager = getServer().getPluginManager();
    private final RedisPluginMessagesAPI redis = (RedisPluginMessagesAPI) pluginManager.getPlugin("RedisPluginMessages");

    @Override
    public void onEnable() {
        RedisPublisher publisher = redis.publisher();

        redis.registerIncomingChannels(Channels.INIT);
        redis.registerSubscriber(Channels.INIT, new InitSubscriber(publisher));

        redis.registerOutgoingChannels(
                Channels.REGISTER,
                Channels.CONTROL
        );

        publisher.sendRedisMessage(Channels.REGISTER, ByteIO.newDataOutput());

        registerListeners(new PlayerQuitListener(this, publisher));
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) pluginManager.registerEvents(listener, this);
    }

}
