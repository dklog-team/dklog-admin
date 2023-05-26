package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDeleteDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentRegisterDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentUpdateDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentRegisterDto;
import kr.dklog.admin.dklogadmin.entity.Student;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public ResponseStudentRegisterDto register(RequestStudentRegisterDto registerDto){
        Student student = Student.builder()
                .name(registerDto.getName())
                .phoneNumber(registerDto.getPhoneNumber())
                .semester(registerDto.getSemester())
                .build();

        Student savedStudent = studentRepository.save(student);

        return student.toResponseStudentRegisterDto(savedStudent);
    }

    public ResponseStudentListDto getList(RequestStudentDto requestStudentDto) {
        List<Student> studentList = studentRepository.findAll(searchWith(requestStudentDto));


        return ResponseStudentListDto.builder()
                .studentList(studentList.stream().map(student -> student.toResponseStudentDto(student))
                        .collect(Collectors.toList())).build();
    }

    @Transactional
    public void edit(Long studentId, RequestStudentUpdateDto requestStudentUpdateDto) {
        Student student = studentRepository.findById(studentId).orElseThrow(RuntimeException::new);

        student.update(requestStudentUpdateDto);
    }

    @Transactional
    public void remove(RequestStudentDeleteDto requestStudentDeleteDto) {
        studentRepository.deleteAllById(requestStudentDeleteDto.getStudentIds());
    }

    public Specification<Student> searchWith(RequestStudentDto requestStudentDto) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(requestStudentDto.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + requestStudentDto.getName() + "%"));
            }
            if (requestStudentDto.getSemester() != null) {
                predicates.add(criteriaBuilder.equal(root.get("semester"), requestStudentDto.getSemester()));
            }
            if (predicates.isEmpty()){
                if ("asc".equalsIgnoreCase(requestStudentDto.getSortDirection())) {
                    query.orderBy(criteriaBuilder.asc(root.get("studentId")));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get("studentId")));
                }
            } else {
                query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
                if ("desc".equalsIgnoreCase(requestStudentDto.getSortDirection())) {
                    query.orderBy(criteriaBuilder.desc(root.get("name")));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get("name")));
                }
            }

            return query.getRestriction();
        });
    }
}