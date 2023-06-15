package kr.dklog.admin.dklogadmin.common.exception;

public class CommentNotFoundException extends DklogException {
    private final static String MESSAGE = "댓글이 존재하지 않습니다.";

    public CommentNotFoundException() { super(MESSAGE); }

    @Override
    public int getStatusCode() { return 404; }
}
