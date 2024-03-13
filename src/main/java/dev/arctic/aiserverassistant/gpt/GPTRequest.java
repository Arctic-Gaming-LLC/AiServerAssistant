package dev.arctic.aiserverassistant.gpt;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import dev.arctic.aiserverassistant.AiServerAssistant;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static dev.arctic.aiserverassistant.AiServerAssistant.plugin;

public class GPTRequest {

    public static String getGPTRequest(String question){

        OpenAiService service = new OpenAiService(plugin.getAPI_KEY(), Duration.ofSeconds(30));

        plugin.getLogger().log(Level.INFO,"Creating completion...");

        String gamemode = plugin.getConfig().getString("Features.gamemode");
        String plugins = plugin.getConfig().getString(String.join(", ", "Features.plugins"));
        String notes = plugin.getConfig().getString("Features.notes");

        List<ChatMessage> messages = getChatMessages(question);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(AiServerAssistant.getCharacter().getModel())
                .n(1)
                .maxTokens(AiServerAssistant.getCharacter().getTokenLimit())
                .temperature(AiServerAssistant.getCharacter().getTemperature())
                .messages(messages)
                .build();

        List<ChatCompletionChoice> results = service.createChatCompletion(chatCompletionRequest).getChoices();
        for(ChatCompletionChoice request : results){
            return request.getMessage().getContent();
        }

        service.shutdownExecutor();

        return "No response from OpenAI";
    }

    private static List<ChatMessage> getChatMessages(String question) {

        String prompt = plugin.getPrompt();
        String character = plugin.getCharacter().getCharacterAsJSON();
        String plugins = plugin.getConfig().getString("Features.plugins");
        String gamemode = plugin.getConfig().getString("Features.gamemode");
        String notes = plugin.getConfig().getString("Features.notes");
        String divider = " » ";


        ChatMessage message = new ChatMessage("user",
                prompt + divider +
                character+ divider +
                question + divider +
                plugins+ divider +
                gamemode+ divider +
                notes + "User Inquiry: " +
                        question);

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(message);
        return messages;
    }
}
