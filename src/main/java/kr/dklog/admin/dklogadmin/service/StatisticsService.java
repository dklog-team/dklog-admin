package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.response.ResponseVisitorStatisticsDto;
import kr.dklog.admin.dklogadmin.entity.Visitor;
import kr.dklog.admin.dklogadmin.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final VisitorRepository visitorRepository;

    public Long getTotalVisitorCount() {
        return visitorRepository.count();
    }

    public Long getYesterdayVisitorCount() {
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.of(23, 59, 59));
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.of(0, 0, 0));

        return visitorRepository.countByVisitedDateBetween(startDate, endDate);
    }

    public Long getTodayVisitorCount() {
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));

        return visitorRepository.countByVisitedDateBetween(startDate, endDate);
    }

    public ResponseVisitorStatisticsDto getVisitorStatistics() {
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusMonths(1L), LocalTime.of(0, 0, 0));

        List<Visitor> visitorList = visitorRepository.findAllByVisitedDateBetween(startDate, endDate);
        Map<LocalDate, Long> map = visitorList.stream().collect(Collectors.groupingBy(Visitor::getConvertedVisitedDate, Collectors.counting()));
        log.info(map.toString());

        ArrayList<LocalDate> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList);

        List<String> dateList = new ArrayList<>();
        List<Long> countList = new ArrayList<>();
        for (LocalDate localDate : keyList) {
            dateList.add(localDate.format(DateTimeFormatter.ofPattern("MM.dd")));
            countList.add(map.get(localDate));
        }

        ResponseVisitorStatisticsDto response = ResponseVisitorStatisticsDto.builder()
                .dateList(dateList)
                .countList(countList)
                .build();

        return response;
    }
}
