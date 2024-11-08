package com.example.jjbfather.jjjqsrt2go2gorestdownload;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestdownload.R;

public class OutlineTextView extends TextView {
    private boolean stroke = false;
    private float strokeWidth = 0.0f;
    private int strokeColor;

    public OutlineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    public OutlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public OutlineTextView(Context context) {
        super(context);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OutlineTextView);
        stroke = a.getBoolean(R.styleable.OutlineTextView_textStroke, false);
        strokeWidth = a.getFloat(R.styleable.OutlineTextView_textStrokeWidth, 0.0f);
        strokeColor = a.getColor(R.styleable.OutlineTextView_textStrokeColor, 0xffffffff);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (stroke) {
            ColorStateList states = getTextColors();
            getPaint().setStyle(Paint.Style.STROKE);
            getPaint().setStrokeWidth(strokeWidth);
            setTextColor(strokeColor);
            super.onDraw(canvas);

            getPaint().setStyle(Paint.Style.FILL);
            setTextColor(states);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }
}
