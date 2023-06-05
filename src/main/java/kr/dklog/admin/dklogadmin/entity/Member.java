package kr.dklog.admin.dklogadmin.entity;

import kr.dklog.admin.dklogadmin.dto.response.ResponseMemberDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String username;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(Long memberId, String githubUsername, String email, String picture, String role, String username, Student student) {
        this.memberId = memberId;
        this.githubUsername = githubUsername;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.student = student;
        this.username = username;
    }

    public ResponseMemberDto toResponseMemberDto(Member member) {
        return ResponseMemberDto.builder()
                .memberId(member.getMemberId())
                .githubUsername(member.getGithubUsername())
                .email(member.getEmail())
                .picture(member.getPicture())
                .role(member.getRole())
                .username(member.getUsername())
                .build();
    }

    public void changeStudentIdNull() {
        this.student = null;
    }
}
