package com.gregspitz.flashcardserver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.model.Flashcard;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the implementation of {@link FlashcardServer}
 */
public class FlashcardServerTest {

    private static final Gson gson = new Gson();
    private static final String BASE_URL = "http://localhost:8080";

    private static final Flashcard FLASHCARD_1 =
            new Flashcard("0", "Front", "Back");

    private static final Flashcard FLASHCARD_2 =
            new Flashcard("1", "Front 2", "Back again");

    @Mock
    private FlashcardRepository mockRepository;

    @Captor
    private ArgumentCaptor<Flashcard> saveFlashcardCaptor;

    private FlashcardServer flashcardServer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(mockRepository.getFlashcards()).thenReturn(Arrays.asList(FLASHCARD_1, FLASHCARD_2));
        when(mockRepository.getFlashcardById(FLASHCARD_1.getId())).thenReturn(FLASHCARD_1);
        when(mockRepository.getFlashcardById(FLASHCARD_2.getId())).thenReturn(FLASHCARD_2);
        flashcardServer = new FlashcardServer(8080, mockRepository);
    }

    @After
    public void breakdown() {
        flashcardServer.stop();
    }

    @Test
    public void getRequestForFlashcards_respondsWithListOfFlashcards() {
        String responseJson = runClientWithGetRequestAndReturnResponse("/flashcards/");
        Type listType = new TypeToken<List<Flashcard>>() {}.getType();
        List<Flashcard> responseFlashcards = gson.fromJson(responseJson, listType);
        assertEquals(FLASHCARD_1, responseFlashcards.get(0));
        assertEquals(FLASHCARD_2, responseFlashcards.get(1));
    }

    @Test
    public void getRequestWithIncludedId_stillRespondsWithAllFlashcards() {
        String responseJson = runClientWithGetRequestAndReturnResponse("/flashcards/" + FLASHCARD_1.getId());
        Type listType = new TypeToken<List<Flashcard>>() {}.getType();
        List<Flashcard> responseFlashcards = gson.fromJson(responseJson, listType);
        assertEquals(2, responseFlashcards.size());
    }

    @Test
    public void getRequestForFlashcardWithId_respondsWithCorrectFlashcard() {
        String response = runClientWithGetRequestAndReturnResponse("/flashcard/" + FLASHCARD_1.getId());
        Flashcard responseFlashcard = gson.fromJson(response, Flashcard.class);
        assertEquals(FLASHCARD_1, responseFlashcard);
    }

    @Test
    public void postRequestForFlashcard_addsFlashcardToRepoAndRespondsWithFlashcard() {
        Flashcard newFlashcard = new Flashcard("2", "A front", "A back");
        String response = runClientWithPostRequestAndReturnResponse("/flashcard/", newFlashcard);
        verify(mockRepository).addFlashcard(saveFlashcardCaptor.capture());
        assertEquals(newFlashcard, saveFlashcardCaptor.getValue());
        assertEquals(newFlashcard, gson.fromJson(response, Flashcard.class));
    }

    private String runClientWithPostRequestAndReturnResponse(String path, Flashcard flashcard) {
        String url = BASE_URL + path;
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        String flashcardJson = gson.toJson(flashcard);
        method.setRequestBody(flashcardJson);
        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        String response = null;
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();

            response = new String(responseBody);

        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
        return response;
    }

    private String runClientWithGetRequestAndReturnResponse(String path) {
        String url = BASE_URL + path;
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        String response = null;
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();

            response = new String(responseBody);

        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
        return response;
    }
}
