# Raven

Raven is a Java client for the OpenAI API. It offers simple wrappers around the HTTP endpoints so you can call OpenAI services from your Java applications.

## Maven

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.ullas-k-prabhakar</groupId>
    <artifactId>raven</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Usage

Create an `OpenAIClient` with your API key and invoke the desired service. The example below creates a chat completion using the completion service.

```java
import java.util.Collections;
import java.util.List;
import io.github.ullas.k.prabhakar.openai.AIModels;
import io.github.ullas.k.prabhakar.openai.OpenAIClient;
import io.github.ullas.k.prabhakar.openai.vos.ChatMessage;

OpenAIClient client = OpenAIClient.builder()
        .apiKey("YOUR_API_KEY")
        .build();

List<ChatMessage> messages = List.of(
        new ChatMessage("user", "Hello")
);

String result = client.completion()
        .createChatCompletion(AIModels.GPT_3_5_TURBO, messages, Collections.emptyMap());
```
