package com.smidl.app.model;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TaskStateConverter implements AttributeConverter<TaskState, String> {

    @Override
    public String convertToDatabaseColumn(TaskState attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public TaskState convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(TaskState.values()).filter(c -> c.getCode().equals(dbData)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
