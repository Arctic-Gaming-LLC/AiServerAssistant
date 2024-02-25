package dev.arctic.aiserverassistant.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {
    private String apiKey;
    private String organization;
    private boolean encrypted;
    private String model;
    private int maxTokens;
    private String personality;
    private Features features;

    public Config(String apiKey, String organization, boolean encrypted, String model, int maxTokens, String personality, Features features) {
        this.apiKey = apiKey;
        this.organization = organization;
        this.encrypted = encrypted;
        this.model = model;
        this.maxTokens = maxTokens;
        this.personality = personality;
        this.features = features != null ? features : new Features(new String[0], "", "");
    }

    @Getter
    @Setter
    public static class Features {
        private String[] plugins;
        private String gamemode;
        private String notes;

        public Features(String[] plugins, String gamemode, String notes) {
            this.plugins = plugins;
            this.gamemode = gamemode;
            this.notes = notes;
        }
    }
}
