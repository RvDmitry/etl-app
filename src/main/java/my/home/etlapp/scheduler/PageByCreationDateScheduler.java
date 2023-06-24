package my.home.etlapp.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.home.etlapp.service.EventProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty("scheduler.by-creation-date.enabled")
public class PageByCreationDateScheduler {

    private final EventProcessor eventProcessor;

    @Value("${scheduler.by-creation-date.pagination.page}")
    private int page;

    @Value("${scheduler.by-creation-date.pagination.size}")
    private int size;

    @Value("${scheduler.by-creation-date.dateFrom}")
    private LocalDate dateFrom;

    @Value("${scheduler.by-creation-date.dateTo}")
    private LocalDate dateTo;

    @Scheduled(cron = "${scheduler.by-creation-date.checkPeriod}")
    public void loadData() {
        log.debug("Data uploading has started");
        eventProcessor.loadDataByCreationDate(dateFrom, dateTo, page, size);
    }
}
