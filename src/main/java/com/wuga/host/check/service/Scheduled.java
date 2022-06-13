package com.wuga.host.check.service;

import com.wuga.host.check.domain.HostsDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduled {

    private final HostService hostServiceImpl;

    @org.springframework.scheduling.annotation.Scheduled(cron = "0 */30 * * * *")
    public void checkPing() throws InterruptedException {

        log.info("schedule start = {}", LocalDateTime.now());

        Long count = hostServiceImpl.countForSchedule();
        if (count != 0L) {
            List<HostsDTO> all = hostServiceImpl.findAll();
            for (HostsDTO hostsDTO : all) {
                hostServiceImpl.checkPings(hostsDTO);
            }
        }
        log.info("schedule end = {}", LocalDateTime.now());
    }

}
