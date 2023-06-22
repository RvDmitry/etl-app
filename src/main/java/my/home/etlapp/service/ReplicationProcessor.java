package my.home.etlapp.service;

import my.home.etlapp.dto.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class ReplicationProcessor implements Processor {
    @Override
    public void startProcess(MessageDto dto) {
        System.out.println("This processor: " + dto);
    }
}
