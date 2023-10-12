package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ModelParserUtil {

    public ModelParserUtil() {
        this.objectMapper = new ObjectMapper();
    }
    private final ObjectMapper objectMapper;

    public <T> T parse(String json, Class<T> tClass) {
        T resp = null;
        try {
            resp = objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            System.out.println("PARSE ERROR");
        }
        return resp;
    }
}