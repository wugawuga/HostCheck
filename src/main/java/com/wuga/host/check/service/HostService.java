package com.wuga.host.check.service;

import com.wuga.host.check.domain.Hosts;
import com.wuga.host.check.domain.HostsDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface HostService {

    List<HostsDTO> findAll();

    HostsDTO save() throws IOException;

    HostsDTO findByIp(String ip);

    HostsDTO update(String ip, HostsDTO hostsDTO);

    void deleteByIp(String ip);
}
