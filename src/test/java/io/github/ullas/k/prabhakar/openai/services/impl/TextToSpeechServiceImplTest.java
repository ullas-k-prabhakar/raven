import java.util.Collections;
import java.util.Map;

import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.services.impl.TextToSpeechServiceImpl;
import junit.framework.TestCase;
import org.mockito.Mockito;

public class TextToSpeechServiceImplTest extends TestCase {
    public void testSynthesizeSpeech() throws Exception {
        OpenAIClient client = Mockito.mock(OpenAIClient.class);
        byte[] expected = new byte[] {1, 2, 3};
        Mockito.when(client.postForBinary(Mockito.eq("/audio/speech"), Mockito.any()))
                .thenReturn(expected);

        TextToSpeechServiceImpl service = new TextToSpeechServiceImpl(client);
        Map<String, Object> options = Collections.emptyMap();
        byte[] result = service.synthesizeSpeech("tts-1", "Hello", options);
        assertNotNull(result);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }
}
