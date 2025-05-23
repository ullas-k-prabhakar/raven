package io.github.ullas.k.prabhakar.openai.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.exception.RavenException;
import io.github.ullas.k.prabhakar.openai.services.CompletionService;
import io.github.ullas.k.prabhakar.openai.vos.ChatMessage;

public class CompletionServiceImpl implements CompletionService {
	private final OpenAIClient client;

	public CompletionServiceImpl(OpenAIClient client) {
		this.client = client;
	}

	@Override
	public String createChatCompletion(String model, List<ChatMessage> messages, Map<String, Object> options) {
		Map<String, Object> body = new HashMap<>();
		body.put("model", model);
		body.putAll(options);
		body.put("messages", messages);
		String response = null;
		try {
			response = client.post("/chat/completions", body);
			JsonObject root = client.getGson().fromJson(response, JsonObject.class);
			JsonObject message = root.getAsJsonArray("choices").get(0).getAsJsonObject().getAsJsonObject("message");
			return message.get("content").getAsString();

		} catch (IOException e) {
			e.printStackTrace();
			throw new RavenException(response, e);

		}

	}

}