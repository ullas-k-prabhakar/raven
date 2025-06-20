import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.services.impl.CompletionServiceImpl;
import io.github.ullas.k.prabhakar.openai.vos.ChatMessage;
import junit.framework.TestCase;
import org.mockito.Mockito;

public class CompletionServiceImplTest extends TestCase {
    public void testCreateChatCompletion() throws Exception {
        OpenAIClient client = Mockito.mock(OpenAIClient.class);
        Mockito.when(client.getGson()).thenReturn(new Gson());
        String json = "{\"choices\":[{\"message\":{\"content\":\"Hello world\"}}]}";
        Mockito.when(client.post(Mockito.eq("/chat/completions"), Mockito.any()))
                .thenReturn(json);

        CompletionServiceImpl service = new CompletionServiceImpl(client);
        List<ChatMessage> messages = Arrays.asList(new ChatMessage("user", "Hi"));
        Map<String, Object> options = Collections.emptyMap();
        String result = service.createChatCompletion("gpt-3", messages, options);
        assertEquals("Hello world", result);
    }
}
