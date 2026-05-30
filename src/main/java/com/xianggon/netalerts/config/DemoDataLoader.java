package com.xianggon.netalerts.config;

import com.xianggon.netalerts.domain.AlertEvent;
import com.xianggon.netalerts.domain.AlertSeverity;
import com.xianggon.netalerts.domain.AlertStatus;
import com.xianggon.netalerts.domain.DeviceStatus;
import com.xianggon.netalerts.domain.NetworkDevice;
import com.xianggon.netalerts.domain.OperationRecord;
import com.xianggon.netalerts.repository.AlertEventRepository;
import com.xianggon.netalerts.repository.NetworkDeviceRepository;
import com.xianggon.netalerts.repository.OperationRecordRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataLoader {
    @Bean
    CommandLineRunner demoData(NetworkDeviceRepository devices, AlertEventRepository alerts, OperationRecordRepository records) {
        return args -> {
            if (devices.count() > 0) {
                return;
            }
            NetworkDevice core = devices.save(new NetworkDevice("银川核心交换机", "SW-YC-CORE-01", "10.10.1.1", "Switch", "银川政企机房", DeviceStatus.ONLINE, "网络运维组", LocalDateTime.now().minusMinutes(5)));
            NetworkDevice firewall = devices.save(new NetworkDevice("中卫出口防火墙", "FW-ZW-EDGE-01", "10.20.1.254", "Firewall", "中卫汇聚节点", DeviceStatus.WARNING, "安全支撑组", LocalDateTime.now().minusMinutes(18)));
            NetworkDevice ap = devices.save(new NetworkDevice("园区无线控制器", "AC-PARK-02", "10.30.5.10", "Wireless AC", "智慧园区弱电间", DeviceStatus.OFFLINE, "政企交付组", LocalDateTime.now().minusHours(2)));
            alerts.saveAll(List.of(
                    new AlertEvent(firewall, AlertSeverity.HIGH, AlertStatus.OPEN, "出口防火墙会话数过高", "近 10 分钟会话数持续超过阈值，需要检查访问流量和安全策略。", "Zabbix", LocalDateTime.now().minusMinutes(20), null, null),
                    new AlertEvent(ap, AlertSeverity.MEDIUM, AlertStatus.PROCESSING, "无线控制器离线", "园区无线控制器心跳丢失，疑似供电或链路异常。", "巡检系统", LocalDateTime.now().minusHours(2), null, "曹博轩"),
                    new AlertEvent(core, AlertSeverity.LOW, AlertStatus.CLOSED, "端口错误包增多", "核心交换机 Gi1/0/24 错误包短时增加，已观察恢复。", "Prometheus", LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(22), "值班人员")
            ));
            records.saveAll(List.of(
                    new OperationRecord("新增设备", "录入银川核心交换机设备台账", "曹博轩", LocalDateTime.now().minusDays(2)),
                    new OperationRecord("告警处理", "跟进中卫出口防火墙会话数过高告警", "曹博轩", LocalDateTime.now().minusMinutes(15)),
                    new OperationRecord("Excel导入", "批量导入园区网络设备台账", "系统", LocalDateTime.now().minusHours(3))
            ));
        };
    }
}
