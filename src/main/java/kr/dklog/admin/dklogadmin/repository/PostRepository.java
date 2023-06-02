package kr.dklog.admin.dklogadmin.repository;

import kr.dklog.admin.dklogadmin.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    @Query(value = "select post_id as postId, title, views, (select count(*) from comment where comment.post_id = p.post_id) as commentCount, views + (select count(*) from comment where comment.post_id = p.post_id) as point " +
            "from post p " +
            "order by point desc " +
            "limit 5 ",
            nativeQuery = true)
    List<PopularPostInterface> findPopularPostList();

    List<Post> findTop4ByOrderByPostIdDesc();
}
