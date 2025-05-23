package io.github.ullas.k.prabhakar.openai;

import java.io.IOException;

import com.google.gson.Gson;

import io.github.ullas.k.prabhakar.openai.services.CompletionService;
import io.github.ullas.k.prabhakar.openai.services.ImageInputCompletionService;
import io.github.ullas.k.prabhakar.openai.services.SpeechToTextService;
import io.github.ullas.k.prabhakar.openai.services.TextToSpeechService;
import io.github.ullas.k.prabhakar.openai.services.impl.CompletionServiceImpl;
import io.github.ullas.k.prabhakar.openai.services.impl.ImageInputCompletionServiceImpl;
import io.github.ullas.k.prabhakar.openai.services.impl.SpeechToTextServiceImpl;
import io.github.ullas.k.prabhakar.openai.services.impl.TextToSpeechServiceImpl;
import io.github.ullas.k.prabhakar.openai.util.RavenUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAIClient {
	private final String apiKey;
	private String baseUrl = "https://api.openai.com/v1";
	protected final OkHttpClient httpClient;
	protected final Gson gson;

	public OpenAIClient(String apiKey) {
		this.apiKey = apiKey;
		this.httpClient = new OkHttpClient();
		this.gson = new Gson();
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public OkHttpClient getHttpClient() {
		return httpClient;
	}

	public Gson getGson() {
		return gson;
	}

	public static Builder builder() {
		return new Builder();
	}

	public CompletionService completion() {
		return new CompletionServiceImpl(this);
	}

	public ImageInputCompletionService createImageChatCompletion() {
		return new ImageInputCompletionServiceImpl(this);
	}

	public TextToSpeechService textToSpeech() {
		return new TextToSpeechServiceImpl(this);
	}

	public SpeechToTextService speechToText() {
		return new SpeechToTextServiceImpl(this);
	}

	public String post(String endpoint, Object body) throws IOException {
		String json = gson.toJson(body);
		Request request = new Request.Builder().url(baseUrl + endpoint).addHeader("Authorization", "Bearer " + apiKey)
				.addHeader("Content-Type", "application/json")
				.post(RequestBody.create(json, MediaType.parse("application/json"))).build();
		try (Response response = httpClient.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			return response.body().string();
		}
	}

	public byte[] postForBinary(String endpoint, Object body) throws IOException {
	    String json = gson.toJson(body);

	    Request request = new Request.Builder()
	            .url(baseUrl + endpoint)
	            .addHeader("Authorization", "Bearer " + apiKey)
	            .addHeader("Content-Type", "application/json")
	            .post(RequestBody.create(json, MediaType.parse("application/json")))
	            .build();

	    try (Response response = httpClient.newCall(request).execute()) {
	        if (!response.isSuccessful()) {
	            String errorBody = response.body() != null ? response.body().string() : "No error body";
	            throw new IOException("HTTP " + response.code() + ": " + errorBody);
	        }

	        return RavenUtil.toByteArray(response.body().byteStream());
	    }
	}

	public static class Builder {
		private String apiKey;

		public Builder apiKey(String apiKey) {
			this.apiKey = apiKey;
			return this;
		}

		public OpenAIClient build() {
			return new OpenAIClient(apiKey);
		}
	}

}