package com.sandrlab.drawapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SquareView extends View {

    private Box mCurrentBox;
    private final List<Box> mBoxes = new ArrayList<>();
    private final Paint mBoxPaint = new Paint();
    private final Paint mBackgroundPaint = new Paint();

    public SquareView(Context context) {
        this(context, null);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxes) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());

        int action = event.getAction();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentBox = new Box(current);
                mBoxes.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mCurrentBox = null;
                break;
        }
        return true;
    }

    public void reset() {
        mBoxes.clear();
        invalidate();
    }

    private void setUpPaint() {
        mBackgroundPaint.setColor(Color.WHITE);
        mBoxPaint.setColor(Color.GREEN);
        mBoxPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
}
