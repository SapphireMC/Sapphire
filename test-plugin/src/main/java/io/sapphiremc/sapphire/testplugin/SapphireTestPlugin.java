/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin;

import io.sapphiremc.sapphire.api.event.PacketMessageEvent;
import io.sapphiremc.sapphire.testplugin.command.TestCommand;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTestsManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SapphireTestPlugin extends JavaPlugin {

    private static SapphireTestPlugin instance;

    public static SapphireTestPlugin getInstance() {
        return instance;
    }

    @Getter
    private NBTTestsManager testsManager;

    @Getter
    private final String prefix = ChatColor.of("#004EFF") + "" + ChatColor.BOLD + this.getDescription().getName() + " " + ChatColor.of("#1F2B44") + "▶ " + ChatColor.of("#D4E1FF");

    @Override
    public void onLoad() {
        instance = this;
        this.testsManager = new NBTTestsManager(this);
        testsManager.loadTests();
    }

    @Override
    public void onEnable() {
        final SapphireTestPlugin plugin = this;
        this.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                Player player = event.getPlayer();
                if (player.usesChromiumClient()) {
                    plugin.getLogger().info("Player " + player.getName() + " uses sapphire client");
                    player.sendMessage(prefix + " You are using sapphire client!");
                }
                player.sendMessage(prefix + "Hello " + player.getName() + ", we are use %server-brand%!");
            }

            @EventHandler
            public void onMessage(PacketMessageEvent event) {
                if (event.getMessageType().equals(PacketMessageEvent.MessageType.SYSTEM)) {
                    String message = event.getMessage();
                    if (message.contains("%server-brand%")) {
                        message = message.replace("%server-brand%", Bukkit.getServer().getName());
                        event.setMessage(message);
                    }
                }
            }
        }, this);

        this.getCommand("tests").setExecutor(new TestCommand(this));

        this.getServer().getLogger().info(prefix + "Sapphire test plugin successfully enabled!");
    }
}
