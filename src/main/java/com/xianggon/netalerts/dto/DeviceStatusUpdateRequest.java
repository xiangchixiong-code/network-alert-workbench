package com.xianggon.netalerts.dto;

import com.xianggon.netalerts.domain.DeviceStatus;
import jakarta.validation.constraints.NotNull;

public record DeviceStatusUpdateRequest(@NotNull DeviceStatus status) {
}
