package org.gooinpro.gooinproparttimerapi.login.exception;

import lombok.Data;


@Data
public class PartTimerTaskException extends RuntimeException {

    private int status;

    private String msg;

    public PartTimerTaskException(final int status, String msg) {

        super(status + "_" + msg);

        this.status = status;
        this.msg = msg;
    }
}
