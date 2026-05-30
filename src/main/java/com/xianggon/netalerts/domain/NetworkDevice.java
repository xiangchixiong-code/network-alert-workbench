package com.xianggon.netalerts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "network_devices")
public class NetworkDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String assetCode;
    @Column(nullable = false)
    private String managementIp;
    @Column(nullable = false)
    private String deviceType;
    @Column(nullable = false)
    private String location;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status;
    private String ownerTeam;
    private LocalDateTime lastSeenAt;

    protected NetworkDevice() {
    }

    public NetworkDevice(String name, String assetCode, String managementIp, String deviceType, String location, DeviceStatus status, String ownerTeam, LocalDateTime lastSeenAt) {
        this.name = name;
        this.assetCode = assetCode;
        this.managementIp = managementIp;
        this.deviceType = deviceType;
        this.location = location;
        this.status = status;
        this.ownerTeam = ownerTeam;
        this.lastSeenAt = lastSeenAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAssetCode() { return assetCode; }
    public String getManagementIp() { return managementIp; }
    public String getDeviceType() { return deviceType; }
    public String getLocation() { return location; }
    public DeviceStatus getStatus() { return status; }
    public String getOwnerTeam() { return ownerTeam; }
    public LocalDateTime getLastSeenAt() { return lastSeenAt; }
    public void setStatus(DeviceStatus status) { this.status = status; }
    public void updateFrom(String name, String managementIp, String deviceType, String location, DeviceStatus status, String ownerTeam) {
        this.name = name;
        this.managementIp = managementIp;
        this.deviceType = deviceType;
        this.location = location;
        this.status = status;
        this.ownerTeam = ownerTeam;
        this.lastSeenAt = LocalDateTime.now();
    }
}
