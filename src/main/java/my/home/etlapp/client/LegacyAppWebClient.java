package my.home.etlapp.client;

import lombok.RequiredArgsConstructor;
import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.entity.BusinessType;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class LegacyAppWebClient implements LegacyAppClient {

    private final WebClient webClient;

    @Override
    public BusinessDto getValue(long id) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/value/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(BusinessDto.class)
                .block();
    }

    @Override
    public Page<BusinessDto> pageByType(Set<BusinessType> types, int page, int size) {
        StringJoiner joiner = new StringJoiner(",");
        types.forEach(type -> joiner.add(type.toString()));
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/values/types")
                        .queryParam("types", String.join(",", joiner.toString()))
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Page.class)
                .block();
    }
}
