package dev.arctic.aiserverassistant.character;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Character {

    public String name;
    public String personality;
    public String language;
    public String color;
    public String model;
    public int tokenLimit;
    public double temperature;

    public String getCharacterAsJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
