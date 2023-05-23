package kr.dklog.admin.dklogadmin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String name;

    private String phoneNumber;

    private Integer semester;

    private String authCode;

    private String githubUsername;

    private String authStatus;

    @Builder
    public Student(Long studentId, String name, String phoneNumber, Integer semester, String authCode, String githubUsername, String authStatus) {
        this.studentId = studentId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.semester = semester;
        this.authCode = authCode;
        this.githubUsername = githubUsername;
        this.authStatus = authStatus;
    }
}
