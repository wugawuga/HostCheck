package com.wuga.host.check.controller;


import com.wuga.host.check.domain.HostsDTO;
import com.wuga.host.check.service.HostService;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CheckController {

    private final HostService hostServiceImpl;
    private final HttpSession session;

    @GetMapping("/api/hosts")
    public ResponseEntity<?> hosts() {

        List<HostsDTO> all = hostServiceImpl.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("data", all);
        result.put("count", all.size());

        log.info("session count = {}", session.getAttribute("count"));

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/api/hosts")
    public ResponseEntity<?> saveHost(@RequestParam("hostName") String hostName)
        throws RuntimeException, UnknownHostException {

        session.setAttribute("count", hostServiceImpl.count() + 1);

        HostsDTO result = hostServiceImpl.save(hostName);

        URI location = creatLocation();

        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/api/hosts/{ip}")
    public ResponseEntity<?> hostByIp(@PathVariable String ip) {

        HostsDTO result = hostServiceImpl.findByIp(ip);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/api/hosts/{ip}")
    public ResponseEntity<?> updateHost(@PathVariable String ip, HostsDTO hostsDTO) {

        HostsDTO result = hostServiceImpl.update(ip, hostsDTO);

        URI location = creatLocation();

        return ResponseEntity.created(location).body(result);
    }

    @DeleteMapping("/api/hosts/{ip}")
    public ResponseEntity<?> deleteHost(@PathVariable String ip) {

        hostServiceImpl.deleteByIp(ip);

        return ResponseEntity.noContent().build();
    }

    private URI creatLocation() {
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .build()
            .toUri();
    }
}
