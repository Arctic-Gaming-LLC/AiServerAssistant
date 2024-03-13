package dev.arctic.aiserverassistant.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("AiSA")) {
            if (args.length > 0) {
                return switch (args[0].toLowerCase()) {
                    case "force_encrypt" -> {
                        try {
                            yield handleForceEncryptCommand(sender);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "reload" -> {
                        try {
                            yield handleReloadCommand(sender);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "update_character" -> {
                        sender.sendMessage("Updating character...");
                        yield handleUpdateCharacterCommand(sender);
                    }
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

    private boolean handleForceEncryptCommand(CommandSender sender) throws IOException {
        new ForceEncryptCommand().execute(sender);
        return true;
    }

    private boolean handleReloadCommand(CommandSender sender) throws IOException {
        new ReloadCommand().execute(sender);
        return true;
    }

    private boolean handleAskCommand(CommandSender sender, String[] args) {
        new AskCommand().execute(sender, args);
        return true;
    }

    private boolean handleUpdateCharacterCommand(@NotNull CommandSender sender) {
        new UpdateCharacterCommand().execute(sender);
        return true;
    }
}
