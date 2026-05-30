package com.xianggon.netalerts.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "告警等级")
public enum AlertSeverity {
    LOW, MEDIUM, HIGH, CRITICAL
}
