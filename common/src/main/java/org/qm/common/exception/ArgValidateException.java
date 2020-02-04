package org.qm.common.exception;

public class ArgValidateException extends RuntimeException {
    private String errMessage;

    public ArgValidateException() {
        super();
    }

    public ArgValidateException(String errMessage) {
        super(errMessage);
    }
}
