package com.xianggon.netalerts.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "告警处理状态")
public enum AlertStatus {
    OPEN, PROCESSING, CLOSED
}
