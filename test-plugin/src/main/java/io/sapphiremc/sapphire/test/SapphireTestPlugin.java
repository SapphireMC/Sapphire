/*
 * Copyright (c) 2021 DenaryDev
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

import io.papermc.paper.event.player.AsyncChatEvent;
import io.sapphiremc.sapphire.api.event.PacketMessageEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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
public class SapphireTestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        final String prefix = ChatColor.of("#004EFF") + "" + ChatColor.BOLD + this.getDescription().getName() + " " + ChatColor.of("#1F2B44") + "▶ " + ChatColor.of("#D4E1FF");
        this.getServer().getLogger().info(prefix + "Hello World!");

        final SapphireTestPlugin plugin = this;
        this.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                Player player = event.getPlayer();
                player.sendMessage(prefix + "Hello " + player.getName() + ", we are use %server-brand%!");
            }

            @EventHandler
            public void onMessage(PacketMessageEvent event) {
                if (event.getMessageType().equals(PacketMessageEvent.MessageType.SYSTEM)) {
                    String message = event.getMessage();
                    if (message.contains("%server-brand%")) {
                        event.setMessage(message.replace("%server-brand%", Bukkit.getServer().getName()));
                    }
                }
            }

            @EventHandler
            public void onChatMessage(AsyncChatEvent event) {
                plugin.getLogger().info(PlainTextComponentSerializer.plainText().serialize(event.message()));
            }
        }, this);
    }
}
