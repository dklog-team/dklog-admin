package kr.dklog.admin.dklogadmin.common.exception;

public abstract class DklogException extends RuntimeException {

    public DklogException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
