package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.exception.StudentNotFoundException;
import kr.dklog.admin.dklogadmin.common.util.PagingUtil;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDeleteDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentRegisterDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentUpdateDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentRegisterDto;
import kr.dklog.admin.dklogadmin.entity.Student;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        if (requestStudentDto.getColumn() == null || "".equals(requestStudentDto.getColumn())) {
            requestStudentDto.setColumn("studentId");
        }
        PageRequest pageable = PageRequest.of(requestStudentDto.getPage(), requestStudentDto.getPageSize(), requestStudentDto.getSortDirection(), requestStudentDto.getColumn());
        Page<Student> studentList = studentRepository.findAll(searchWith(requestStudentDto), pageable);


        return ResponseStudentListDto.builder()
                .pagingUtil(new PagingUtil(studentList.getTotalElements(), studentList.getTotalPages(), studentList.getNumber(), studentList.getSize()))
                .studentList(studentList.getContent().stream().map(student -> student.toResponseStudentDto(student))
                        .collect(Collectors.toList())).build();
    }

    @Transactional
    public void edit(Long studentId, RequestStudentUpdateDto requestStudentUpdateDto) {
        Student student = studentRepository.findById(studentId).
                orElseThrow(StudentNotFoundException::new);

        student.update(requestStudentUpdateDto);
    }

    @Transactional
    public void remove(RequestStudentDeleteDto requestStudentDeleteDto) {
        try {
            studentRepository.deleteAllById(requestStudentDeleteDto.getStudentIds());
        } catch (EmptyResultDataAccessException e){
            throw new StudentNotFoundException();
        }
    }

    private Specification<Student> searchWith(RequestStudentDto requestStudentDto) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(requestStudentDto.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + requestStudentDto.getName() + "%"));
            }
            if (requestStudentDto.getSemester() != null) {
                predicates.add(criteriaBuilder.equal(root.get("semester"), requestStudentDto.getSemester()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}