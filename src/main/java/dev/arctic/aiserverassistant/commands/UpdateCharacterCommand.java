package dev.arctic.aiserverassistant.commands;

import dev.arctic.aiserverassistant.AiServerAssistant;
import dev.arctic.aiserverassistant.utilities.UpdateCharacter;
import org.bukkit.command.CommandSender;

public class UpdateCharacterCommand {

    public void execute(CommandSender sender) {
        if (sender.hasPermission("aisa.admin") || sender.hasPermission("aisa.character")|| sender.isOp()) {
            AiServerAssistant.character = new UpdateCharacter().updateCharacter();
            sender.sendMessage("Character updated!");
        } else {
            sender.sendMessage("You do not have permission to perform this action.");
        }
    }
}
