package my.home.etlapp.client;

import my.home.etlapp.dto.BusinessDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${legacy.service.url}", name = "legacy")
public interface LegacyAppClient {

    @GetMapping(value = "/api/value/{id}", produces = "application/json", consumes = "application/json")
    BusinessDto getValue(@PathVariable("id") Long id);
}
