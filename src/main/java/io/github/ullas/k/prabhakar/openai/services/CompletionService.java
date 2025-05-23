package io.github.ullas.k.prabhakar.openai.services;

import java.util.List;
import java.util.Map;

import io.github.ullas.k.prabhakar.openai.vos.ChatMessage;

public interface CompletionService {
	String createChatCompletion(String model, List<ChatMessage> messages, Map<String, Object> options);
}