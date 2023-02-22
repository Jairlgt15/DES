package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Archivos {

   public Archivos(){};

    public  byte[] CrearArchivo(String mensaje) throws Exception {
        String originalContent = "";
        Path tempFile = Files.createTempFile("temp", "txt");
        writeString(tempFile, originalContent);
        byte[] fileBytes = Files.readAllBytes(tempFile);
        return fileBytes;
    }

    public void writeString(Path path, String content) throws Exception {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        }
    }
    public String readString(Path path) throws Exception {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        }
        return resultStringBuilder.toString();
    }
}
