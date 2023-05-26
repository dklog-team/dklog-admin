package kr.dklog.admin.dklogadmin.dto.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@ToString
public class RequestPageDto {
    private Integer page = 1;     // 페이지 번호
    private final Integer pageSize = 10; // 한 페이지 갯수
    private Sort.Direction dir = Sort.Direction.DESC;
    private String column = "postId";

    public void setPage(Integer page) {
        if(page < 1)
            page = 1;
        this.page = page;
    }

    public void setDir(String dir) {
        Sort.Direction direction = null;
        if(dir.equals("reverse")){
            direction = Sort.Direction.ASC;
        }else{
            direction = Sort.Direction.DESC;
        }
        this.dir = direction;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
