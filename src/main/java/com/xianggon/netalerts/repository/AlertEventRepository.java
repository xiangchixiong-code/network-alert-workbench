package com.xianggon.netalerts.repository;

import com.xianggon.netalerts.domain.AlertEvent;
import com.xianggon.netalerts.domain.AlertStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertEventRepository extends JpaRepository<AlertEvent, Long> {
    List<AlertEvent> findByStatus(AlertStatus status);
    long countByStatus(AlertStatus status);
}
