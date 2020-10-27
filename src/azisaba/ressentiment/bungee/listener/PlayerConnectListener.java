package azisaba.ressentiment.bungee.listener;

import static azisaba.ressentiment.Output.printf;
import azisaba.ressentiment.bungee.control.AdjustedDownstreamBridge;
import azisaba.ressentiment.bungee.Ressentiment;
import azisaba.ressentiment.bungee.subscriber.ControlMessageSubscriber;
import azisaba.ressentiment.bungee.subscriber.RegisterMessageSubscriber;
import net.md_5.bungee.ServerConnection;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.md_5.bungee.netty.HandlerBoss;

import java.util.*;

public class PlayerConnectListener implements Listener {

    private final Ressentiment plugin;
    private final List<String> playersSwitchingServers = new ArrayList<>();
    private final Set<String> targetServersName;
    private final Map<String, Runnable> callbacks;

    public PlayerConnectListener(Ressentiment plugin, RegisterMessageSubscriber rms, ControlMessageSubscriber cms) {
        this.plugin = plugin;
        this.targetServersName = rms.serverNameRegistry;
        this.callbacks = cms.callbacks;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSwitch(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        printf("onSwitch: player connecting");

        ServerConnection server = (ServerConnection) player.getServer();
        if (server == null) return;

        printf("onSwitch: player switching servers");

        ServerInfo targetServer = event.getTarget();
        printf("onSwitch: is target server", "server-name", targetServer.getName());
        if (!targetServersName.contains(targetServer.getName())) return;

        String playerName = player.getName();
        if (playersSwitchingServers.contains(playerName)) {
            playersSwitchingServers.remove(playerName);
            printf("onSwitch: reconnect");
            return;
        }

        server.getCh().getHandle().pipeline().get(HandlerBoss.class).setHandler(
                new AdjustedDownstreamBridge(plugin.getProxy(), (UserConnection) player, server)
        );
        server.disconnect();

        printf("onSwitch: disconnected");

        event.setCancelled(true);

        callbacks.put(playerName, () -> {
           playersSwitchingServers.add(playerName);
           player.connect(targetServer);
           printf("onSwitch: callback: connect");
        });
    }

}
