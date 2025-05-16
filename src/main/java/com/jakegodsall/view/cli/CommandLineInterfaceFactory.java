package com.jakegodsall.view.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClients;
import com.jakegodsall.config.impl.ApiKeyConfigImpl;
import com.jakegodsall.services.flashcard.FlashcardService;
import com.jakegodsall.services.flashcard.impl.FlashcardServiceGPTImpl;
import com.jakegodsall.services.http.impl.HttpClientServiceGPTImpl;
import com.jakegodsall.services.json.impl.JsonParseServiceGPTImpl;
import com.jakegodsall.services.prompt.impl.PromptServiceGPTImpl;

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

