package com.example.sampledrawingboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PaintView extends View {

    private ArrayList<PathInfo> data = new ArrayList<PathInfo>();
    private PathInfo pathInfo;
    private Paint paint;

    int color = Color.BLACK;
    int radius = 15;

    public void setColor(int color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setPaintInfo() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(radius);

        pathInfo = new PathInfo();
        pathInfo.setPaint(paint);
    }

    public PaintView(Context context) {
        super(context);

        setPaintInfo();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (PathInfo p : data) {
            canvas.drawPath(p, p.getPaint());
            Log.d("data", p.getPaint().toString());
        }

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pathInfo.moveTo(x, y);  // View가 눌렸을 때
                break;
            case MotionEvent.ACTION_MOVE:
                pathInfo.lineTo(x, y);  // View를 누르고 이동했을 때
                break;
            case MotionEvent.ACTION_UP:
                data.add(pathInfo);
                pathInfo = new PathInfo();
                pathInfo.setPaint(paint);
                break;  // View에서 터치를 중단했을 때
        }

        invalidate();

        return true;
    }

    public void eraseAll() {
        data.clear();

        invalidate();
    }
}
