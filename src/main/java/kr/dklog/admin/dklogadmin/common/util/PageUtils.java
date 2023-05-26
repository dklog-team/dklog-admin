package kr.dklog.admin.dklogadmin.common.util;

import kr.dklog.admin.dklogadmin.dto.common.RequestPageDto;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@ToString
public class PageUtils {
    public static Pageable setPageable(RequestPageDto pageDto){
        return PageRequest.of(pageDto.getPage()-1, pageDto.getPageSize(), pageDto.getDir(), pageDto.getColumn());
    }
}