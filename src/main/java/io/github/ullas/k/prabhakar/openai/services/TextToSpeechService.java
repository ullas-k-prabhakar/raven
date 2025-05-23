package io.github.ullas.k.prabhakar.openai.services;

import java.io.IOException;
import java.util.Map;

public interface TextToSpeechService {
	byte[] synthesizeSpeech(String model, String text, Map<String, Object> options) throws IOException;
}