package com.wuga.host.check.controller;


import com.wuga.host.check.domain.HostsDTO;
import com.wuga.host.check.service.HostService;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class CheckController {

    private final HostService hostServiceImpl;

    @GetMapping("/hosts")
    public ResponseEntity<?> hosts() {

        List<HostsDTO> all = hostServiceImpl.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("data", all);
        result.put("count", all.size());

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/hosts")
    public ResponseEntity<?> createHost() throws IOException {

        HostsDTO result = hostServiceImpl.save();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .build()
            .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/hosts/{ip}")
    public ResponseEntity<?> hostByIp(@PathVariable String ip) {

        HostsDTO result = hostServiceImpl.findByIp(ip);

        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/hosts/{ip}")
    public ResponseEntity<?> updateHost(@PathVariable String ip, HostsDTO hostsDTO) {

        HostsDTO update = hostServiceImpl.update(ip, hostsDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .build()
            .toUri();
        return ResponseEntity.created(location).body(update);
    }

    @DeleteMapping("/hosts/{ip}")
    public ResponseEntity<?> deleteHost(@PathVariable String ip) {

        hostServiceImpl.deleteByIp(ip);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .build()
            .toUri();

        return ResponseEntity.ok().body(location);
    }

    @GetMapping("/hosts/health/{ip}")
    public ResponseEntity<> checkHealth(@PathVariable String ip) {

        hostServiceImpl.
    }
}
