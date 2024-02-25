package dev.arctic.aiserverassistant.utilities;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static dev.arctic.aiserverassistant.AiServerAssistant.plugin;

public class ConfigManager {

    public static Config createConfigObject() {
        FileConfiguration config = plugin.getConfig();

        String apiKey = config.getString("API_KEY");
        String organization = config.getString("Organization");
        boolean encrypted = config.getBoolean("Encrypted");
        String model = config.getString("Model");
        int maxTokens = config.getInt("Max tokens");
        String personality = config.getString("Personality");

        // Assuming plugins are listed as a list of strings in the format "plugin: description"
        List<String> pluginList = config.getStringList("Features.plugins");
        String[] plugins = pluginList.toArray(new String[0]);
        String gamemode = config.getString("Features.gamemode");
        String notes = config.getString("Features.notes");

        Config.Features features = new Config.Features(plugins, gamemode, notes);

        return new Config(apiKey, organization, encrypted, model, maxTokens, personality, features);
    }

    public static void saveEncryptedConfig(Config config) throws IOException {
        String configContent = "#▬▬▬▬| Config \\▬▬▬▬\n" +
                "#\n" +
                "# !!! Important Links !!!\n" +
                "#\n" +
                "# OpenAI Pricing (this is not free!)\n" +
                "# https://openai.com/pricing#language-models\n" +
                "# (gpt 3 models are the cheapest, and will work well enough!)\n" +
                "#\n" +
                "# NOTICE!!!!!!!!!!!!!!!!!!!!\n" +
                "# ASA has a very shaky form of encryption. While the default encryption is\n" +
                "# not on GitHub, there's nothing stopping somebody from decompiling. If you\n" +
                "# are compiling yourself, you should consider programming your own form of\n" +
                "# encryption, or use https request to a private webserver (most secure).\n" +
                "#\n" +
                "# API Key\n" +
                "API_KEY: \"" + Encryption.encryptKey(config.getApiKey()) + "\"\n" +
                "#\n" +
                "# Organization Key\n" +
                "Organization: \"" + Encryption.encryptKey(config.getOrganization()) + "\"\n" +
                "#\n" +
                "# DO NOT CHANGE THIS VALUE!\n" +
                "Encrypted: true \n" +
                "#\n" +
                "# GPT Model\n" +
                "Model: \"" + config.getModel() + "\"\n" +
                "#\n" +
                "# Max Tokens\n" +
                "Max tokens: " + config.getMaxTokens() + "\n" +
                "#\n" +
                "#▬▬▬▬| Customization \\▬▬▬▬\n" +
                "#\n" +
                "# Personality/Persona\n" +
                "Personality: \"" + config.getPersonality() + "\"\n" +
                "#\n" +
                "# Features\n" +
                "Features:\n" +
                "  plugins:\n";
        for (String plugin : config.getFeatures().getPlugins()) {
            configContent += "    - \"" + plugin + "\"\n";
        }
        configContent += "  gamemode: \"" + config.getFeatures().getGamemode() + "\"\n" +
                "  notes: \"" + config.getFeatures().getNotes() + "\"\n";

        File configFile = new File(plugin.getDataFolder(), "config.yml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            writer.write(configContent);
        }
    }

    public static void updateEncryptedConfig(Config config) throws IOException {
        String configContent = "#▬▬▬▬| Config \\▬▬▬▬\n" +
                "#\n" +
                "# !!! Important Links !!!\n" +
                "#\n" +
                "# OpenAI Pricing (this is not free!)\n" +
                "# https://openai.com/pricing#language-models\n" +
                "# (gpt 3 models are the cheapest, and will work well enough!)\n" +
                "#\n" +
                "# NOTICE!!!!!!!!!!!!!!!!!!!!\n" +
                "# ASA has a very shaky form of encryption. While the default encryption is\n" +
                "# not on GitHub, there's nothing stopping somebody from decompiling. If you\n" +
                "# are compiling yourself, you should consider programming your own form of\n" +
                "# encryption, or use https request to a private webserver (most secure).\n" +
                "#\n" +
                "# API Key\n" +
                "API_KEY: \"" + config.getApiKey() + "\"\n" +
                "#\n" +
                "# Organization Key\n" +
                "Organization: \"" + config.getOrganization() + "\"\n" +
                "#\n" +
                "# DO NOT CHANGE THIS VALUE!\n" +
                "Encrypted: true \n" +
                "#\n" +
                "# GPT Model\n" +
                "Model: \"" + config.getModel() + "\"\n" +
                "#\n" +
                "# Max Tokens\n" +
                "Max tokens: " + config.getMaxTokens() + "\n" +
                "#\n" +
                "#▬▬▬▬| Customization \\▬▬▬▬\n" +
                "#\n" +
                "# Personality/Persona\n" +
                "Personality: \"" + config.getPersonality() + "\"\n" +
                "#\n" +
                "# Features\n" +
                "Features:\n" +
                "  plugins:\n";
        for (String plugin : config.getFeatures().getPlugins()) {
            configContent += "    - \"" + plugin + "\"\n";
        }
        configContent += "  gamemode: \"" + config.getFeatures().getGamemode() + "\"\n" +
                "  notes: \"" + config.getFeatures().getNotes() + "\"\n";

        File configFile = new File(plugin.getDataFolder(), "config.yml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            writer.write(configContent);
        }
    }
}