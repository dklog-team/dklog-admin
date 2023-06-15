package kr.dklog.admin.dklogadmin.common.exception;

public class AdminNotFoundException extends DklogException {

    private static final String MESSAGE = "존재하지 않는 관리자입니다.";

    public AdminNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
