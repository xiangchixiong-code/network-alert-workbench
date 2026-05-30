package com.xianggon.netalerts.dto;

import com.xianggon.netalerts.domain.DeviceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "网络设备创建请求")
public record DeviceCreateRequest(
        @NotBlank @Schema(description = "设备名称", example = "银川核心交换机") String name,
        @NotBlank @Schema(description = "资产编号", example = "SW-YC-CORE-02") String assetCode,
        @NotBlank @Schema(description = "管理 IP", example = "10.10.1.2") String managementIp,
        @NotBlank @Schema(description = "设备类型", example = "Switch") String deviceType,
        @NotBlank @Schema(description = "安装位置", example = "银川政企机房") String location,
        @NotNull @Schema(description = "设备状态", example = "ONLINE") DeviceStatus status,
        @Schema(description = "负责团队", example = "网络运维组") String ownerTeam
) {
}
