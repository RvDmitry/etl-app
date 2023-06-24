package my.home.etlapp.controller;

import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.entity.BusinessType;
import my.home.etlapp.repository.BusinessRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class HandleControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    private static final Long PARENT_ID = 3L;
    private static final BusinessType TYPE = BusinessType.TYPE3;
    private static final String BUSINESS_VALUE = "Value3";

    private ClientAndServer mockServer;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BusinessRepository businessRepository;
    private Page<BusinessDto> page;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8081);
        var businessDto = new BusinessDto(PARENT_ID, TYPE, BUSINESS_VALUE, LocalDateTime.now(), LocalDateTime.now());
        page = new PageImpl<>(List.of(businessDto), Pageable.ofSize(1), 1);

    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    void testDownloadsDataFromLegacyAppByTypeAndSavesThemToDb() throws Exception {
        new MockServerClient("localhost", 8081)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/api/values/types")
                                .withHeader("content-type", "application/json"),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("content-type", "application/json")
                                .withBody(json(page))
                );
        this.mockMvc.perform(get("/api/values/types").param("types", TYPE.toString()))
                .andDo(print())
                .andExpect(status().isAccepted());
        var entityOptional = businessRepository.findByParentId(PARENT_ID);
        assertTrue(entityOptional.isPresent());
        assertEquals(PARENT_ID, entityOptional.get().getParentId());
        assertEquals(TYPE, entityOptional.get().getType());
        assertEquals(BUSINESS_VALUE, entityOptional.get().getBusinessValue());
    }

    @Test
    void testDownloadsDataFromLegacyAppByCreateAtAndSavesThemToDb() throws Exception {
        new MockServerClient("localhost", 8081)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/api/values/created")
                                .withHeader("content-type", "application/json"),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("content-type", "application/json")
                                .withBody(json(page))
                );
        this.mockMvc.perform(get("/api/values/created")
                        .param("dateFrom", LocalDate.now().toString())
                        .param("dateTo", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isAccepted());
        var entityOptional = businessRepository.findByParentId(PARENT_ID);
        assertTrue(entityOptional.isPresent());
        assertEquals(PARENT_ID, entityOptional.get().getParentId());
        assertEquals(TYPE, entityOptional.get().getType());
        assertEquals(BUSINESS_VALUE, entityOptional.get().getBusinessValue());
    }

}