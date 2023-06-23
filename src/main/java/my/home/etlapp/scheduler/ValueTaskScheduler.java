/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package my.home.etlapp.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.home.etlapp.entity.BusinessType;
import my.home.etlapp.service.EventProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty("scheduler.enabled")
public class ValueTaskScheduler {

    private final EventProcessor eventProcessor;

    @Scheduled(cron = "${scheduler.checkPeriod}")
    public void loadData() {
        log.debug("Data uploading has started");
        var types = Set.of(BusinessType.TYPE4, BusinessType.TYPE5);
        eventProcessor.loadData(types, 0, 10);
    }
}
