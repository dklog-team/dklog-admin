package kr.dklog.admin.dklogadmin.repository;

import kr.dklog.admin.dklogadmin.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
