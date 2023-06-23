package my.home.etlapp.service;

import my.home.etlapp.dto.MessageDto;
import my.home.etlapp.entity.BusinessType;

import java.util.Set;

public interface EventProcessor {

    void startEventProcessing(MessageDto dto);

    void loadData(Set<BusinessType> types, int page, int size);
}
