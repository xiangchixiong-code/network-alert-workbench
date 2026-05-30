package com.xianggon.netalerts.dto;

import com.xianggon.netalerts.domain.AlertSeverity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "告警创建请求")
public record AlertCreateRequest(
        @NotNull @Schema(description = "设备 ID", example = "1") Long deviceId,
        @NotNull @Schema(description = "告警等级", example = "HIGH") AlertSeverity severity,
        @NotBlank @Schema(description = "告警标题", example = "端口流量异常") String title,
        @NotBlank @Schema(description = "告警描述", example = "上联端口流量持续超过阈值") String description,
        @Schema(description = "告警来源", example = "Zabbix") String source
) {
}
