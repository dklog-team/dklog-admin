package kr.dklog.admin.dklogadmin.repository;

import kr.dklog.admin.dklogadmin.entity.SmsSendResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmsSendResponseRepository extends JpaRepository<SmsSendResponse, Long> {

    List<SmsSendResponse> findAllByOrderByRequestId();

    Page<SmsSendResponse> findAll(Specification<SmsSendResponse> smsSendResponseSpecification, Pageable pageable);
}
