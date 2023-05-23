package kr.dklog.admin.dklogadmin.repository;

import kr.dklog.admin.dklogadmin.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
