package azisaba.ressentiment.spigot.subscriber;

import amata1219.redis.plugin.messages.common.RedisPublisher;
import amata1219.redis.plugin.messages.common.RedisSubscriber;
import amata1219.redis.plugin.messages.common.io.ByteIO;
import azisaba.ressentiment.Channels;
import azisaba.ressentiment.Output;
import com.google.common.io.ByteArrayDataInput;

public class InitialMessageSubscriber implements RedisSubscriber {

    private final RedisPublisher publisher;

    public InitialMessageSubscriber(RedisPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput in) {
        publisher.sendRedisMessage(Channels.REGISTER, ByteIO.newDataOutput());
        Output.printf("onInit: request for registering this server");
    }

}
