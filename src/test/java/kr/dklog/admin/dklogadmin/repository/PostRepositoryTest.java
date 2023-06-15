package kr.dklog.admin.dklogadmin.repository;

import kr.dklog.admin.dklogadmin.entity.Post;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    @Order(1)
    void 전체조회(){
        postRepository.findAll();
    }

    @Test
    @Order(2)
    @Transactional
    void 삭제(){
        List<Post> posts = postRepository.findAll();
        postRepository.deleteAllById(List.of(posts.get(0).getPostId(),posts.get(1).getPostId()));
    }
}