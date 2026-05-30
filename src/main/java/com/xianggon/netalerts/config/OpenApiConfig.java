package com.xianggon.netalerts.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI networkAlertOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("网络设备告警与运维记录分析系统 API")
                        .version("1.0.0")
                        .description("模拟运营商/企业网络运维场景，包含网络设备台账、告警记录、处理状态、运维日志、统计看板和 Excel 导入导出。"))
                .servers(List.of(new Server().url("http://localhost:8081").description("本地演示环境")));
    }
}
