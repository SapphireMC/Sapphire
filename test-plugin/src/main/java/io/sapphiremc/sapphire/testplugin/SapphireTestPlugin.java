package io.sapphiremc.sapphire.testplugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SapphireTestPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("Hello world!");
        getServer().getPluginManager().registerEvents(this, this);
    }
}
