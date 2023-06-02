package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponsePopularPostListDto {

    private List<ResponsePopularPostDto> popularPostList;

    @Builder
    public ResponsePopularPostListDto(List<ResponsePopularPostDto> popularPostList) {
        this.popularPostList = popularPostList;
    }
}
