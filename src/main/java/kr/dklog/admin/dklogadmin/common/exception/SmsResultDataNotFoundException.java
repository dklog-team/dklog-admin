package kr.dklog.admin.dklogadmin.common.exception;

public class SmsResultDataNotFoundException extends DklogException {

    private static final String MESSAGE = "SMS 요청 결과가 존재하지 않습니다.";

    public SmsResultDataNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
