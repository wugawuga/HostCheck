package com.wuga.host.check.controller;

import static com.wuga.host.check.domain.Hosts.createHosts;

import com.wuga.host.check.repository.HostsEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final HostsEntityRepository hostsEntityRepository;

    @GetMapping("/")
    public String goMain() {

        return "index";
    }
}
