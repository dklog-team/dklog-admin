package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseRecentPostListDto {

    private List<ResponseRecentPostDto> recentPostList;

    @Builder
    public ResponseRecentPostListDto(List<ResponseRecentPostDto> recentPostList) {
        this.recentPostList = recentPostList;
    }
}
