package org.jakegodsall.view.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClients;
import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.impl.FlashcardServiceGPTImpl;
import org.jakegodsall.services.impl.HttpClientServiceGPTImpl;
import org.jakegodsall.services.impl.JsonParseServiceGPTImpl;
import org.jakegodsall.services.impl.PromptServiceGPTImpl;

public class CommandLineInterfaceFactory {
    public static CommandLineInterface createGPTCommandLineInterface() {
        // Instantiate GPT FlashcardService
        FlashcardService flashcardService = new FlashcardServiceGPTImpl(
                new HttpClientServiceGPTImpl(HttpClients.createDefault()),
                new JsonParseServiceGPTImpl(new ObjectMapper()),
                new PromptServiceGPTImpl()
        );
        // Instantiate ApiKeyHandler
        ApiKeyHandler apiKeyHandler = new ApiKeyHandler(new ApiKeyConfigImpl());
        // Instantiate CommandLineInterface and return
        return new CommandLineInterface(flashcardService, apiKeyHandler);
    }
}

