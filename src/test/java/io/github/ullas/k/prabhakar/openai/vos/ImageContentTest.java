package io.github.ullas.k.prabhakar.openai.vos;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;

import junit.framework.TestCase;

public class ImageContentTest extends TestCase {

    public void testConstructorsProduceDataUri() throws Exception {
        byte[] data = "hello".getBytes();
        File temp = File.createTempFile("img", ".png");
        temp.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(temp)) {
            fos.write(data);
        }

        ImageContent fileContent = new ImageContent(temp);
        assertNotNull(fileContent.getImageUrl());
        assertTrue(fileContent.getImageUrl().getUrl().startsWith("data:"));

        ImageContent byteContent = new ImageContent(data);
        assertNotNull(byteContent.getImageUrl());
        assertTrue(byteContent.getImageUrl().getUrl().startsWith("data:"));
    }

    public void testGetImageUrlNotNullAndToString() {
        ImageContent empty = new ImageContent();
        assertNotNull(empty.getImageUrl());

        ImageContent content = new ImageContent();
        ImageInputChatMessage msg = new ImageInputChatMessage("user", Collections.singletonList(content));
        String str = msg.toString();
        assertTrue(str.contains("role=user"));
        assertTrue(str.contains("contents="));
    }
}
