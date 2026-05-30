package com.xianggon.netalerts.web;

import com.xianggon.netalerts.common.ApiResponse;
import com.xianggon.netalerts.dto.DashboardResponse;
import com.xianggon.netalerts.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "统计看板")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Operation(summary = "查询设备和告警统计")
    public ApiResponse<DashboardResponse> dashboard() {
        return ApiResponse.ok(dashboardService.dashboard());
    }
}
