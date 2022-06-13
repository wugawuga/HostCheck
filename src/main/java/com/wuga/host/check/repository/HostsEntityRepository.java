package com.wuga.host.check.repository;

import com.wuga.host.check.domain.Hosts;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface HostsEntityRepository extends JpaRepository<Hosts, Long> {

    boolean existsByIp(String ip);

    Hosts findByIp(String ip);

    void deleteByIp(String ip);

    boolean existsByName(String hostName);
}
