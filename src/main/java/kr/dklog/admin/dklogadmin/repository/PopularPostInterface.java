package kr.dklog.admin.dklogadmin.repository;

public interface PopularPostInterface {

    Long getPostId();

    String getTitle();

    Integer getViews();

    Integer getCommentCount();

    Integer getPoint();
}
