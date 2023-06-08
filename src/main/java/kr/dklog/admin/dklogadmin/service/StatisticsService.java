package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.response.*;
import kr.dklog.admin.dklogadmin.entity.Post;
import kr.dklog.admin.dklogadmin.entity.Student;
import kr.dklog.admin.dklogadmin.entity.Visitor;
import kr.dklog.admin.dklogadmin.repository.PopularPostInterface;
import kr.dklog.admin.dklogadmin.repository.PostRepository;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import kr.dklog.admin.dklogadmin.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${url.dklog}")
    private String dklogUrl;

    private final VisitorRepository visitorRepository;

    private final PostRepository postRepository;

    private final StudentRepository studentRepository;

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

        ResponseVisitorStatisticsDto responseData = ResponseVisitorStatisticsDto.builder()
                .dateList(dateList)
                .countList(countList)
                .build();

        return responseData;
    }

    public ResponsePopularPostListDto getPopularPostList() {
        List<PopularPostInterface> popularPostList = postRepository.findPopularPostList();

        List<ResponsePopularPostDto> popularPostDtoList = popularPostList.stream().map((post) -> ResponsePopularPostDto.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .views(post.getViews())
                        .commentCount(post.getCommentCount())
                        .point(post.getPoint())
                        .postUrl(dklogUrl + "/post/" + post.getPostId())
                        .build())
                .collect(Collectors.toList());

        ResponsePopularPostListDto responseData = ResponsePopularPostListDto.builder()
                .popularPostList(popularPostDtoList)
                .build();

        return responseData;
    }

    public ResponseRecentPostListDto getRecentPostList() {
        List<Post> recentPostList = postRepository.findTop4ByOrderByPostIdDesc();

        List<ResponseRecentPostDto> responseRecentPostDtoList = recentPostList.stream()
                .map(post -> post.toResponseRecentPostDto(dklogUrl + "/post/" + post.getPostId()))
                .collect(Collectors.toList());

        ResponseRecentPostListDto responseData = ResponseRecentPostListDto.builder()
                .recentPostList(responseRecentPostDtoList)
                .build();

        return responseData;
    }

    public ResponseStudentAuthDataDto getStudentAuthData() {
        List<Student> studentList = studentRepository.findAll();
        List<Student> noAuthStudentList = studentList.stream().filter(student -> student.getAuthStatus().equals("N")).collect(Collectors.toList());

        long totalCount = studentList.size();
        long yesCount = studentList.stream().filter(student -> student.getAuthStatus().equals("Y")).count();
        long noCount = noAuthStudentList.size();
        double percent = (yesCount / (double) totalCount) * 100;

        List<ResponseNoAuthStudentDto> noAuthStudentDtoList = noAuthStudentList.stream()
                .map(Student::toResponseNoAuthStudentDto)
                .collect(Collectors.toList());

        ResponseStudentAuthDataDto response = ResponseStudentAuthDataDto.builder()
                .noAuthStudentList(noAuthStudentDtoList)
                .totalCount(totalCount)
                .yesCount(yesCount)
                .noCount(noCount)
                .percent(percent)
                .build();

        return response;
    }
}
