package kr.dklog.admin.dklogadmin.dto.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@ToString
public class RequestListDto {
    private Integer page = 1;     // 페이지 번호
    private final Integer pageSize = 10; // 한 페이지 갯수
    private Sort.Direction dir = Sort.Direction.DESC;
    private String column = "postId";

    public void setPage(Integer page) {
        if(page < 0)
            page = 0;
        this.page = page;
    }

    public void setDir(String dir) {
        if(dir.equalsIgnoreCase("asc")){
            this.dir = Sort.Direction.ASC;
        }
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
