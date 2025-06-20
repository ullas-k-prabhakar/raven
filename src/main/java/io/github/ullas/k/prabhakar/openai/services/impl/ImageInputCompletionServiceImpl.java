package io.github.ullas.k.prabhakar.openai.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.exception.RavenException;
import io.github.ullas.k.prabhakar.openai.services.ImageInputCompletionService;
import io.github.ullas.k.prabhakar.openai.util.RavenUtil;
import io.github.ullas.k.prabhakar.openai.vos.ImageContent;
import io.github.ullas.k.prabhakar.openai.vos.ImageInputChatMessage;
import io.github.ullas.k.prabhakar.openai.vos.ImageUrl;

public class ImageInputCompletionServiceImpl implements ImageInputCompletionService {
	private final OpenAIClient client;

	public ImageInputCompletionServiceImpl(OpenAIClient client) {
		super();
		this.client = client;
	}

	@Override
	public String createImageChatCompletion(String model, List<ImageInputChatMessage> messages,
			Map<String, Object> options) {
		Map<String, Object> body = new HashMap<>();
		body.put("model", model);
		body.putAll(options);

		for (ImageInputChatMessage msg : messages) {
			for (ImageContent c : msg.getContent()) {
				String img = c.getImageUrl().getUrl();
				if (RavenUtil.isValidFilePath(img)) {
					String base64;
					try {
						base64 = RavenUtil.encodeImageToBase64(new File(img));
						String dataUri = "data:" + RavenUtil.getMimeTypeByExtension(img) + ";base64,'" + base64 + "'";
						c.setImageUrl(new ImageUrl(dataUri));
                                        } catch (IOException e) {
                                                throw new RavenException(
                                                                "Failed to encode image to Base64 for path: " + img,
                                                                e);
                                        }
				}
				// else if you want to normalize an already‐Base64 string:
				else if (RavenUtil.isValidBase64(img)) {
					// (optionally) prefix it with a proper data URI header
					// c.setImageUrl(" { \"url\": data:image/jpeg;base64," + img + " } ");
				}
			}
		}

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