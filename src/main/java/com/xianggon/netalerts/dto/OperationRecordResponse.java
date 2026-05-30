package com.xianggon.netalerts.dto;

import com.xianggon.netalerts.domain.OperationRecord;
import java.time.LocalDateTime;

public record OperationRecordResponse(Long id, String action, String detail, String operator, LocalDateTime operatedAt) {
    public static OperationRecordResponse from(OperationRecord record) {
        return new OperationRecordResponse(record.getId(), record.getAction(), record.getDetail(), record.getOperator(), record.getOperatedAt());
    }
}
