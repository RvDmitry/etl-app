package my.home.etlapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import my.home.etlapp.dto.MessageDto;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class KafkaValueDeserializer implements Deserializer<MessageDto> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public MessageDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data, "UTF-8"), MessageDto.class);
        } catch (Exception e) {
            log.error("Unable to deserialize message {}", data, e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}
