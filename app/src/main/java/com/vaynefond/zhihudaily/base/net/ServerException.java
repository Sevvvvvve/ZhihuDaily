package com.vaynefond.zhihudaily.base.net;

public class ServerException extends RuntimeException {
    private int mCode;

    public ServerException(int code, String message) {
        super(message);
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}
