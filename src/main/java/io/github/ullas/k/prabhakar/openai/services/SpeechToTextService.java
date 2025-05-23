package io.github.ullas.k.prabhakar.openai.services;

import java.io.IOException;
import java.util.Map;

public interface SpeechToTextService {
	String transcribeAudio(String model, byte[] audioBytes, Map<String, Object> options) throws IOException;
}
