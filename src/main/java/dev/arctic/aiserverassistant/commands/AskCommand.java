package dev.arctic.aiserverassistant.commands;

import dev.arctic.aiserverassistant.AiServerAssistant;
import dev.arctic.aiserverassistant.character.Character;
import dev.arctic.aiserverassistant.gpt.GPTRequest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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

                    Character character = AiServerAssistant.character;

                    Component message = Component.text().content("[DM] " + character.getName() + " » " + response)
                                    .color(TextColor.fromHexString(character.getColor())).build();


                    Bukkit.getScheduler().runTask(plugin, () -> player.sendMessage(message));
                } catch (Exception e) {
                    plugin.getLogger().log(Level.SEVERE, "Error executing GPT request", e);
                }
            });
        } else {
            plugin.getLogger().log(Level.INFO, "Sorry, Console can't do this command");
        }
    }
}
