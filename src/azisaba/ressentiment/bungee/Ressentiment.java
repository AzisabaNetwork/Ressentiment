package azisaba.ressentiment.bungee;

import amata1219.redis.plugin.messages.common.RedisPluginMessagesAPI;
import amata1219.redis.plugin.messages.common.io.ByteIO;
import azisaba.ressentiment.Channels;
import azisaba.ressentiment.Output;
import azisaba.ressentiment.bungee.listener.PlayerConnectListener;
import azisaba.ressentiment.bungee.subscriber.ControlSubscriber;
import azisaba.ressentiment.bungee.subscriber.RegisterSubscriber;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Ressentiment extends Plugin {

    private final PluginManager pluginManager = getProxy().getPluginManager();
    private final RedisPluginMessagesAPI redis = (RedisPluginMessagesAPI) pluginManager.getPlugin("RedisPluginMessages");

    @Override
    public void onEnable() {
        redis.registerIncomingChannels(
                Channels.REGISTER,
                Channels.CONTROL
        );

        RegisterSubscriber rms = new RegisterSubscriber();
        redis.registerSubscriber(Channels.REGISTER, rms);

        ControlSubscriber cms = new ControlSubscriber();
        redis.registerSubscriber(Channels.CONTROL, cms);

        redis.registerOutgoingChannels(Channels.INIT);
        redis.publisher().sendRedisMessage(Channels.INIT, ByteIO.newDataOutput());

        pluginManager.registerListener(this, new PlayerConnectListener(this, rms, cms));

        Output.printf("onEnable: successful enable");
    }

    @Override
    public void onDisable() {
        pluginManager.unregisterListeners(this);
    }

}
