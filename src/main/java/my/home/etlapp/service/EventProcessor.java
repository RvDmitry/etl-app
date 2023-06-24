package my.home.etlapp.service;

import my.home.etlapp.dto.MessageDto;
import my.home.etlapp.entity.BusinessType;

import java.time.LocalDate;
import java.util.Set;

public interface EventProcessor {

    void startEventProcessing(MessageDto dto);

    void loadDataByType(Set<BusinessType> types, int page, int size);

    void loadDataByCreationDate(LocalDate dateFrom, LocalDate dateTo, int page, int size);
}
