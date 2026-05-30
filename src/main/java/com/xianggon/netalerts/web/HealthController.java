package com.xianggon.netalerts.web;

import com.xianggon.netalerts.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "健康检查")
public class HealthController {
    @GetMapping("/health")
    @Operation(summary = "服务健康检查")
    public ApiResponse<Map<String, String>> health() {
        return ApiResponse.ok(Map.of("service", "network-alert-workbench", "status", "UP"));
    }
}
