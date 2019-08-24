package com.github.mistra.graphqldemo.exception;

public class ParsingRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -3044224013583868433L;

	public ParsingRuntimeException(String msg) {
        super(msg);
    }

    public ParsingRuntimeException(Exception e) {
        super(e);
    }

    public ParsingRuntimeException(String msg, Exception e) {
        super(msg, e);
    }
}