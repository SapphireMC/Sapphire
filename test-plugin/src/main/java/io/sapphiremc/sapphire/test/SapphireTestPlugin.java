/*
 * Copyright (c) 2022 DenaryDev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package io.sapphiremc.sapphire.test;

import io.sapphiremc.sapphire.api.event.PacketMessageEvent;
import io.sapphiremc.sapphire.test.nbt.NBTTestsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created 01.01.2022
 *
 * @author DenaryDev
 */
public class SapphireTestPlugin extends JavaPlugin {

    private static SapphireTestPlugin instance;

    public static SapphireTestPlugin getInstance() {
        return instance;
    }

    private NBTTestsManager testsManager;

    @Override
    public void onLoad() {
        instance = this;
        this.testsManager = new NBTTestsManager(this);
        testsManager.loadTests();
    }

    @Override
    public void onEnable() {
        final String prefix = ChatColor.of("#004EFF") + "" + ChatColor.BOLD + this.getDescription().getName() + " " + ChatColor.of("#1F2B44") + "▶ " + ChatColor.of("#D4E1FF");

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

        this.getCommand("testnbt").setExecutor((sender, command, label, args) -> {
            testsManager.runTests();
            if (testsManager.isSuccess()) {
                getLogger().info("§aAll tests passed!");
                sender.sendMessage(prefix + "All nbt tests passed, check console for more details!");
            } else {
                getLogger().warning("Some tests didn't passed!");
                sender.sendMessage(prefix + "Some nbt tests didn't passed, check console for more details!");
            }

            return false;
        });

        this.getServer().getLogger().info(prefix + "Sapphire test plugin successfully enabled!");
    }
}
