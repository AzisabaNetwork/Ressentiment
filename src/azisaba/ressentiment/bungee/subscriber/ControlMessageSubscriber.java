package azisaba.ressentiment.bungee.subscriber;

import amata1219.redis.plugin.messages.common.RedisSubscriber;
import com.google.common.io.ByteArrayDataInput;

import java.util.HashMap;
import java.util.Map;

public class ControlMessageSubscriber implements RedisSubscriber {

    public final Map<String, Runnable> callbacks = new HashMap<>();

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput in) {
        String playerName = in.readUTF();
        Runnable callback = callbacks.remove(playerName);
        if (callback != null) callback.run();
    }

}
