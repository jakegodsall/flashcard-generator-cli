package org.jakegodsall.view.cli;

import org.jakegodsall.config.ApiKeyConfig;
import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.exceptions.ApiKeyNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ApiKeyHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setOut(originalErr);
    }


    @Test
    void handle_apiKeyFound() throws Exception {
        // Mock the ApiKeyConfig
        ApiKeyConfig apiKeyConfigMock = mock(ApiKeyConfig.class);

        // Mock the ApiKeyConfig.getApiKeyFromJsonFile method
        given(apiKeyConfigMock.getApiKeyFromFile(anyString()))
                .willReturn("validApiKey");

        // Create the ApiKeyHandler using the ApiKeyConfig mock as a dependency
        ApiKeyHandler apiKeyHandler = new ApiKeyHandler(apiKeyConfigMock);

        // Prepare the BufferedReader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // Call the method
        apiKeyHandler.handle(bufferedReader);

        // verify the results
        assertThat(outContent.toString()).isEqualTo("API Key Found: validApiKey\n");
    }

    @Test
    void handle_apiKeyNotFound() throws Exception {
        // Mock the ApiKeyConfig
        ApiKeyConfig apiKeyConfigMock = mock(ApiKeyConfig.class);

        // Set up the ApiKeyConfig.getApiKeyFromJsonFile mock
        given(apiKeyConfigMock.getApiKeyFromFile(anyString()))
                .willThrow(new ApiKeyNotFoundException("API key not found"));

        // Do nothing when storeApiKeyInJsonFile called
        doNothing().when(apiKeyConfigMock).storeApiKeyInFile(anyString(), anyString());

        // Prepare the BufferedReader
        String input = "newApiKey\n";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));

        // Create the ApiKeyHandler with mocked ApiKeyConfig
        ApiKeyHandler apiKeyHandler = new ApiKeyHandler(apiKeyConfigMock);

        // Call the handle method
        apiKeyHandler.handle(bufferedReader);

        // Verify the error output and prompt interaction
        verify(apiKeyConfigMock).getApiKeyFromFile(ApiKeyConfigImpl.CONFIG_DIR);
        verify(apiKeyConfigMock).storeApiKeyInFile("newApiKey", ApiKeyConfigImpl.CONFIG_DIR);
    }

    @Test
    void promptUserForApiKey_whitespaceRemoved() throws Exception {
        String inputString = "testapikey     ";
        InputStream input = new ByteArrayInputStream(inputString.getBytes());

        System.setIn(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String result = ApiKeyHandler.promptUserForApiKey(br);

        assertThat(result).isEqualTo("testapikey");
    }
}