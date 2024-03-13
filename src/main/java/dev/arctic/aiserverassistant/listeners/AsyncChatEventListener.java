package dev.arctic.aiserverassistant.listeners;

import dev.arctic.aiserverassistant.gpt.GPTRequest;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static dev.arctic.aiserverassistant.AiServerAssistant.character;
import static dev.arctic.aiserverassistant.AiServerAssistant.plugin;


public class AsyncChatEventListener implements Listener {

    @EventHandler
    public void onAsyncChatEvent(AsyncChatEvent event) {

        if (plugin.isChatEnabled() && event.getPlayer().hasPermission("aisa.chat")) {
            String username = event.getPlayer().getName();
            String message = PlainTextComponentSerializer.plainText().serialize(event.message());
            String botName = character.getName();

            if (message.contains("Hey " + botName)) {
                String response = GPTRequest.getGPTRequest(username + message);

                Component gptAnswer = Component.text().content(botName + " » " + response)
                        .color(TextColor.fromHexString(character.getColor())).build();

                Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
                audience.sendMessage(gptAnswer);
            }
        }
    }
}
