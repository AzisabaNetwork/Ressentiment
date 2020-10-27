package azisaba.ressentiment.bungee.subscriber;

import amata1219.redis.plugin.messages.common.RedisSubscriber;
import azisaba.ressentiment.Output;
import com.google.common.io.ByteArrayDataInput;

import java.util.HashMap;
import java.util.Map;

public class ControlMessageSubscriber implements RedisSubscriber {

    public final Map<String, Runnable> callbacks = new HashMap<>();

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput in) {
        String playerName = in.readUTF();

        Output.printf("onControl: received message", "player-name", playerName, "has-callback", callbacks.containsKey(playerName));

        Runnable callback = callbacks.remove(playerName);
        if (callback != null) callback.run();
    }

}
