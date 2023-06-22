package my.home.etlapp.service;

import my.home.etlapp.dto.MessageDto;

public interface EventProcessor {

    void startEventProcessing(MessageDto dto);
}
