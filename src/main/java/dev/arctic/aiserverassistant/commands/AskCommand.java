package dev.arctic.aiserverassistant.commands;

import dev.arctic.aiserverassistant.gpt.GPTRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.logging.Level;

import static dev.arctic.aiserverassistant.AiServerAssistant.plugin;

public class AskCommand {
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            StringBuilder result = new StringBuilder();
            for (String arg : args) {
                result.append(arg).append(" ");
            }

            // Run the OpenAI request asynchronously to not block the main thread
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    String response = GPTRequest.getGPTRequest(String.valueOf(result).trim());
                    // Ensure the response is sent back in the main thread
                    Bukkit.getScheduler().runTask(plugin, () -> player.sendMessage("[AiSA] " + response));
                } catch (Exception e) {
                    plugin.getLogger().log(Level.SEVERE, "Error executing GPT request", e);
                }
            });
        } else {
            plugin.getLogger().log(Level.INFO, "Sorry, Console can't do this command");
        }
    }
}
