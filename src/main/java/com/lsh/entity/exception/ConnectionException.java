package com.lsh.entity.exception;

/**
 * @ClassName ConnectionException
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/13 21:51
 * @Version
 */
public class ConnectionException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ConnectionException(String message) {
        super(message);
    }
}
