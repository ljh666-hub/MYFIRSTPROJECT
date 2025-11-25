package com.ljh.exception;

public class BusinessException extends RuntimeException{
//
//
    private Integer status;
//

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BusinessException(Integer status) {
        this.status = status;
    }

    public BusinessException(Throwable cause, Integer status) {
        super(cause);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public BusinessException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}