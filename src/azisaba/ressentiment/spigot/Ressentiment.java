package azisaba.ressentiment.spigot;

import amata1219.redis.plugin.messages.common.RedisPluginMessagesAPI;
import azisaba.ressentiment.Channels;
import azisaba.ressentiment.spigot.listener.PlayerJoinListener;
import azisaba.ressentiment.spigot.listener.PlayerQuitListener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Ressentiment extends JavaPlugin {

    private final PluginManager pluginManager = getServer().getPluginManager();
    private final RedisPluginMessagesAPI redis = (RedisPluginMessagesAPI) pluginManager.getPlugin("RedisPluginMessages");

    @Override
    public void onEnable() {
        redis.registerOutgoingChannel(
                Channels.REGISTER,
                Channels.CONTROL
        );

        registerListeners(
                new PlayerJoinListener(redis.publisher()),
                new PlayerQuitListener(this, redis.publisher())
        );
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) pluginManager.registerEvents(listener, this);
    }

}
