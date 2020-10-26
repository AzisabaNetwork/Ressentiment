package azisaba.ressentiment.bungee;

import amata1219.redis.plugin.messages.common.RedisPluginMessagesAPI;
import azisaba.ressentiment.Channels;
import azisaba.ressentiment.bungee.listener.PlayerConnectionListener;
import azisaba.ressentiment.bungee.subscriber.ControlMessageSubscriber;
import azisaba.ressentiment.bungee.subscriber.RegisterMessageSubscriber;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Ressentiment extends Plugin {

    private final PluginManager pluginManager = getProxy().getPluginManager();
    private final RedisPluginMessagesAPI redis = (RedisPluginMessagesAPI) pluginManager.getPlugin("RedisPluginMessages");

    @Override
    public void onEnable() {
        RegisterMessageSubscriber rms = new RegisterMessageSubscriber();
        redis.registerSubscriber(Channels.REGISTER, rms);

        ControlMessageSubscriber cms = new ControlMessageSubscriber();
        redis.registerSubscriber(Channels.CONTROL, cms);

        pluginManager.registerListener(this, new PlayerConnectionListener(this, rms, cms));
    }

    @Override
    public void onDisable() {
        pluginManager.unregisterListeners(this);
    }

}
