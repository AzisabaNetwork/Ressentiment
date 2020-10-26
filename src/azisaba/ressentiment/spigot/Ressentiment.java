package azisaba.ressentiment.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class Ressentiment extends JavaPlugin {

    private static Ressentiment instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }

    public static Ressentiment instance() {
        return instance;
    }

}
