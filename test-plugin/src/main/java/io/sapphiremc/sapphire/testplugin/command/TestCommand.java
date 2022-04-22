/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.command;

import io.sapphiremc.sapphire.testplugin.SapphireTestPlugin;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTestsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCommand implements CommandExecutor, TabCompleter {

    private final SapphireTestPlugin plugin;

    public TestCommand(SapphireTestPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) return false;

        switch (args[0]) {
            case "nbt" -> {
                NBTTestsManager testsManager = SapphireTestPlugin.getInstance().getTestsManager();
                testsManager.runTests();
                if (testsManager.isSuccess()) {
                    plugin.getLogger().info("All tests passed!");
                    sender.sendMessage(plugin.getPrefix() + "All nbt tests passed, check console for more details!");
                } else {
                    plugin.getLogger().warning("Some tests didn't passed!");
                    sender.sendMessage(plugin.getPrefix() + "Some nbt tests didn't passed, check console for more details!");
                }
            }
            case "chromium" -> {
                if (sender instanceof Player player) {
                    if (player.usesChromiumClient()) {
                        player.sendMessage(plugin.getPrefix() + "You are using chromium!");
                    } else {
                        player.sendMessage(plugin.getPrefix() + "You are using vanilla minecraft!");
                    }
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> availableArgs = new ArrayList<>();

        if (args.length == 1) {
            if (args[0].isEmpty()) {
                availableArgs.add("nbt");
                availableArgs.add("chromium");
            } else {
                String arg0 = args[0];
                if ("nbt".startsWith(arg0)) availableArgs.add("nbt");
                if ("chromium".startsWith(arg0)) availableArgs.add("chromium");
            }
        }

        if (availableArgs.size() > 0) return availableArgs;
        return Collections.emptyList();
    }
}
