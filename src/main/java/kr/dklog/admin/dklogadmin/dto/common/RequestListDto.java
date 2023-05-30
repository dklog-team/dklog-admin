package kr.dklog.admin.dklogadmin.dto.common;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RequestListDto {

    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private final Integer pageSize = 10;
    @Builder.Default
    private Sort.Direction sortDirection = Sort.Direction.DESC;

    private String column;

    public Integer getPage() {
        page = page - 1;
        if (page < 0) {
            page = 0;
        }
        return page;
    }

    public void setSortDirection(String sortDirection) {
        if (sortDirection.equalsIgnoreCase("asc")) {
            this.sortDirection = Sort.Direction.ASC;
        }
    }
}
