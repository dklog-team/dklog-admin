package kr.dklog.admin.dklogadmin.common.util;

import lombok.Getter;

@Getter
public class PagingUtil {

    private long totalElements;

    private int totalPages;

    private int pageNumber;

    private int pageSize;

    private int totalPageGroups;

    private int pageGroupSize = 5;

    private int pageGroup;

    private int startPage;

    private int endPage;

    private boolean existPrePageGroup;

    private boolean existNextPageGroup;

    public PagingUtil(long totalElements, int totalPages, int pageNumber, int pageSize) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber + 1;
        this.pageSize = pageSize;
        this.totalPageGroups = setTotalPageGroups();
        this.pageGroup = setPageGroup();
        this.startPage = setStartPage();
        this.endPage = setEndPage();
        this.existPrePageGroup = setExistPrePageGroup();
        this.existNextPageGroup = setExistNextPageGroup();
    }

    public int setTotalPageGroups() {
        if (this.totalPages % this.pageGroupSize == 0) {
            return this.totalPages / this.pageGroupSize;
        }
        return this.totalPages / this.pageGroupSize + 1;
    }

    public int setPageGroup() {
        if (this.pageNumber % this.pageGroupSize == 0) {
            return this.pageNumber / this.pageGroupSize;
        }
        return this.pageNumber / this.pageGroupSize + 1;
    }

    public int setStartPage() {
        return (this.pageGroup - 1) * this.pageGroupSize + 1;
    }

    public int setEndPage() {
        int endPageNumber = this.pageGroup * this.pageGroupSize;
        if (this.totalPages < endPageNumber) {
            return this.totalPages;
        }
        return endPageNumber;
    }

    public boolean setExistPrePageGroup() {
        return this.pageGroup > 1;
    }

    public boolean setExistNextPageGroup() {
        return this.pageGroup < this.totalPageGroups;
    }
}
