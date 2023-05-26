package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.request.RequestKeywordDto;
import kr.dklog.admin.dklogadmin.entity.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> searchWith(RequestKeywordDto keywordDto){
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(keywordDto.getKeyword() != null || keywordDto.getKeywordType() != null)
                predicates.add(builder.like(root.get(keywordDto.getKeywordType()), "%"+keywordDto.getKeyword()+"%"));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
