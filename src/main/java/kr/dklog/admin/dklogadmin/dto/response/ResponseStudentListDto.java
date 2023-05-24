package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseStudentListDto {

    List<ResponseStudentDto> studentList;
    @Builder
    public ResponseStudentListDto(List<ResponseStudentDto> studentList) {
        this.studentList = studentList;
    }
}
