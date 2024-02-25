package dev.arctic.aiserverassistant.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (alias.equalsIgnoreCase("AiSA")) {
            if (args.length == 1) {
                return Arrays.asList("force_encrypt", "reload");
            }
        } else if (alias.equalsIgnoreCase("ask") && args.length == 1) {
            return new ArrayList<>();
        }
        return null;
    }
}
