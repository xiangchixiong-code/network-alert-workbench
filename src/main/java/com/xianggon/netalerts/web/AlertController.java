package com.xianggon.netalerts.web;

import com.xianggon.netalerts.common.ApiResponse;
import com.xianggon.netalerts.domain.AlertStatus;
import com.xianggon.netalerts.dto.AlertCreateRequest;
import com.xianggon.netalerts.dto.AlertResponse;
import com.xianggon.netalerts.dto.AlertStatusUpdateRequest;
import com.xianggon.netalerts.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "告警记录")
public class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    @Operation(summary = "查询告警记录")
    public ApiResponse<List<AlertResponse>> list(@RequestParam(required = false) AlertStatus status) {
        return ApiResponse.ok(alertService.list(status));
    }

    @PostMapping
    @Operation(summary = "创建告警记录")
    public ApiResponse<AlertResponse> create(@Valid @RequestBody AlertCreateRequest request) {
        return ApiResponse.ok(alertService.create(request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "更新告警处理状态")
    public ApiResponse<AlertResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody AlertStatusUpdateRequest request) {
        return ApiResponse.ok(alertService.updateStatus(id, request.status(), request.handler()));
    }
}
