package com.xianggon.netalerts.repository;

import com.xianggon.netalerts.domain.DeviceStatus;
import com.xianggon.netalerts.domain.NetworkDevice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkDeviceRepository extends JpaRepository<NetworkDevice, Long> {
    Optional<NetworkDevice> findByAssetCode(String assetCode);
    List<NetworkDevice> findByStatus(DeviceStatus status);
}
