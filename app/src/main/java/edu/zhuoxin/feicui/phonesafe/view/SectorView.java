package edu.zhuoxin.feicui.phonesafe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import edu.zhuoxin.feicui.phonesafe.R;

/**
 * Created by Administrator on 2016/12/22.
 *   自绘View：
 *      View的绘制机制（原理）:测量------>定位------>绘制
 *  步骤：
 *      1.自定义属性：
 *          找到需求中的属性，自定义
 *      2.在Xml文件中使用自定义的View和属性
 *      3.在自定义的View类中，找到属性
 *      4.[测量  ]
 *      5.绘制
 *  画画：
 *      画笔  画布 （画板 画笔）
 *  设置颜色的方法:
 *      #ff0
 *      Color.BLUE
 *      Color.parse("#FFOOOO");
 *
 *
 */

public class SectorView extends View {
    /**开始角度*/
    private int startAngle ;
    /**结束角度*/
    private int endAngle;
    /**背景圆形颜色*/
    private int backgroundColor = Color.GREEN;
    /**进度扇形的颜色*/
    private int progressColor = Color.BLUE;
    /**画笔*/
    private Paint paint;

    public SectorView(Context context) {
        this(context,null);
    }

    public SectorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //初始化属性：
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SectorView,0,0);
        //获取属性的个数
        int num = typedArray.getIndexCount();
        for (int i = 0 ; i < num ; i++){
            int id = typedArray.getIndex(i);
            switch (id){
                case R.styleable.SectorView_backgroundColorSector:
                    backgroundColor = typedArray.getColor(id,Color.GREEN);
                    break;
                case R.styleable.SectorView_progressColorSector:
                    progressColor = typedArray.getColor(id,Color.BLUE);
                    break;
                case R.styleable.SectorView_endAngle:
                    endAngle = typedArray.getInt(id,0);
                    break;
            }
        }
        //回收再利用
        typedArray.recycle();
    }
    /**测量的方法*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width < height){
            height = width;
        }else {
            width = height;
        }
        //设置测量结果
        setMeasuredDimension(width,height);
    }
    /**绘制*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //首先绘制底部整圆
        paint.setColor(backgroundColor);
//        paint.setStrokeWidth(2); //设置笔触宽度
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawArc(rectF,-90,360,true,paint);
        //绘制进度扇形
        paint.setColor(progressColor);
        canvas.drawArc(rectF,-90+startAngle,endAngle,true,paint);
    }
    /**动画效果*/
    public void drawAnim(int start, final int end){
        startAngle = start;
        endAngle = start;
        //创建定时器对象
        final Timer timer = new Timer();
        //定时器需要执行的任务
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                endAngle+=4;
                if (endAngle >=  end){
                    endAngle = end;
                    //告诉view重新绘制
                    postInvalidate();
                    timer.cancel();
                }
                postInvalidate();
            }
        };
        //定时器启动任务
        timer.schedule(task,400,20);
    }

}
