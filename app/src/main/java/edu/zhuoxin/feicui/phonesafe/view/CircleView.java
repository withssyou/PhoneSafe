package edu.zhuoxin.feicui.phonesafe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/12/22.
 */

public class CircleView extends View {
    /**扇形的当前角度*/
    private int currentAngle = 90;
    private Paint paint;
    /**记录是否回退*/
    private boolean back = true;
    /**记录动画状态，是否在执行  true表示动画正在执行*/
    private boolean isRunning = false;


    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width < height){
            height = width;
        }else {
            width = height;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawArc(rectF,-90,currentAngle,true,paint);
    }

    /**
     *  设置动画
     * @param targetAngle 指定的目标角度
     */
    public void  setAngle(final int targetAngle){
        if (!isRunning){
            isRunning = true;
            final Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (back){
                        if (currentAngle >= 10 ) {
                            currentAngle -= 10;
                        }else {
                            currentAngle = 0;
                            back = false;
                        }
                    }else {
                        if (currentAngle <= targetAngle-10){
                            currentAngle += 10;
                        }else {
                            currentAngle = targetAngle;
                            back = true;
                            isRunning = false;
                            timer.cancel();
                        }
                    }
                    postInvalidate();
                }
            };

            timer.schedule(task,400,40);
        }
    }
}
