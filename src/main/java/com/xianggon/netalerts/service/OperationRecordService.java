package com.xianggon.netalerts.service;

import com.xianggon.netalerts.dto.OperationRecordResponse;
import com.xianggon.netalerts.repository.OperationRecordRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationRecordService {
    private final OperationRecordRepository records;

    public OperationRecordService(OperationRecordRepository records) {
        this.records = records;
    }

    @Transactional(readOnly = true)
    public List<OperationRecordResponse> list() {
        return records.findAll().stream().map(OperationRecordResponse::from).toList();
    }
}
