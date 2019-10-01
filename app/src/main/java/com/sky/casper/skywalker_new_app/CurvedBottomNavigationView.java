package com.sky.casper.skywalker_new_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.AttributeSet;

public class CurvedBottomNavigationView extends BottomNavigationView {
    private Path mPath;
    private Paint mPaint;

    /** the CURVE_CIRCLE_RADIUS represent the radius of the fab button */
    public final int CURVE_CIRCLE_RADIUS = 90;
    // the coordinates of the first curve
    public Point firstCurveStartPoint = new Point();
    public Point firstCurveEndPoint = new Point();
    public Point firstCurveControlPoint2 = new Point();
    public Point firstCurveControlPoint1 = new Point();

    //the coordinates of the second curve
    @SuppressWarnings("FieldCanBeLocal")
    public Point secondCurveStartPoint = new Point();
    public Point secondCurveEndPoint = new Point();
    public Point secondCurveControlPoint1 = new Point();
    public Point secondCurveControlPoint2 = new Point();
    public int navigationBarWidth;
    public int navigationBarHeight;

    public CurvedBottomNavigationView(Context context) {
        super(context);
        init();
    }

    public CurvedBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurvedBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        navigationBarWidth = getWidth();
        navigationBarHeight = getHeight();

        // the coordinates (x,y) of the start point before curve
        firstCurveStartPoint.set((navigationBarWidth / 2) - (CURVE_CIRCLE_RADIUS * 2) - (CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y) of the end point after curve
        firstCurveEndPoint.set(navigationBarWidth / 2, CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4));

        // same thing for the second curve
        secondCurveStartPoint = firstCurveEndPoint;

        secondCurveEndPoint.set((navigationBarWidth / 2) + (CURVE_CIRCLE_RADIUS * 2) + (CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        firstCurveControlPoint1.set(firstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4), firstCurveStartPoint.y);

        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        firstCurveControlPoint2.set(firstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS * 2) + CURVE_CIRCLE_RADIUS, firstCurveEndPoint.y);


        secondCurveControlPoint1.set(secondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2) - CURVE_CIRCLE_RADIUS, secondCurveStartPoint.y);
        secondCurveControlPoint2.set(secondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4)), secondCurveEndPoint.y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(firstCurveStartPoint.x, firstCurveStartPoint.y);

        mPath.cubicTo(firstCurveControlPoint1.x, firstCurveControlPoint1.y,
                firstCurveControlPoint2.x, firstCurveControlPoint2.y,
                firstCurveEndPoint.x, firstCurveEndPoint.y);

        mPath.cubicTo(secondCurveControlPoint1.x, secondCurveControlPoint1.y,
                secondCurveControlPoint2.x, secondCurveControlPoint2.y,
                secondCurveEndPoint.x, secondCurveEndPoint.y);

        mPath.lineTo(navigationBarWidth, 0);
        mPath.lineTo(navigationBarWidth, navigationBarHeight);
        mPath.lineTo(0, navigationBarHeight);
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }
}