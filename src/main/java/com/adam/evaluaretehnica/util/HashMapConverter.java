package com.adam.evaluaretehnica.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HashMapConverter implements AttributeConverter<Map<String,String>, String> {
    @Autowired
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(HashMapConverter.class);

    @Override
    public String convertToDatabaseColumn(Map<String, String> map) {
        String serialized = null;
        try {
            serialized = objectMapper.writeValueAsString(map);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return serialized;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String s) {
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(s,
                    new TypeReference<HashMap<String, String>>() {});
        } catch (final IOException | JsonProcessingException e) {
            logger.error("JSON reading error", e);
        }

        return map;
    }
}
