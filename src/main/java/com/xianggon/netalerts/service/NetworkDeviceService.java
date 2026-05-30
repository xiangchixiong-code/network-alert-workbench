package com.xianggon.netalerts.service;

import com.xianggon.netalerts.common.ResourceNotFoundException;
import com.xianggon.netalerts.domain.DeviceStatus;
import com.xianggon.netalerts.domain.NetworkDevice;
import com.xianggon.netalerts.dto.DeviceCreateRequest;
import com.xianggon.netalerts.dto.DeviceResponse;
import com.xianggon.netalerts.repository.NetworkDeviceRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NetworkDeviceService {
    private final NetworkDeviceRepository devices;

    public NetworkDeviceService(NetworkDeviceRepository devices) {
        this.devices = devices;
    }

    @Transactional(readOnly = true)
    public List<DeviceResponse> list(DeviceStatus status) {
        List<NetworkDevice> result = status == null ? devices.findAll() : devices.findByStatus(status);
        return result.stream().map(DeviceResponse::from).toList();
    }

    @Transactional
    public DeviceResponse create(DeviceCreateRequest request) {
        NetworkDevice saved = devices.save(new NetworkDevice(request.name(), request.assetCode(), request.managementIp(), request.deviceType(), request.location(), request.status(), request.ownerTeam(), LocalDateTime.now()));
        return DeviceResponse.from(saved);
    }

    @Transactional
    public DeviceResponse updateStatus(Long id, DeviceStatus status) {
        NetworkDevice device = devices.findById(id).orElseThrow(() -> new ResourceNotFoundException("网络设备不存在: " + id));
        device.setStatus(status);
        return DeviceResponse.from(device);
    }
}
