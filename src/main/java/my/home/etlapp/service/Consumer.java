package my.home.etlapp.service;

import lombok.RequiredArgsConstructor;
import my.home.etlapp.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty("spring.kafka.consumer.enabled")
public class Consumer {

    private final EventProcessor eventProcessor;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(id = "etl-group", topics = "#{'${spring.kafka.consumer.topics}'.split(',')}")
    public void consume(final @Payload MessageDto message,
                        final @Header(KafkaHeaders.OFFSET) Integer offset,
                        final @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        final @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                        final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                        final Acknowledgment acknowledgment
    ) {
        logger.info("""
                        Consumed:
                        topic: {}
                        partition: {}
                        offset: {}
                        key: {}
                        message: {}""",
                topic,
                partition,
                offset,
                key,
                message
        );
        acknowledgment.acknowledge();
        eventProcessor.startEventProcessing(message);
    }
}
