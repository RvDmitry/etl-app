package my.home.etlapp.client;

import lombok.RequiredArgsConstructor;
import my.home.etlapp.dto.BusinessDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class LegacyAppWebClient implements LegacyAppClient {

    private final WebClient webClient;

    @Override
    public BusinessDto getValue(long id) {
        return webClient
                .get()
                .uri(String.join("", "/api/value/", String.valueOf(id)))
                .retrieve()
                .bodyToMono(BusinessDto.class)
                .block();
    }
}
