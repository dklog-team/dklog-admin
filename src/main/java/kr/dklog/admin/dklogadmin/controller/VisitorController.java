package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.common.data.AdminData;
import kr.dklog.admin.dklogadmin.dto.common.ResponseCountDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseVisitorStatisticsDto;
import kr.dklog.admin.dklogadmin.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/visitor")
public class VisitorController {

    private final StatisticsService statisticsService;

    @GetMapping("/count/all")
    public ResponseEntity<ResponseCountDto> totalCount(AdminData adminData) {
        Long totalVisitorCount = statisticsService.getTotalVisitorCount();
        ResponseCountDto response = ResponseCountDto.builder()
                .count(totalVisitorCount)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/yesterday")
    public ResponseEntity<ResponseCountDto> yesterdayCount(AdminData adminData) {
        Long visitorCountByDate = statisticsService.getYesterdayVisitorCount();
        ResponseCountDto response = ResponseCountDto.builder()
                .count(visitorCountByDate)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/today")
    public ResponseEntity<ResponseCountDto> todayCount(AdminData adminData) {
        Long visitorCountByDate = statisticsService.getTodayVisitorCount();
        ResponseCountDto response = ResponseCountDto.builder()
                .count(visitorCountByDate)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/statistics")
    public ResponseEntity<ResponseVisitorStatisticsDto> statistics(AdminData adminData) {
        ResponseVisitorStatisticsDto response = statisticsService.getVisitorStatistics();
        return ResponseEntity.ok(response);
    }
}
