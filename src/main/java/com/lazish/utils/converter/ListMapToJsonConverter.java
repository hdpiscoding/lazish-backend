package com.lazish.utils.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Converter(autoApply = true)
public class ListMapToJsonConverter implements AttributeConverter<List<Map<String, String>>, String> {
    private static final Logger logger = LoggerFactory.getLogger(ListMapToJsonConverter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Map<String, String>> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            logger.error("Error converting List<Map<String, String>> to JSON", e);
            throw new RuntimeException("JSON writing error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, String>> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Map<String, String>>>() {});
        } catch (IOException e) {
            logger.error("Error converting JSON to List<Map<String, String>>", e);
            throw new RuntimeException("JSON reading error: " + e.getMessage(), e);
        }
    }
}
