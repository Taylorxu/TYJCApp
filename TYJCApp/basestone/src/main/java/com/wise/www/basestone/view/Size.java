package com.wise.www.basestone.view;

/**
 * Created by Julian Villella on 16-02-23.
 */
public class Size {
    int mWidth;
    int mHeight;

    public Size(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    @Override
    public String toString() {
        return "Size{" +
                "mWidth=" + mWidth +
                ", mHeight=" + mHeight +
                '}';
    }
}
