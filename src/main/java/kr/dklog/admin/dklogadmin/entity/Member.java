package kr.dklog.admin.dklogadmin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String githubUsername;

    private String email;

    private String picture;

    private String role;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Builder
    public Member(Long memberId, String githubUsername, String email, String picture, String role, Student student) {
        this.memberId = memberId;
        this.githubUsername = githubUsername;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.student = student;
    }
}
