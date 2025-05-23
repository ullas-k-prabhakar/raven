package io.github.ullas.k.prabhakar.openai.services.impl;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;

import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.services.SpeechToTextService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SpeechToTextServiceImpl implements SpeechToTextService {
	private final OpenAIClient client;

	public SpeechToTextServiceImpl(OpenAIClient client) {
		this.client = client;
	}

	@Override
	public String transcribeAudio(String model, byte[] audioBytes, Map<String, Object> options) throws IOException {

		if (audioBytes == null || audioBytes.length == 0) {
			throw new IllegalArgumentException("Audio bytes cannot be null or empty");
		}

		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("model", model)
				.addFormDataPart("file", "audio.mp3", RequestBody.create(audioBytes, MediaType.parse("audio/mp3")));

		if (options != null) {
			options.forEach((k, v) -> builder.addFormDataPart(k, v.toString()));
		}

		Request request = new Request.Builder().url(client.getBaseUrl() + "/audio/transcriptions")
				.addHeader("Authorization", "Bearer " + client.getApiKey()).post(builder.build()).build();

		try (Response response = client.getHttpClient().newCall(request).execute()) {
			String responseBody = response.body() != null ? response.body().string() : "";
			if (!response.isSuccessful()) {
				System.err.println("STT API error response: " + responseBody);
				throw new IOException("Unexpected code " + response.code() + ": " + responseBody);
			}
			JsonObject json = client.getGson().fromJson(responseBody, JsonObject.class);
			return json.get("text").getAsString();
		}
	}
}
