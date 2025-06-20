import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.google.gson.Gson;

import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.services.impl.SpeechToTextServiceImpl;
import junit.framework.TestCase;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.mockito.Mockito;

public class SpeechToTextServiceImplTest extends TestCase {
    public void testTranscribeAudio() throws Exception {
        OpenAIClient client = Mockito.mock(OpenAIClient.class);
        OkHttpClient httpClient = Mockito.mock(OkHttpClient.class);
        Call call = Mockito.mock(Call.class);

        Mockito.when(client.getBaseUrl()).thenReturn("http://localhost");
        Mockito.when(client.getApiKey()).thenReturn("KEY");
        Mockito.when(client.getHttpClient()).thenReturn(httpClient);
        Mockito.when(client.getGson()).thenReturn(new Gson());
        Mockito.when(httpClient.newCall(Mockito.any(Request.class))).thenReturn(call);

        Response response = new Response.Builder()
                .request(new Request.Builder().url("http://localhost").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create("{\"text\":\"done\"}", MediaType.parse("application/json")))
                .build();
        Mockito.when(call.execute()).thenReturn(response);

        SpeechToTextServiceImpl service = new SpeechToTextServiceImpl(client);
        Map<String, Object> options = Collections.emptyMap();
        String result = service.transcribeAudio("whisper-1", new byte[]{1,2}, options);
        assertEquals("done", result);
    }
}
