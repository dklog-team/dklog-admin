package kr.dklog.admin.dklogadmin.common.exception;

public class DuplicateAdminUsernameException extends DklogException {

    private static final String MESSAGE = "이미 존재하는 관리자 아이디입니다. 다른 아이디를 사용해주세요.";

    public DuplicateAdminUsernameException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
