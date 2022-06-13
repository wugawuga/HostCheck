package com.wuga.host.check.domain;

import static com.wuga.host.check.domain.AliveStatus.DISCONNECTED;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "HOSTS",
    uniqueConstraints = {@UniqueConstraint(
    name = "IP_NAME_UNIQUE",
    columnNames= {"IP", "NAME"}
)}
)
@Getter
public class Hosts {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "IP")
    private String ip;

    @Column(name = "NAME")
    private String name;

    @Column(name = "REGISTRATION_TIME")
    private LocalDateTime registrationTime;

    @Column(name = "UPDATE_TIME")
    private LocalDateTime updateTime;

    @Enumerated(value = EnumType.STRING)
    private AliveStatus status;

    private Hosts(String ip, String name, LocalDateTime registrationTime, LocalDateTime updateTime, AliveStatus status) {
        this.ip = ip;
        this.name = name;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;
        this.status = status;
    }

    private Hosts(String ip, String name, LocalDateTime updateTime) {
        this.ip = ip;
        this.name = name;
        this.updateTime = updateTime;
    }

    private Hosts(LocalDateTime updateTime, AliveStatus status) {
        this.updateTime = updateTime;
        this.status = status;
    }

    public static Hosts createHosts(String ip, String name, LocalDateTime registrationTime, LocalDateTime updateTime, AliveStatus status) {
       return new Hosts(ip, name, registrationTime, updateTime, status);
    }

    public void updateHosts(HostsDTO hostsDTO) {
        this.ip = hostsDTO.getIp();
        this.name = hostsDTO.getName();
        this.updateTime = LocalDateTime.now();
    }

    public void updateAliveHosts() {
        this.updateTime = LocalDateTime.now();
        this.status = DISCONNECTED;
    }
}
