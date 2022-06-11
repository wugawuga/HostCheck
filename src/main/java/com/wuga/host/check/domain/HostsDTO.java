package com.wuga.host.check.domain;

import lombok.Getter;

@Getter
public class HostsDTO {

    private String ip;
    private String name;

    public HostsDTO(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }
}
