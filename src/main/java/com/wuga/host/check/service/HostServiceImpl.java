package com.wuga.host.check.service;

import static com.wuga.host.check.domain.Hosts.*;

import com.wuga.host.check.domain.Hosts;
import com.wuga.host.check.domain.HostsDTO;
import com.wuga.host.check.exception.ExistHostException;
import com.wuga.host.check.exception.NotResponseException;
import com.wuga.host.check.repository.HostsEntityRepository;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class HostServiceImpl implements HostService{

    private final HostsEntityRepository hostsEntityRepository;

    @Override
    public List<HostsDTO> findAll() {

        log.info("findAll() 호출");

        List<Hosts> all = hostsEntityRepository.findAll();
        if (all.size() == 0) {
            throw new NoResultException("등록된 호스트가 없습니다");
        }
        List<HostsDTO> allDTO = all.stream().map(hosts -> new HostsDTO(
            hosts.getIp(),
            hosts.getName())).collect(Collectors.toList());
        return allDTO;
    }

    @Override
    public HostsDTO save() throws IOException {

        log.info("save() 호출");


        InetAddress ip = InetAddress.getLocalHost();

        if (ip.isReachable(1000)) {
            log.info("Reachable = {}", ip.getHostName());
        } else {
            log.error("UnReachable = {}", ip.getHostAddress());
            throw new NotResponseException("응답이 없습니다");
        }
        check(ip.getHostAddress());
        Hosts hosts = hostsEntityRepository.save(createHosts(ip.getHostAddress(), ip.getHostName()));

        return changeDTO(hosts);
    }

    @Override
    public HostsDTO findByIp(String ip) {

        log.info("findByIp() 호출");

        checkNotExist(ip);
        Hosts hosts = hostsEntityRepository.findByIp(ip);

        return changeDTO(hosts);
    }

    @Override
    public HostsDTO update(String ip, HostsDTO hostsDTO) {

        log.info("update() 호출");

        checkNotExist(ip);
        Hosts host = hostsEntityRepository.findByIp(ip);
        host.updateHosts(hostsDTO.getIp(), hostsDTO.getName());
        Hosts updateHost = hostsEntityRepository.save(host);

        return changeDTO(updateHost);
    }

    @Override
    public void deleteByIp(String ip) {

        checkNotExist(ip);
        hostsEntityRepository.deleteByIp(ip);
    }

    private void checkNotExist(String ip) {

        log.info("존재할때 수정,조회");
        if (!hostsEntityRepository.existsByIp(ip)) {
            throw new NoResultException("존재하지 않는 호스트입니다");
        }
    }

    private void check(String ip) {

        log.info("존재하지않을때 삽입");
        if (hostsEntityRepository.existsByIp(ip)) {
            throw new ExistHostException("존재하는 호스트입니다");
        }
    }

    private HostsDTO changeDTO(Hosts hosts) {

        return new HostsDTO(hosts.getIp(), hosts.getName());
    }
}
