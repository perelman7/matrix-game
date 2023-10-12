package org.example.util;

import org.example.model.request.GameConfigDto;

import java.io.FileInputStream;

public class FileReader {

    public static GameConfigDto parseFromFile(String filename) {
        ModelParserUtil modelParser = new ModelParserUtil();
        GameConfigDto resp;
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            String json = new String(fileInputStream.readAllBytes());
            resp = modelParser.parse(json, GameConfigDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}
