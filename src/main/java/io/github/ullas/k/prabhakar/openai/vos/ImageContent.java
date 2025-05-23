package io.github.ullas.k.prabhakar.openai.vos;

import java.io.File;
import java.io.IOException;

import io.github.ullas.k.prabhakar.openai.util.RavenUtil;

public class ImageContent {

	private String type;
	private String text;
	private ImageUrl image_url;

	public ImageContent() {

	}

	public ImageContent(File f) {
		this.type = "image_url";
		this.text = null;
		try {
			String base64 = RavenUtil.encodeImageToBase64(f);
			String dataUri = "data:" + RavenUtil.getMimeTypeByExtension(f.getName()) + ";base64,'" + base64 + "'";
			this.image_url = new ImageUrl(dataUri);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ImageContent(byte[] file) {
		this.type = "image_url";
		this.text = null;
		String base64 = RavenUtil.encodeImageToBase64(file);
		String dataUri = "data:" + RavenUtil.getMimeTypeByExtension(null) + ";base64,'" + base64 + "'";
		this.image_url = new ImageUrl(dataUri);

	}

	public ImageContent(String type, String text, ImageUrl imageUrl) {
		this.type = type;
		this.text = text;
		this.image_url = imageUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ImageUrl getImageUrl() {
		if (null == image_url) {
			return new ImageUrl();

		}
		return image_url;
	}

	public void setImageUrl(ImageUrl imageUrl) {
		this.image_url = imageUrl;
	}

}
