package com.xianggon.netalerts.web;

import com.xianggon.netalerts.common.ApiResponse;
import com.xianggon.netalerts.domain.DeviceStatus;
import com.xianggon.netalerts.dto.DeviceCreateRequest;
import com.xianggon.netalerts.dto.DeviceResponse;
import com.xianggon.netalerts.dto.DeviceStatusUpdateRequest;
import com.xianggon.netalerts.service.DeviceExcelService;
import com.xianggon.netalerts.service.NetworkDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "网络设备台账")
public class DeviceController {
    private final NetworkDeviceService deviceService;
    private final DeviceExcelService excelService;

    public DeviceController(NetworkDeviceService deviceService, DeviceExcelService excelService) {
        this.deviceService = deviceService;
        this.excelService = excelService;
    }

    @GetMapping
    @Operation(summary = "查询网络设备台账")
    public ApiResponse<List<DeviceResponse>> list(@RequestParam(required = false) DeviceStatus status) {
        return ApiResponse.ok(deviceService.list(status));
    }

    @PostMapping
    @Operation(summary = "新增网络设备")
    public ApiResponse<DeviceResponse> create(@Valid @RequestBody DeviceCreateRequest request) {
        return ApiResponse.ok(deviceService.create(request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "更新设备状态")
    public ApiResponse<DeviceResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody DeviceStatusUpdateRequest request) {
        return ApiResponse.ok(deviceService.updateStatus(id, request.status()));
    }

    @GetMapping("/export")
    @Operation(summary = "导出设备台账 Excel")
    public ResponseEntity<byte[]> exportDevices() {
        byte[] bytes = excelService.exportDevices();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("network-devices.xlsx").build().toString())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "导入设备台账 Excel")
    public ApiResponse<Integer> importDevices(@RequestParam MultipartFile file) {
        return ApiResponse.ok(excelService.importDevices(file));
    }
}
