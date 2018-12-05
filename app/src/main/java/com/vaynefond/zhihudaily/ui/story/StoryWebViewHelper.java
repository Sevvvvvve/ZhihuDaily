package com.vaynefond.zhihudaily.ui.story;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class StoryWebViewHelper {
    private static final String XDATA_BASE_URL = "x-data://base";
    private static final String MIME_TYPE = "text/html";
    private static final String ENCODING = "utf-8";

    private static final String CSS_ASSET = "file:///android_asset/";

    private static final String HTML_HEADER = "<!doctype html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<meta charset=\"utf-8\">\n" +
            "\t<meta name=\"viewport\" content=\"width=device-width,user-scalable=no\">\n" +
            "\t<link href=\"news_qa.min.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
            "\t<script src=\"zepto.min.js\"></script>\n" +
            "\t<script src=\"img_replace.js\"></script>\n" +
            "\t<script src=\"video.js\"></script>\n" +
            "</head>\n";

    private static final String LIGHT_THME = "<body className=\"\" onload=\"onLoaded()\">";
    private static final String NIGHT_THEME = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
    private static final String HTML_FOOTER = "</body>\n</html>";

    private WebView mWebView;

    public StoryWebViewHelper(@NonNull WebView webView) {
        mWebView = webView;
    }

    public void initWebView(boolean noImageMode) {
        WebSettings settings = mWebView.getSettings();

        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(false);

        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setBlockNetworkImage(noImageMode);

        mWebView.setScrollbarFadingEnabled(true);
    }

    public void loadPage(String body, String shareUrl, boolean nightMode) {
        String theme = nightMode ? NIGHT_THEME : LIGHT_THME;
        if (body != null) {
            String html = HTML_HEADER + theme + body + HTML_FOOTER;
            mWebView.loadDataWithBaseURL(CSS_ASSET, html, MIME_TYPE, ENCODING, null);
        } else {
            mWebView.loadDataWithBaseURL(XDATA_BASE_URL, shareUrl, MIME_TYPE, ENCODING, null);
        }
    }

    public void destroyWebView() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", MIME_TYPE, ENCODING, null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
        }
    }
}
