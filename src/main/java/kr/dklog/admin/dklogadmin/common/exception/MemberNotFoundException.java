package kr.dklog.admin.dklogadmin.common.exception;

public class MemberNotFoundException extends DklogException{

    private static final String MESSAGE = "회원 정보가 존재하지 않습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
