package fixel.cs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;

// 제네릭 타입으로 어떠한 타입도 String으로 변환할 수 있도록 함
@Slf4j
public class ListToConverter<T> implements AttributeConverter<T, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(T attribute) {

        // T -> Json
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("fail to serialize as object into Json : {}", attribute, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {

        // Json -> T
        try {
            return objectMapper.readValue(dbData, new TypeReference<T>() {});
        } catch (IOException e) {
            log.error("fail to deserialize as Json into Object : {}", dbData, e);
            throw new RuntimeException(e);
        }
    }
}
