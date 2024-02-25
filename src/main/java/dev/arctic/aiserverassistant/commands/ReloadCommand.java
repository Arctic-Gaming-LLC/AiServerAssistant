package dev.arctic.aiserverassistant.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.logging.Level;

import static dev.arctic.aiserverassistant.AiServerAssistant.plugin;

public class ReloadCommand {
    public void execute(CommandSender sender) throws IOException {

        boolean executable = false;

        if (sender instanceof Player){
            if(sender.hasPermission("aisa.admin") || sender.isOp()){
                executable = true;
            }
        } else {
            executable = true;
        }

        if (executable){
            plugin.updateKeys();

            plugin.getLogger().log(Level.WARNING, "[AiSA] Config Reloaded!");
            if(sender instanceof Player){
                sender.sendMessage("[AiSA] Config Reloaded!");
            }
        }
    }
}
