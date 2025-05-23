package br.com.fiap.lanchonete.application.device.persistence.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Integer>
{

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        return attribute == Boolean.TRUE ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        return dbData != null && dbData == 1;
    }

}