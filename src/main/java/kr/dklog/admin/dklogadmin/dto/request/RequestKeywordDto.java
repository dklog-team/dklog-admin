package kr.dklog.admin.dklogadmin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RequestKeywordDto {
    private String keyword;
    private String keywordType;
}
