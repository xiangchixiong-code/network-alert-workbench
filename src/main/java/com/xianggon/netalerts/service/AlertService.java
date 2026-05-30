package com.xianggon.netalerts.service;

import com.xianggon.netalerts.common.ResourceNotFoundException;
import com.xianggon.netalerts.domain.AlertEvent;
import com.xianggon.netalerts.domain.AlertStatus;
import com.xianggon.netalerts.domain.NetworkDevice;
import com.xianggon.netalerts.dto.AlertCreateRequest;
import com.xianggon.netalerts.dto.AlertResponse;
import com.xianggon.netalerts.repository.AlertEventRepository;
import com.xianggon.netalerts.repository.NetworkDeviceRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertService {
    private final AlertEventRepository alerts;
    private final NetworkDeviceRepository devices;

    public AlertService(AlertEventRepository alerts, NetworkDeviceRepository devices) {
        this.alerts = alerts;
        this.devices = devices;
    }

    @Transactional(readOnly = true)
    public List<AlertResponse> list(AlertStatus status) {
        List<AlertEvent> result = status == null ? alerts.findAll() : alerts.findByStatus(status);
        return result.stream().map(AlertResponse::from).toList();
    }

    @Transactional
    public AlertResponse create(AlertCreateRequest request) {
        NetworkDevice device = devices.findById(request.deviceId()).orElseThrow(() -> new ResourceNotFoundException("网络设备不存在: " + request.deviceId()));
        AlertEvent alert = alerts.save(new AlertEvent(device, request.severity(), AlertStatus.OPEN, request.title(), request.description(), request.source(), LocalDateTime.now(), null, null));
        return AlertResponse.from(alert);
    }

    @Transactional
    public AlertResponse updateStatus(Long id, AlertStatus status, String handler) {
        AlertEvent alert = alerts.findById(id).orElseThrow(() -> new ResourceNotFoundException("告警不存在: " + id));
        alert.updateStatus(status, handler);
        return AlertResponse.from(alert);
    }
}
