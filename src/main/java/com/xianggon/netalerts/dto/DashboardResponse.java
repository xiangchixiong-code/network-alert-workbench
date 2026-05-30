package com.xianggon.netalerts.dto;

import java.util.Map;

public record DashboardResponse(long deviceCount, long openAlertCount, long processingAlertCount, long closedAlertCount, Map<String, Long> deviceStatusCount) {
}
