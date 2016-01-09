package com.example.nobell.project3.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;

/*
 * This object must be unique tablayout included in the main activity.
 * This could be removed for some special fragments are activated.
 */
public class MainTabLayout extends TabLayout {
    /* Singleton */
    private static MainTabLayout mInstance;
    public static MainTabLayout getInstance () {
        if (mInstance == null) {
            mInstance = new MainTabLayout(MainActivity.getInstance());
        }
        return mInstance;
    }
    public MainTabLayout(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

    }
}
