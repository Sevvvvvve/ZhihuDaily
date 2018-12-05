package com.vaynefond.zhihudaily.ui.home;

public class StoryInfo {
    private int mPaperId;
    private String mDate;
    private int mIndex;
    private int mSize;

    public StoryInfo(int paperId, String date, int index, int size) {
        mPaperId = paperId;
        mDate = date;
        mIndex = index;
        mSize = size;
    }

    public int getPaperId() {
        return mPaperId;
    }

    public String getDate() {
        return mDate;
    }

    public int getIndex() {
        return mIndex;
    }

    public boolean isFirstItemInAll() {
        return mPaperId == 0 && mIndex == 0;
    }

    public boolean isFirstElement() {
        return mIndex == 0;
    }

    public boolean isLastElement() {
        return mIndex == mSize - 1;
    }
}

