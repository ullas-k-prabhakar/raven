package io.github.ullas.k.prabhakar.openai.services;

import java.util.List;
import java.util.Map;

import io.github.ullas.k.prabhakar.openai.vos.ImageInputChatMessage;

public interface ImageInputCompletionService {
	String createImageChatCompletion(String model, List<ImageInputChatMessage> messages, Map<String, Object> options);
}