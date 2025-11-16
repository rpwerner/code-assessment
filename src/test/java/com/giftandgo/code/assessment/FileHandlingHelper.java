package com.giftandgo.code.assessment;

import java.io.IOException;
import java.io.InputStream;

public class FileHandlingHelper {

    public byte[] getFileInBytes(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        return inputStream.readAllBytes();
    }
}
