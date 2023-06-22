package my.home.etlapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.home.etlapp.connector.LegacyAppConnector;
import my.home.etlapp.dto.Action;
import my.home.etlapp.dto.MessageDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventProcessorImpl implements EventProcessor {

    private final LegacyAppConnector legacyAppConnector;
    private final BusinessService businessService;

    @Override
    public void startEventProcessing(MessageDto dto) {
        var action = dto.action();
        if (action == Action.CREATE || action == Action.UPDATE) {
            var businessDto = legacyAppConnector.getValue(dto.id());
            businessService.save(businessDto);
        } else if (action == Action.DELETE) {
            businessService.delete(dto.id());
        }
        log.debug("Update database by event");
    }
}
