package com.vaynefond.zhihudaily.base.net;

public class NoDataException extends Exception {
    private int mErrorCode;
    private String mErrorMsg;

    public static NoDataException create() {
        return new NoDataException(ErrorCodes.NO_DATA_ERROR, "无法加载数据，请连接网络并重试");
    }

    public NoDataException(int code, String message) {
        super();
        mErrorCode = code;
        mErrorMsg = message;
    }

    public int getCode() {
        return mErrorCode;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }
}
