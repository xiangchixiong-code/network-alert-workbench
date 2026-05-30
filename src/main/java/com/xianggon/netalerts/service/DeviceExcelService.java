package com.xianggon.netalerts.service;

import com.xianggon.netalerts.domain.DeviceStatus;
import com.xianggon.netalerts.domain.NetworkDevice;
import com.xianggon.netalerts.repository.NetworkDeviceRepository;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DeviceExcelService {
    private final NetworkDeviceRepository devices;

    public DeviceExcelService(NetworkDeviceRepository devices) {
        this.devices = devices;
    }

    public byte[] exportDevices() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("devices");
            Row header = sheet.createRow(0);
            String[] headers = {"设备名称", "资产编号", "管理IP", "设备类型", "状态", "安装位置", "负责团队"};
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }
            List<NetworkDevice> rows = devices.findAll();
            for (int i = 0; i < rows.size(); i++) {
                NetworkDevice d = rows.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(d.getName());
                row.createCell(1).setCellValue(d.getAssetCode());
                row.createCell(2).setCellValue(d.getManagementIp());
                row.createCell(3).setCellValue(d.getDeviceType());
                row.createCell(4).setCellValue(d.getStatus().name());
                row.createCell(5).setCellValue(d.getLocation());
                row.createCell(6).setCellValue(d.getOwnerTeam());
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("导出 Excel 失败", e);
        }
    }

    public int importDevices(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            int changed = 0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String assetCode = text(row, 1);
                if (assetCode.isBlank()) {
                    continue;
                }
                NetworkDevice device = devices.findByAssetCode(assetCode)
                        .orElse(new NetworkDevice(text(row, 0), assetCode, text(row, 2), text(row, 3), text(row, 5), status(text(row, 4)), text(row, 6), LocalDateTime.now()));
                device.updateFrom(text(row, 0), text(row, 2), text(row, 3), text(row, 5), status(text(row, 4)), text(row, 6));
                devices.save(device);
                changed++;
            }
            return changed;
        } catch (IOException e) {
            throw new IllegalStateException("导入 Excel 失败", e);
        }
    }

    private String text(Row row, int index) {
        return row.getCell(index) == null ? "" : row.getCell(index).toString().trim();
    }

    private DeviceStatus status(String value) {
        if (value == null || value.isBlank()) {
            return DeviceStatus.ONLINE;
        }
        return DeviceStatus.valueOf(value.trim().toUpperCase());
    }
}
