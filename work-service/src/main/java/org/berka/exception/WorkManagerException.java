package org.berka.exception;

import lombok.Getter;

@Getter
public class WorkManagerException extends RuntimeException{

    private final ErrorType errorType;

    public WorkManagerException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


    public WorkManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
