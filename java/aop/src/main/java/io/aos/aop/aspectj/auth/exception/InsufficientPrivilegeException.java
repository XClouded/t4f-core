package io.aos.aop.aspectj.auth.exception;

public class InsufficientPrivilegeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public InsufficientPrivilegeException(String message) {
        super(message);
    }

}
