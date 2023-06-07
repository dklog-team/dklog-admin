package kr.dklog.admin.dklogadmin.common.exception;

public class InvalidAdminUsernameException extends DklogException{

    private static final String MESSAGE = "관리자 아이디가 올바르지 않습니다.";

    public InvalidAdminUsernameException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
