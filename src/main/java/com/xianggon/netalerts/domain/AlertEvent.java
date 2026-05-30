package com.xianggon.netalerts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "alert_events")
public class AlertEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private NetworkDevice device;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertSeverity severity;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus status;
    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String description;
    private String source;
    private LocalDateTime occurredAt;
    private LocalDateTime handledAt;
    private String handler;

    protected AlertEvent() {
    }

    public AlertEvent(NetworkDevice device, AlertSeverity severity, AlertStatus status, String title, String description, String source, LocalDateTime occurredAt, LocalDateTime handledAt, String handler) {
        this.device = device;
        this.severity = severity;
        this.status = status;
        this.title = title;
        this.description = description;
        this.source = source;
        this.occurredAt = occurredAt;
        this.handledAt = handledAt;
        this.handler = handler;
    }

    public Long getId() { return id; }
    public NetworkDevice getDevice() { return device; }
    public AlertSeverity getSeverity() { return severity; }
    public AlertStatus getStatus() { return status; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSource() { return source; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public LocalDateTime getHandledAt() { return handledAt; }
    public String getHandler() { return handler; }
    public void updateStatus(AlertStatus status, String handler) {
        this.status = status;
        this.handler = handler;
        if (status == AlertStatus.CLOSED) {
            this.handledAt = LocalDateTime.now();
        }
    }
}
