package dev.arctic.aiserverassistant.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("AiSA")) {
            if (args.length > 0) {
                return switch (args[0].toLowerCase()) {
                    case "force_encrypt" -> handleForceEncryptCommand(sender);
                    case "reload" -> handleReloadCommand(sender);
                    default -> {
                        sender.sendMessage("Unknown AiSA command.");
                        yield false;
                    }
                };
            } else {
                sender.sendMessage("Usage: /AiSA <force_encrypt|reload>");
                return false;
            }
        } else if (label.equalsIgnoreCase("ask")) {
            return handleAskCommand(sender, args);
        }
        return false;
    }

    private boolean handleForceEncryptCommand(CommandSender sender) {
        new ForceEncryptCommand().execute(sender);
        return true;
    }

    private boolean handleReloadCommand(CommandSender sender) {
        new ReloadCommand().execute(sender);
        return true;
    }

    private boolean handleAskCommand(CommandSender sender, String[] args) {
        new AskCommand().execute(sender, args);
        return true;
    }
}
