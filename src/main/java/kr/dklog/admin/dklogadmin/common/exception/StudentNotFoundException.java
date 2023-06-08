package kr.dklog.admin.dklogadmin.common.exception;

public class StudentNotFoundException extends DklogException {

    private static final String MESSAGE = "학생 정보가 존재하지 않습니다.";

    public StudentNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
