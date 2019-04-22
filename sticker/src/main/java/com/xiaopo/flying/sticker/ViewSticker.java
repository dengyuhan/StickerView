package com.xiaopo.flying.sticker;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author dengyuhan
 * created 2019/3/21 16:38
 */
public class ViewSticker extends Sticker {
    private View mView;
    private Rect mOriginalRect;

    public ViewSticker(View v) {
        this.mView = v;
        this.mOriginalRect = new Rect(0, 0, mView.getWidth(), mView.getHeight());
    }

    //@Override
    public void onPostInitialize() {
        final ViewGroup.LayoutParams params = mView.getLayoutParams();
        int width = mView.getWidth();
        int height = mView.getHeight();
        if (width <= 0 && params.width > 0) {
            width = params.width;
        }
        if (height <= 0 && params.height > 0) {
            height = params.height;
        }
        this.mOriginalRect = new Rect(0, 0, width, height);
    }

    @NonNull
    public View getView() {
        return mView;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    public int getOriginalWidth() {
        return mOriginalRect.right;
    }

    @Override
    public int getOriginalHeight() {
        return mOriginalRect.bottom;
    }

    @Override
    public Sticker setDrawable(@NonNull Drawable drawable) {
        return null;
    }

    @NonNull
    @Override
    public Drawable getDrawable() {
        return null;
    }

    @NonNull
    @Override
    public Sticker setAlpha(int alpha) {
        mView.setAlpha(alpha);
        return this;
    }


}
