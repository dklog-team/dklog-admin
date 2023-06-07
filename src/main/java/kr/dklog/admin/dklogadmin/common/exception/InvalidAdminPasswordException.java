package kr.dklog.admin.dklogadmin.common.exception;

public class InvalidAdminPasswordException extends DklogException{

    private static final String MESSAGE = "비밀번호가 올바르지 않습니다.";

    public InvalidAdminPasswordException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
