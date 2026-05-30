package com.xianggon.netalerts.service;

import com.xianggon.netalerts.domain.AlertStatus;
import com.xianggon.netalerts.domain.DeviceStatus;
import com.xianggon.netalerts.dto.DashboardResponse;
import com.xianggon.netalerts.repository.AlertEventRepository;
import com.xianggon.netalerts.repository.NetworkDeviceRepository;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final NetworkDeviceRepository devices;
    private final AlertEventRepository alerts;

    public DashboardService(NetworkDeviceRepository devices, AlertEventRepository alerts) {
        this.devices = devices;
        this.alerts = alerts;
    }

    public DashboardResponse dashboard() {
        Map<String, Long> statusCount = Arrays.stream(DeviceStatus.values())
                .collect(Collectors.toMap(Enum::name, status -> (long) devices.findByStatus(status).size()));
        return new DashboardResponse(devices.count(), alerts.countByStatus(AlertStatus.OPEN), alerts.countByStatus(AlertStatus.PROCESSING), alerts.countByStatus(AlertStatus.CLOSED), statusCount);
    }
}
