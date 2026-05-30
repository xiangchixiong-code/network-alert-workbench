package com.xianggon.netalerts.web;

import com.xianggon.netalerts.common.ApiResponse;
import com.xianggon.netalerts.dto.OperationRecordResponse;
import com.xianggon.netalerts.service.OperationRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operation-records")
@Tag(name = "运维记录")
public class OperationRecordController {
    private final OperationRecordService recordService;

    public OperationRecordController(OperationRecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    @Operation(summary = "查询运维操作记录")
    public ApiResponse<List<OperationRecordResponse>> list() {
        return ApiResponse.ok(recordService.list());
    }
}
