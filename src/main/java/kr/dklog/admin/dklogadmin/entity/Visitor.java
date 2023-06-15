package kr.dklog.admin.dklogadmin.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorId;

    private String ip;

    private String os;

    private String requestUrl;

    @CreatedDate
    private LocalDateTime visitedDate;

    @Builder
    public Visitor(Long visitorId, String ip, String os, String requestUrl, LocalDateTime visitedDate) {
        this.visitorId = visitorId;
        this.ip = ip;
        this.os = os;
        this.requestUrl = requestUrl;
        this.visitedDate = visitedDate;
    }

    public LocalDate getConvertedVisitedDate() {
        return this.visitedDate.toLocalDate();
    }
}