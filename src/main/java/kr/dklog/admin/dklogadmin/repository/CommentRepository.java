package kr.dklog.admin.dklogadmin.repository;

import kr.dklog.admin.dklogadmin.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}