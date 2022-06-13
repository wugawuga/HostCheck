package com.wuga.host.check.domain;

import lombok.Getter;

@Getter
public class HostsDTO {

    private Long id;

    private String ip;
    private String name;
    private String registrationTime;
    private String updateTime;

    private AliveStatus status;

    public HostsDTO(Long id, String ip, String name, String registrationTime, String updateTime, AliveStatus status) {
        this.id = id;
        this.ip = ip;
        this.name = name;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;
        this.status = status;
    }

}
