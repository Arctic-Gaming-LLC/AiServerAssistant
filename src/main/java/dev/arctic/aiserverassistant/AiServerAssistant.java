package dev.arctic.aiserverassistant;

import dev.arctic.aiserverassistant.character.Character;
import dev.arctic.aiserverassistant.commands.CommandManager;
import dev.arctic.aiserverassistant.commands.CommandTabCompleter;
import dev.arctic.aiserverassistant.listeners.AsyncChatEventListener;
import dev.arctic.aiserverassistant.utilities.Encryption;
import dev.arctic.aiserverassistant.utilities.UpdateCharacter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public final class AiServerAssistant extends JavaPlugin {

    @Getter @Setter String API_KEY;
    @Getter public static AiServerAssistant plugin;
    @Getter public static Character character;
    @Getter private boolean chatEnabled;
    @Getter private String prompt;

    @Override
    public void onEnable() {
        plugin = this;

        loadFiles();
        plugin.getLogger().log(Level.WARNING, "[AiSA] Config Loaded!");

        character = new UpdateCharacter().updateCharacter();
        chatEnabled = getConfig().getBoolean("Respond In Chat");

        getServer().getPluginManager().registerEvents(new AsyncChatEventListener(), this);


        //Get and Set our API keys and Org
        try {
            updateKeys();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Objects.requireNonNull(getCommand("aisa")).setExecutor(new CommandManager());
        Objects.requireNonNull(getCommand("ask")).setExecutor(new CommandManager());
        Objects.requireNonNull(getCommand("aisa")).setTabCompleter(new CommandTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        plugin.getLogger().log(Level.WARNING, "[AiSA] Shutting Down!");

    }

    public void updateKeys() throws IOException {
        File keysFile = new File(getDataFolder().getAbsolutePath(), "keys.yml");
        YamlConfiguration keysConfig = YamlConfiguration.loadConfiguration(keysFile);
        boolean encrypted = keysConfig.getBoolean("encrypted");
        if (encrypted) {
            String encryptedKey = keysConfig.getString("api");
            API_KEY = Encryption.decryptKey(encryptedKey);
        } else {
            throw new IOException("API Key is not encrypted!");
        }
    }

    public void loadFiles() {
        // Check and create config.yml if it doesn't exist, or ensure defaults are set if it does
        File configFile = new File(getDataFolder().getAbsolutePath(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        } else {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        // Check and create prompt.txt if it doesn't exist
        File promptFile = new File(getDataFolder().getAbsolutePath(), "prompt.txt");
        if (!promptFile.exists()) {
            saveResource("prompt.txt", false);
        } else {
            // Read the content of prompt.txt and update the prompt variable
            StringBuilder promptContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(promptFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    promptContent.append(line).append("\n");
                }
                prompt = promptContent.toString().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Check and create keys.yml if it doesn't exist, or update it if it does
        File keysFile = new File(getDataFolder().getAbsolutePath(), "keys.yml");
        if (!keysFile.exists()) {
            saveResource("keys.yml", false);
            PluginManager pluginManager = getServer().getPluginManager();
            plugin.getLogger().log(Level.SEVERE, "API KEY NOT FOUND! CREATING KEYS.YML NOW!" +
                    "\nPlease update the API key in keys.yml, then restart the server." +
                    "\nSee config.yml for Setup Instructions.");
            pluginManager.disablePlugin(this);
        } else {
            YamlConfiguration keysConfig = YamlConfiguration.loadConfiguration(keysFile);
            boolean encrypted = keysConfig.getBoolean("encrypted");
            if (!encrypted) {
                String key = keysConfig.getString("api");
                String encryptedKey = Encryption.encryptKey(key); // Assuming this method returns the encrypted key
                keysConfig.set("api", encryptedKey);
                keysConfig.set("encrypted", true);
                try {
                    keysConfig.save(keysFile);
                } catch (IOException e) {
                    e.printStackTrace(); // Handle exceptions properly
                }
            }
        }
    }

}

