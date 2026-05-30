package com.xianggon.netalerts.dto;

import com.xianggon.netalerts.domain.AlertStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlertStatusUpdateRequest(@NotNull AlertStatus status, @NotBlank String handler) {
}
