package azisaba.ressentiment.bungee.control;

import net.md_5.bungee.ServerConnection;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.connection.DownstreamBridge;
import net.md_5.bungee.netty.ChannelWrapper;

public class AdjustedDownstreamBridge extends DownstreamBridge {

    private final ProxyServer bungee;
    private final UserConnection user;
    private final ServerConnection server;

    public AdjustedDownstreamBridge(ProxyServer bungee, UserConnection user, ServerConnection server) {
        super(bungee, user, server);
        this.bungee = bungee;
        this.user = user;
        this.server = server;
    }

    @Override
    public void disconnected(ChannelWrapper channel) {
        ServerDisconnectEvent event = new ServerDisconnectEvent(user, server.getInfo());
        bungee.getPluginManager().callEvent(event);
    }

}
