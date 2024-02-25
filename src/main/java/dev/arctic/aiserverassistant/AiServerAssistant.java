package dev.arctic.aiserverassistant;

import dev.arctic.aiserverassistant.commands.CommandManager;
import dev.arctic.aiserverassistant.commands.CommandTabCompleter;
import dev.arctic.aiserverassistant.utilities.Config;
import dev.arctic.aiserverassistant.utilities.ConfigManager;
import dev.arctic.aiserverassistant.utilities.Encryption;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public final class AiServerAssistant extends JavaPlugin {

    @Getter @Setter String API_KEY;
    @Getter @Setter String ORG_KEY;
    @Getter public static AiServerAssistant plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Plugin startup logic
        if (!new File(getDataFolder().getAbsolutePath(), "config.yml").exists()) {
            saveDefaultConfig();
        } else {
            Config config = ConfigManager.createConfigObject();
            try {
                ConfigManager.updateEncryptedConfig(config);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        plugin.getLogger().log(Level.WARNING, "[AiSA] Config Loaded!");

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
        if(getConfig().getBoolean("Encrypted")){
            setAPI_KEY(Encryption.decryptKey(getConfig().getString("API_KEY")));
            setORG_KEY(Encryption.decryptKey(getConfig().getString("Organization")));
        } else {
            plugin.getLogger().log(Level.SEVERE, "[AiSA] KEYS NOT ENCRYPTED, DOING THAT NOW!");
            Config config = ConfigManager.createConfigObject();
            ConfigManager.saveEncryptedConfig(config);
            plugin.getLogger().log(Level.SEVERE, "[AiSA] Keys Encrypted - VERIFY IN CONFIG.YML");
        }
        plugin.getLogger().log(Level.WARNING, "[AiSA] Keys Loaded!");
    }
}

