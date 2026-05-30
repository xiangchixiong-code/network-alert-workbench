package com.xianggon.netalerts.dto;

import com.xianggon.netalerts.domain.DeviceStatus;
import com.xianggon.netalerts.domain.NetworkDevice;
import java.time.LocalDateTime;

public record DeviceResponse(Long id, String name, String assetCode, String managementIp, String deviceType, String location, DeviceStatus status, String ownerTeam, LocalDateTime lastSeenAt) {
    public static DeviceResponse from(NetworkDevice device) {
        return new DeviceResponse(device.getId(), device.getName(), device.getAssetCode(), device.getManagementIp(), device.getDeviceType(), device.getLocation(), device.getStatus(), device.getOwnerTeam(), device.getLastSeenAt());
    }
}
