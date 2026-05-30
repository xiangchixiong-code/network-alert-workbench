package com.xianggon.netalerts.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_records")
public class OperationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String detail;
    private String operator;
    private LocalDateTime operatedAt;

    protected OperationRecord() {
    }

    public OperationRecord(String action, String detail, String operator, LocalDateTime operatedAt) {
        this.action = action;
        this.detail = detail;
        this.operator = operator;
        this.operatedAt = operatedAt;
    }

    public Long getId() { return id; }
    public String getAction() { return action; }
    public String getDetail() { return detail; }
    public String getOperator() { return operator; }
    public LocalDateTime getOperatedAt() { return operatedAt; }
}
