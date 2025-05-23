package io.github.ullas.k.prabhakar.openai.vos;

import java.util.List;

public class ImageInputChatMessage {
	private String role;
	private List<ImageContent> content;

	public ImageInputChatMessage() {
	}

	public ImageInputChatMessage(String role, List<ImageContent> content) {
		this.role = role;
		this.content = content;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<ImageContent> getContent() {
		return content;
	}

	public void setContent(List<ImageContent> contents) {
		this.content = contents;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImageInputChatMessage [");
		if (role != null) {
			builder.append("role=").append(role).append(", ");
		}
		if (content != null) {
			builder.append("contents=").append(content);
		}
		builder.append("]");
		return builder.toString();
	}

}