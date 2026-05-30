package com.xianggon.netalerts.dto;

import com.xianggon.netalerts.domain.AlertEvent;
import com.xianggon.netalerts.domain.AlertSeverity;
import com.xianggon.netalerts.domain.AlertStatus;
import java.time.LocalDateTime;

public record AlertResponse(Long id, Long deviceId, String deviceName, AlertSeverity severity, AlertStatus status, String title, String description, String source, LocalDateTime occurredAt, LocalDateTime handledAt, String handler) {
    public static AlertResponse from(AlertEvent alert) {
        return new AlertResponse(alert.getId(), alert.getDevice().getId(), alert.getDevice().getName(), alert.getSeverity(), alert.getStatus(), alert.getTitle(), alert.getDescription(), alert.getSource(), alert.getOccurredAt(), alert.getHandledAt(), alert.getHandler());
    }
}
