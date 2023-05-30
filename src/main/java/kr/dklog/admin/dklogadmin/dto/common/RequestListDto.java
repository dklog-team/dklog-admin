package kr.dklog.admin.dklogadmin.dto.common;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class RequestListDto {

    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private final Integer pageSize = 10;
    @Builder.Default
    private Sort.Direction sortDirection = Sort.Direction.DESC;

    private String column;

    public void setPage(Integer page) {
        this.page = page - 1;
        if (page < 0) {
            this.page = 0;
        }
    }

    public void setSortDirection(String sortDirection) {
        if (sortDirection.equalsIgnoreCase("asc")) {
            this.sortDirection = Sort.Direction.ASC;
        }
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
