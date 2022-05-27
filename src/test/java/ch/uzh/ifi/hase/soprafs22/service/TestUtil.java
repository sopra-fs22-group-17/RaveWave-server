package ch.uzh.ifi.hase.soprafs22.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;

import se.michaelthelin.spotify.IHttpManager;
import se.michaelthelin.spotify.SpotifyApi;

public class TestUtil {
    private static final String FIXTURE_DIR = "src/test/json/";

    private static String readTestData(String fileName) throws IOException {
        return readFromFile(new File(FIXTURE_DIR, fileName));
    }

    private static String readFromFile(File file) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        StringBuilder out = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            out.append(line);
        }

        in.close();

        return out.toString();
    }

    protected static String readFromFileTry(File file) {
        try {
            return readFromFile(file);
        } catch (IOException e) {
            System.out.println("WHY");
            return null;
        }
    }

    public static class MockedHttpManager {

        public static IHttpManager returningJson(String jsonFixtureFileName) throws Exception {

            // Mocked HTTP Manager to get predictable responses
            final IHttpManager mockedHttpManager = mock(IHttpManager.class);
            String fixture = null;

            if (jsonFixtureFileName != null) {
                fixture = readTestData(jsonFixtureFileName);
            }

            when(mockedHttpManager.get(any(URI.class), (Header[]) any(Header[].class))).thenReturn(fixture);
            when(mockedHttpManager.post(any(URI.class), any(Header[].class), (HttpEntity) any(HttpEntity.class)))
                    .thenReturn(fixture);
            when(mockedHttpManager.put(any(URI.class), any(Header[].class), any(HttpEntity.class))).thenReturn(fixture);
            when(mockedHttpManager.delete(any(URI.class), any(Header[].class), any(HttpEntity.class)))
                    .thenReturn(fixture);

            return mockedHttpManager;
        }
    }

}
