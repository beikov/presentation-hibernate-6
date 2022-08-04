package org.hibernate.presentation.model;

import jakarta.persistence.AttributeConverter;

public class TopicTagConverter implements AttributeConverter<TopicTag, String> {
    @Override
    public String convertToDatabaseColumn(TopicTag attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public TopicTag convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TopicTag.valueOf(dbData);
    }
}
