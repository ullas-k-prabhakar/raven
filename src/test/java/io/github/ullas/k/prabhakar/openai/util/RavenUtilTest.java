package io.github.ullas.k.prabhakar.openai.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.junit.jupiter.api.Test;

public class RavenUtilTest {

    @Test
    public void testEncodeImageToBase64FileSmall() throws IOException {
        File temp = File.createTempFile("raven-test", ".bin");
        byte[] data = "hello".getBytes();
        Files.write(temp.toPath(), data);
        String encoded = RavenUtil.encodeImageToBase64(temp);
        assertEquals(Base64.getEncoder().encodeToString(data), encoded);
    }

    @Test
    public void testEncodeImageToBase64FileTooLarge() throws IOException {
        File temp = File.createTempFile("raven-large", ".bin");
        byte[] big = new byte[10 * 1024 * 1024 + 1];
        Files.write(temp.toPath(), big);
        assertThrows(IllegalArgumentException.class, () -> RavenUtil.encodeImageToBase64(temp));
    }

    @Test
    public void testEncodeImageToBase64Bytes() {
        byte[] data = "data".getBytes();
        String encoded = RavenUtil.encodeImageToBase64(data);
        assertEquals(Base64.getEncoder().encodeToString(data), encoded);
    }

    @Test
    public void testIsValidFilePathExisting() throws IOException {
        File temp = File.createTempFile("raven-path", ".txt");
        assertTrue(RavenUtil.isValidFilePath(temp.getAbsolutePath()));
    }

    @Test
    public void testIsValidFilePathNonExisting() {
        File file = new File("/non/existing/path/file.txt");
        assertFalse(RavenUtil.isValidFilePath(file.getAbsolutePath()));
    }

    @Test
    public void testIsValidBase64Valid() {
        String s = Base64.getEncoder().encodeToString("ok".getBytes());
        assertTrue(RavenUtil.isValidBase64(s));
    }

    @Test
    public void testIsValidBase64Invalid() {
        assertFalse(RavenUtil.isValidBase64("not-base64***"));
    }

    @Test
    public void testGetMimeTypeByExtensionPng() {
        assertEquals("image/png", RavenUtil.getMimeTypeByExtension("image.png"));
    }

    @Test
    public void testGetMimeTypeByExtensionJpg() {
        assertEquals("image/jpeg", RavenUtil.getMimeTypeByExtension("photo.jpg"));
    }

    @Test
    public void testGetMimeTypeByExtensionJpeg() {
        assertEquals("image/jpeg", RavenUtil.getMimeTypeByExtension("photo.jpeg"));
    }

    @Test
    public void testGetMimeTypeByExtensionDefault() {
        assertEquals("image/jpeg", RavenUtil.getMimeTypeByExtension("other.txt"));
    }
}
