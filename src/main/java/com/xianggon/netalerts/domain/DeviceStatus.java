package com.xianggon.netalerts.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "网络设备状态")
public enum DeviceStatus {
    ONLINE, WARNING, OFFLINE, MAINTENANCE
}
