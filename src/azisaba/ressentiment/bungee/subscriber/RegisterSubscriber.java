package azisaba.ressentiment.bungee.subscriber;

import amata1219.redis.plugin.messages.common.RedisSubscriber;
import azisaba.ressentiment.Output;
import com.google.common.io.ByteArrayDataInput;

import java.util.HashSet;
import java.util.Set;

public class RegisterSubscriber implements RedisSubscriber {

    public final Set<String> serverNameRegistry = new HashSet<>();

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput in) {
        serverNameRegistry.add(sourceServerName);
        Output.printf("onRegister: received message", "source-server-name", sourceServerName);
    }

}
