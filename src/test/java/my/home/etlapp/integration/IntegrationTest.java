package my.home.etlapp.integration;

import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.entity.BusinessEntity;
import my.home.etlapp.entity.BusinessType;
import my.home.etlapp.repository.BusinessRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@Testcontainers
@Transactional
@SpringBootTest
class IntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");


    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private WebClient webClient;

    @Test
    void testCallsPostMethodInLegacyAppThenValueMustBeCreatedInEtlApp() throws InterruptedException {
        var requestDto = new BusinessDto(BusinessType.TYPE5, "Value5");
        var responseDto = webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/value")
                        .build(7))
                .body(Mono.just(requestDto), BusinessDto.class)
                .retrieve()
                .bodyToMono(BusinessDto.class)
                .block();
        assert responseDto != null;
        TimeUnit.SECONDS.sleep(5);
        var dto = businessRepository.findByParentId(responseDto.id()).orElseGet(BusinessEntity::new);
        assertEquals(responseDto.id(), dto.getParentId());
        assertEquals(requestDto.type(), dto.getType());
        assertEquals(requestDto.businessValue(), dto.getBusinessValue());
    }
}
