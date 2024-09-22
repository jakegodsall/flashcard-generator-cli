package org.jakegodsall.view.cli;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.config.ApiKeyConfig;
import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.exceptions.ApiKeyNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
public class ApiKeyHandler {
    private final ApiKeyConfig apiKeyConfig;

    public void handle(BufferedReader bufferedReader) {
        try {
            String api = apiKeyConfig.getApiKeyFromFile(ApiKeyConfigImpl.CONFIG_DIR);
        } catch (ApiKeyNotFoundException | IOException exception) {
            System.err.println(exception.getMessage());
            try {
                String newApiKey = promptUserForApiKey(bufferedReader);
                apiKeyConfig.storeApiKeyInFile(newApiKey, ApiKeyConfigImpl.CONFIG_DIR);
            } catch (IOException ioException) {
                System.err.println(ioException.getMessage());
            }
        }
    }

    public static String promptUserForApiKey(BufferedReader bufferedReader) throws IOException {
        System.out.println("Please enter your API key: ");
        return bufferedReader.readLine().trim();
    }
}
