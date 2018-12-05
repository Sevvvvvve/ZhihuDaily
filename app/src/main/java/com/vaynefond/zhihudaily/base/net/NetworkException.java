package com.vaynefond.zhihudaily.base.net;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

import static com.vaynefond.zhihudaily.base.net.ErrorCodes.HTTP_ERROR;
import static com.vaynefond.zhihudaily.base.net.ErrorCodes.JSON_PARSE_ERROR;
import static com.vaynefond.zhihudaily.base.net.ErrorCodes.SOCKET_ERROR;
import static com.vaynefond.zhihudaily.base.net.ErrorCodes.SOCKET_TIMEOUT;
import static com.vaynefond.zhihudaily.base.net.ErrorCodes.SSL_ERROR;
import static com.vaynefond.zhihudaily.base.net.ErrorCodes.UNKNOWN_ERROR;
import static com.vaynefond.zhihudaily.base.net.ErrorCodes.UNKNOWN_HOST;

public class NetworkException extends Exception {
    private int mErrorCode;
    private String mErrorMsg;

    public static NetworkException create(Throwable throwable) {
        return new NetworkException(throwable);
    }

    public NetworkException(Throwable throwable) {
        super(throwable);
        init(throwable);
    }

    private void init(Throwable throwable) {
        if (throwable instanceof SocketException) {
            mErrorCode = SOCKET_ERROR;
            mErrorMsg = "网络异常，请检查网络并重试";
        } else if (throwable instanceof UnknownHostException) {
            mErrorCode = UNKNOWN_HOST;
            mErrorMsg = "网络未连接，请检查网络并重试";
        } else if (throwable instanceof SocketTimeoutException) {
            mErrorCode = SOCKET_TIMEOUT;
            mErrorMsg = "请求超时，请稍后重试";
        } else if (throwable instanceof SSLHandshakeException) {
            mErrorCode = SSL_ERROR;
            mErrorMsg = "证书错误，请稍后重试";
        } else if (throwable instanceof JSONException || throwable instanceof JsonParseException
                || throwable instanceof ParseException) {
            mErrorCode = JSON_PARSE_ERROR;
            mErrorMsg = "解析错误，请稍后重试";
        } else if (throwable instanceof HttpException) {
            mErrorCode = HTTP_ERROR;
            mErrorMsg = "网络异常，请稍后重试";
        } else if (throwable instanceof ServerException) {
            ServerException exception = (ServerException) throwable;
            mErrorCode = exception.getCode();
            mErrorMsg = exception.getMessage();
        } else {
            mErrorCode = UNKNOWN_ERROR;
            mErrorMsg = "未知错误，请检查网络连接并重试";
        }
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }
}
