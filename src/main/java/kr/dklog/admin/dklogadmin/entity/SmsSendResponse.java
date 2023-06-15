package kr.dklog.admin.dklogadmin.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmsSendResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smsSendResponseId;

    private String requestId;

    private String requestTime;

    private String statusCode;

    private String statusName;
}
