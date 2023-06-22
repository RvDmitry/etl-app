package my.home.etlapp.service;

import my.home.etlapp.dto.MessageDto;

public interface Processor {

    void startProcess(MessageDto dto);
}
