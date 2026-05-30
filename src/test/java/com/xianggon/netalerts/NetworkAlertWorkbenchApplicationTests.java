package com.xianggon.netalerts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class NetworkAlertWorkbenchApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthIsUp() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("UP"));
    }

    @Test
    void listDevicesReturnsDemoData() throws Exception {
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].assetCode").exists());
    }

    @Test
    void createAlertWorks() throws Exception {
        String body = """
                {"deviceId":1,"severity":"HIGH","title":"链路丢包","description":"上联链路出现丢包","source":"巡检"}
                """;
        mockMvc.perform(post("/api/alerts").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("OPEN"));
    }

    @Test
    void dashboardReturnsCounts() throws Exception {
        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.deviceCount").exists())
                .andExpect(jsonPath("$.data.openAlertCount").exists());
    }

    @Test
    void exportDevicesReturnsExcel() throws Exception {
        mockMvc.perform(get("/api/devices/export"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

    @Test
    void importDevicesCreatesOrUpdatesAsset() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("devices");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("设备名称");
            header.createCell(1).setCellValue("资产编号");
            header.createCell(2).setCellValue("管理IP");
            header.createCell(3).setCellValue("设备类型");
            header.createCell(4).setCellValue("状态");
            header.createCell(5).setCellValue("安装位置");
            header.createCell(6).setCellValue("负责团队");
            Row row = sheet.createRow(1);
            row.createCell(0).setCellValue("测试交换机");
            row.createCell(1).setCellValue("SW-TEST-001");
            row.createCell(2).setCellValue("10.99.1.1");
            row.createCell(3).setCellValue("Switch");
            row.createCell(4).setCellValue("ONLINE");
            row.createCell(5).setCellValue("测试机房");
            row.createCell(6).setCellValue("测试组");
            workbook.write(out);
        }
        MockMultipartFile file = new MockMultipartFile("file", "devices.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", out.toByteArray());
        mockMvc.perform(multipart("/api/devices/import").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void openApiDocsGenerated() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("网络设备告警与运维记录分析系统 API"));
    }
}
