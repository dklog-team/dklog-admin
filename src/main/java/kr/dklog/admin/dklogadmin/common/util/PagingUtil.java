package kr.dklog.admin.dklogadmin.common.util;

import lombok.Getter;

@Getter
public class PagingUtil {
    private Long totalElements;
    private int totalpages;
    private int pageNumber;
    private int pageSize;
    private int totalPageGroups;
    private int pageGroupSize = 5;
    private int pageGroup;
    private int startPage;
    private int endPage;
    private boolean existPrePageGroup;
    private boolean existNextPageGroup;

    public PagingUtil(Long totalElements, int totalpages, int pageNumber, int pageSize) {
        this.totalElements = totalElements;
        this.totalpages = totalpages;
        this.pageNumber = pageNumber + 1;
        this.pageSize = pageSize;
        this.totalPageGroups = setTotalPageGroups();
    }

    private int setTotalPageGroups() {
        if (this.totalpages % this.pageGroupSize == 0)
            return this.totalPageGroups = totalPageGroups;
        return this.totalpages/this.pageGroupSize +1;
    }

    private int setPageGroup() {
        if(this.pageNumber % this.pageGroupSize == 0)
            return this.pageNumber / this.pageGroupSize;
        return this.pageNumber / this.pageGroupSize + 1;
    }

    private int setStartPage() {
        return (this.pageGroup -1) * this.pageGroupSize +1;
    }

    private int setEndPage() {
        int endPage = this.pageGroup * this.pageGroupSize;
        if(this.totalpages < endPage)
            return this.totalpages;
        return endPage;
    }

    public boolean setExistPrePageGroup(){
        if(this.pageGroup > 1)
            return true;
        return false;
    }

    public boolean setExistNextPageGroup(){
        if(this.pageGroup < this.totalPageGroups)
            return true;
        return false;
    }

}
