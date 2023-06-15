package kr.dklog.admin.dklogadmin.repository;

import kr.dklog.admin.dklogadmin.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    long countByVisitedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Visitor> findAllByVisitedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
