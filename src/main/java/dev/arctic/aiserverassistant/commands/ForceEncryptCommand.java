package dev.arctic.aiserverassistant.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.logging.Level;
import static dev.arctic.aiserverassistant.AiServerAssistant.plugin;

public class ForceEncryptCommand {
    public void execute(CommandSender sender) throws IOException {
        boolean executable = false;

        if (sender instanceof Player) {
            if (sender.hasPermission("aisa.admin") || sender.isOp()) {
                executable = true;
            }
        } else {
            executable = true;
        }

        if (executable) {
            plugin.getConfig().set("Encrypted", false);
            plugin.saveConfig();
            plugin.saveDefaultConfig();
            plugin.updateKeys();

            plugin.getLogger().log(Level.INFO, "[AiSA] Encryption refreshed and configuration reloaded!");
            if (sender instanceof Player) {
                sender.sendMessage("[AiSA] Encryption refreshed and configuration reloaded!");
            }
        } else {
            sender.sendMessage("[AiSA] You do not have permission to perform this action.");
        }
    }
}
