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

@Converter(autoApply = true)
public class ListStringToJsonConverter implements AttributeConverter<List<String>, String> {
    private static final Logger logger = LoggerFactory.getLogger(ListStringToJsonConverter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            logger.error("Error converting List<String> to JSON", e);
            throw new RuntimeException("JSON writing error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            logger.error("Error converting JSON to List<String>", e);
            throw new RuntimeException("JSON reading error: " + e.getMessage(), e);
        }
    }
}
