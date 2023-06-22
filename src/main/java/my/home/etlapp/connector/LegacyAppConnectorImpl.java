package my.home.etlapp.connector;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import my.home.etlapp.client.LegacyAppClient;
import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.exception.LegacyAppException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LegacyAppConnectorImpl implements LegacyAppConnector {

    private final LegacyAppClient legacyAppClient;

    @Override
    public BusinessDto getValue(Long id) {
        BusinessDto dto;
        try {
            dto = legacyAppClient.getValue(id);
        } catch (FeignException e) {
            throw new LegacyAppException("Load value from legacy-app fail", e);
        }
        return dto;
    }
}
