package dev.arctic.aiserverassistant.utilities;

import dev.arctic.aiserverassistant.character.Character;
import org.bukkit.configuration.file.FileConfiguration;

import static dev.arctic.aiserverassistant.AiServerAssistant.plugin;

public class UpdateCharacter {

    public Character updateCharacter(){

        FileConfiguration config = plugin.getConfig();

        Character updatedChar = new Character(config.get("Character.Name").toString(),
                config.get("Character.Personality").toString(),
                config.get("Character.Language").toString(),
                config.getString("Character.Color").toString(),
                config.getString("Model").toString(),
                config.getInt("Max tokens"),
                config.getDouble("Temperature"));

        return updatedChar;
    }
}
