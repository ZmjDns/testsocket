package com.zmj.mvp.testsocket.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;


/**
 * @author Zmj
 * @date 2018/10/26
 */
public class SilderBar extends View {
    //SliderBar的触摸事件
    private OnTachingLetterChangedListener onTachingLetterChangedListener;

    //26个字母填充SliderBar
    public static String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int choose = -1;//选中
    private Paint paint = new Paint();

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog){
        this.mTextDialog = mTextDialog;
    }

    public SilderBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SilderBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SilderBar(Context context) {
        super(context);
    }

    /**
     * 重写onDraw方法
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;   //每一个字母高度

        for (int i = 0;i < b.length; i++){
            paint.setColor(Color.rgb(145,145,145));//设置字母的颜色
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(30);
            //选中状态
            if (i == choose){
                paint.setColor(Color.parseColor("#3399ff"));//中间弹出字母背景
                paint.setFakeBoldText(true);
            }
            //x坐标等于中间 - 字符串宽度的一半
            float xPos = width / 2 - paint.measureText(b[i]) / 2 ;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i],xPos,yPos,paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        return super.dispatchTouchEvent(event);
        final int action = event.getAction();
        final float y = event.getY();//点击y轴坐标
        final int oldChoose = choose;
        final OnTachingLetterChangedListener listener = onTachingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);//点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数

        switch (action){
            case MotionEvent.ACTION_UP:
                setBackground(new ColorDrawable(0x00000000));
//                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();
                if (mTextDialog != null){
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
                default:
                    setBackgroundResource(R.drawable.sliderbar_background);
                    if (oldChoose != c){
                        if (c >= 0 && c < b.length){
                            if (listener != null){
                                listener.onTachingLetterChanged(b[c]);
                            }
                            if (mTextDialog != null){
                                mTextDialog.setText(b[c]);
                                mTextDialog.setVisibility(VISIBLE);
                            }
                        }
                    }
                    break;
        }
        return true;

    }

    /**
     * 向外公开的方法
     * @param onTachingLetterChangedListener
     */
    public void setOnTachingLetterChangedListener(OnTachingLetterChangedListener onTachingLetterChangedListener){
        this.onTachingLetterChangedListener = onTachingLetterChangedListener;
    }

    /**
     * 触摸SliderBar的接口
     */
    public interface OnTachingLetterChangedListener{
        void onTachingLetterChanged(String s);
    }
}
