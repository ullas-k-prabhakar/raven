import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.services.impl.ImageInputCompletionServiceImpl;
import io.github.ullas.k.prabhakar.openai.vos.ImageContent;
import io.github.ullas.k.prabhakar.openai.vos.ImageInputChatMessage;
import io.github.ullas.k.prabhakar.openai.vos.ImageUrl;
import junit.framework.TestCase;
import org.mockito.Mockito;

public class ImageInputCompletionServiceImplTest extends TestCase {
    public void testCreateImageChatCompletion() throws Exception {
        OpenAIClient client = Mockito.mock(OpenAIClient.class);
        Mockito.when(client.getGson()).thenReturn(new Gson());
        String json = "{\"choices\":[{\"message\":{\"content\":\"Image response\"}}]}";
        Mockito.when(client.post(Mockito.eq("/chat/completions"), Mockito.any()))
                .thenReturn(json);

        ImageInputCompletionServiceImpl service = new ImageInputCompletionServiceImpl(client);
        ImageContent content = new ImageContent("image_url", null, new ImageUrl("http://example.com/img.png"));
        List<ImageInputChatMessage> messages = Arrays.asList(new ImageInputChatMessage("user", Arrays.asList(content)));
        Map<String, Object> options = Collections.emptyMap();
        String result = service.createImageChatCompletion("gpt-4", messages, options);
        assertEquals("Image response", result);
    }
}
