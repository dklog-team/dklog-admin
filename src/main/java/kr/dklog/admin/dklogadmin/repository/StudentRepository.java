package kr.dklog.admin.dklogadmin.repository;

import kr.dklog.admin.dklogadmin.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    public List<Student> findAll(Specification<Student> spec);
}
