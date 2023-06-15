package kr.dklog.admin.dklogadmin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestPostDeleteDto {
    private List<Long> postIds;
}
