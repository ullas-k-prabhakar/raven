package io.github.ullas.k.prabhakar.openai.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.services.TextToSpeechService;

public class TextToSpeechServiceImpl implements TextToSpeechService {
	private final OpenAIClient client;

	public TextToSpeechServiceImpl(OpenAIClient client) {
		this.client = client;
	}

	@Override
	public byte[] synthesizeSpeech(String model, String text, Map<String, Object> options) throws IOException {
		Map<String, Object> body = new HashMap<>();
		body.put("model", model);
		body.put("input", text);
		body.putAll(options);
		return client.postForBinary("/audio/speech", body);
	}
}