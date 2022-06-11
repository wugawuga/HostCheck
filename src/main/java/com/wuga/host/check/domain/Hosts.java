package com.wuga.host.check.domain;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @JsonIgnore
    private Long id;

    @Column(name = "IP")
    private String ip;

    @Column(name = "NAME")
    private String name;

    private Hosts(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public static Hosts createHosts(String ip, String name) {
       return new Hosts(ip, name);
    }

    public void updateHosts(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }
}
