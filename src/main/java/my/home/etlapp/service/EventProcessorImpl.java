package my.home.etlapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.home.etlapp.client.LegacyAppClient;
import my.home.etlapp.dto.Action;
import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.dto.MessageDto;
import my.home.etlapp.entity.BusinessType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventProcessorImpl implements EventProcessor {

    private final LegacyAppClient legacyAppClient;
    private final BusinessService businessService;

    @Override
    public void startEventProcessing(MessageDto dto) {
        var action = dto.action();
        if (action == Action.CREATE || action == Action.UPDATE) {
            var businessDto = legacyAppClient.getValue(dto.id());
            businessService.save(businessDto);
        } else if (action == Action.DELETE) {
            businessService.delete(dto.id());
        }
        log.debug("Update database by event");
    }

    @Override
    public void loadDataByType(Set<BusinessType> types, int page, int size) {
        Page<BusinessDto> businessDtoPage = legacyAppClient.pageByType(types, page, size);
        saveToBase(businessDtoPage.getContent());
    }

    @Override
    public void loadDataByCreationDate(LocalDate dateFrom, LocalDate dateTo, int page, int size) {
        Page<BusinessDto> businessDtoPage = legacyAppClient.pageByCreationDate(dateFrom, dateTo, page, size);
        saveToBase(businessDtoPage.getContent());
    }

    private void saveToBase(List<BusinessDto> dtoList) {
        for (var dto : dtoList) {
            businessService.save(dto);
        }
        log.debug("The data is loaded into the database");
    }
}
