package dev.arctic.aiserverassistant;

import dev.arctic.aiserverassistant.commands.CommandManager;
import dev.arctic.aiserverassistant.commands.CommandTabCompleter;
import dev.arctic.aiserverassistant.utilities.Encryption;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

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
        getConfig();
        saveDefaultConfig();
        updateConfigWithNewOptions();
        plugin.getLogger().log(Level.WARNING, "[AiSA] Config Loaded!");

        //Get and Set our API keys and Org
        updateKeys();

        Objects.requireNonNull(getCommand("aisa")).setExecutor(new CommandManager());
        Objects.requireNonNull(getCommand("ask")).setExecutor(new CommandManager());
        Objects.requireNonNull(getCommand("aisa")).setTabCompleter(new CommandTabCompleter());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        plugin.getLogger().log(Level.WARNING, "[AiSA] Shutting Down!");

    }

    private void updateConfigWithNewOptions() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }

    public void updateKeys(){
        if(getConfig().getBoolean("Encrypted")){
            setAPI_KEY(Encryption.decryptKey(Objects.requireNonNull(getConfig().getString("API_KEY"))));
            setORG_KEY(Encryption.decryptKey(Objects.requireNonNull(getConfig().getString("Organization"))));
        } else {
            plugin.getLogger().log(Level.SEVERE, "[AiSA] KEYS NOT ENCRYPTED, DOING THAT NOW!");
            setAPI_KEY(getConfig().getString("API_KEY"));
            setORG_KEY(getConfig().getString("Organization"));
            String encryption = Encryption.encryptKey(getAPI_KEY());
            String encryptedOrg = Encryption.encryptKey(getORG_KEY());
            getConfig().set("API_KEY", encryption);
            getConfig().set("Organization", encryptedOrg);
            getConfig().set("Encrypted", true);
            saveConfig();
            saveDefaultConfig();
            plugin.getLogger().log(Level.SEVERE, "[AiSA] Keys Encrypted - VERIFY IN CONFIG.YML");
        }
        plugin.getLogger().log(Level.WARNING, "[AiSA] Keys Loaded!");
    }
}

