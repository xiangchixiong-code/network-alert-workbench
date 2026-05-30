package com.xianggon.netalerts.repository;

import com.xianggon.netalerts.domain.OperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRecordRepository extends JpaRepository<OperationRecord, Long> {
}
