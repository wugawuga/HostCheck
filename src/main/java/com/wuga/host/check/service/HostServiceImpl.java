package com.wuga.host.check.service;

import static com.wuga.host.check.domain.Hosts.createHosts;

import com.wuga.host.check.domain.AliveStatus;
import com.wuga.host.check.domain.Hosts;
import com.wuga.host.check.domain.HostsDTO;
import com.wuga.host.check.exception.DoNotCreateAboveException;
import com.wuga.host.check.exception.ExistHostException;
import com.wuga.host.check.repository.HostsEntityRepository;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@Transactional
public class HostServiceImpl implements HostService{

    private final HostsEntityRepository hostsEntityRepository;
    private final HttpSession session;

    @Override
    public Long count() {
        if (session.getAttribute("count") == null) {
            return 0L;
        } else if ((Long)session.getAttribute("count") >= 100L) {
            throw new DoNotCreateAboveException("100개 초과! 등록할 수 없습니다");
        }
        return hostsEntityRepository.count();
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void checkPings(HostsDTO all) throws InterruptedException {

        Thread.sleep(3000);
        try {
            InetAddress ip = InetAddress.getByName(all.getIp());
            boolean reachable = ip.isReachable(2000);
            if (reachable) {
                log.info("success = {}", ip.getHostName());
            } else {
                log.error("fail = {}", ip.getHostName());
                Hosts hostsByIp = hostsEntityRepository.findByIp(all.getIp());
                hostsByIp.updateAliveHosts();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<HostsDTO> findAll() {

        List<Hosts> all = hostsEntityRepository.findAll();
        if (all.size() == 0) {
            throw new NoResultException("등록된 호스트가 없습니다");
        }
        List<HostsDTO> allDTO = all.stream()
            .map(hosts -> new HostsDTO(
                hosts.getId(),
                hosts.getIp(),
                hosts.getName(),
                hosts.getRegistrationTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                hosts.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                hosts.getStatus()))
            .collect(Collectors.toList());

        return allDTO;
    }

    @Override
    public HostsDTO save(String hostName) throws UnknownHostException {

        InetAddress ip;
        try {
            ip = InetAddress.getByName(hostName);
        } catch (UnknownHostException e) {
            throw new UnknownHostException("존재하지 않는 도메인입니다");
        }
        check(ip.getHostAddress(), hostName);
        Hosts hosts = hostsEntityRepository.save(
            createHosts(
                ip.getHostAddress(),
                ip.getHostName(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                AliveStatus.CONNECTED
            ));


        return changeDTO(hosts);
    }

    @Override
    public HostsDTO findByIp(String ip) {

        checkNotExist(ip);
        Hosts hosts = hostsEntityRepository.findByIp(ip);

        return changeDTO(hosts);
    }

    @Override
    public HostsDTO update(String ip, HostsDTO hostsDTO) {

        checkNotExist(ip);
        Hosts host = hostsEntityRepository.findByIp(ip);

        host.updateHosts(hostsDTO);

        return changeDTO(host);
    }

    @Override
    public void deleteByIp(String ip) {

        checkNotExist(ip);
        hostsEntityRepository.deleteByIp(ip);
    }

    private void checkNotExist(String ip) {

        if (!hostsEntityRepository.existsByIp(ip)) {
            throw new NoResultException("존재하지 않는 호스트입니다");
        }
    }

    private void check(String ip, String hostName) {

        if (hostsEntityRepository.existsByIp(ip)) {
            throw new ExistHostException("존재하는 호스트입니다");
        }else if (hostsEntityRepository.existsByName(hostName)) {
            throw new ExistHostException("존재하는 호스트입니다");
        }
    }

    private HostsDTO changeDTO(Hosts hosts) {

        return new HostsDTO(
            hosts.getId(),
            hosts.getIp(),
            hosts.getName(),
            hosts.getRegistrationTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
            hosts.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
            hosts.getStatus()
        );
    }

    @Override
    public Long countForSchedule() {

        return hostsEntityRepository.count();
    }

}
