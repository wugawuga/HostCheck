package com.wuga.host.check.service;

import com.wuga.host.check.domain.HostsDTO;
import java.net.UnknownHostException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface HostService {

    List<HostsDTO> findAll();

    HostsDTO save(String hostName) throws UnknownHostException;

    HostsDTO findByIp(String ip);

    HostsDTO update(String ip, HostsDTO hostsDTO);

    void deleteByIp(String ip);

    Long count();

    void checkPings(HostsDTO all) throws InterruptedException;

    Long countForSchedule();
}
