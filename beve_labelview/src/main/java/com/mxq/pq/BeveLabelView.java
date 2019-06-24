package com.mxq.pq;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
*@author poqiao
 * @date  2019/06/24
*/
public class BeveLabelView extends View {
    private final static int MODE_LEFT_TOP = 0;
    private final static int MODE_RIGHT_TOP = 1;
    private final static int MODE_LEFT_BOTTOM = 2;
    private final static int MODE_RIGHT_BOTTOM = 3;
    private final static int MODE_LEFT_TOP_FILL = 4;
    private final static int MODE_RIGHT_TOP_FILL = 5;
    private final static int MODE_LEFT_BOTTOM_FILL = 6;
    private final static int MODE_RIGHT_BOTTOM_FILL = 7;
    private int mBgColor;//背景颜色
    private String mText;//显示文字
    private int mTextSize;//文字大小
    private int mTextColor;//文字颜色
    private int mLength;//标题长度
    private int mCorner;//圆角
    private int mMode;//显示模式
    private Paint mPaint;
    private Path path;
    private int mWidth, mHeight;
    private int mRotate = 45;//因为默认模式是1，所以这时是45度
    private int mX, mY;

    public BeveLabelView(Context context) {
        super (context);
    }

    public BeveLabelView(Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);
        TypedArray array = context.obtainStyledAttributes (attrs, R.styleable.BeveLabelView);
        mBgColor = array.getColor (R.styleable.BeveLabelView_label_bg_color, Color.RED);//默认红色
        mText = array.getString (R.styleable.BeveLabelView_label_text);
        mTextSize = array.getDimensionPixelOffset (R.styleable.BeveLabelView_label_text_size, sp2px (11));
        mTextColor = array.getColor (R.styleable.BeveLabelView_label_text_color, Color.WHITE);
        mLength = array.getDimensionPixelOffset (R.styleable.BeveLabelView_label_length, dip2px (40));
        mCorner = array.getDimensionPixelOffset (R.styleable.BeveLabelView_label_corner, 0);
        mMode = array.getInt (R.styleable.BeveLabelView_label_mode, 1);
        mPaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias (true);
        path = new Path ();
        if (TextUtils.isEmpty (mText))
        {
            mText  = "Label";
        }
    }

    public BeveLabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);
        mHeight = mWidth = MeasureSpec.getSize (widthMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  super.onDraw (canvas);
        mPaint.setColor (mBgColor);
        drawBackgroundText (canvas);
    }


    private void drawBackgroundText(Canvas canvas) {

        if (mWidth != mHeight) {
            throw new IllegalStateException ("width must equal to height");//标签view 是一个正方形，
        }
        switch (mMode) {
            case MODE_LEFT_TOP:
                mCorner = 0;//没有铺满的时候mCorner要归零；
                leftTopMeasure ();
                getLeftTop ();
                break;
            case MODE_RIGHT_TOP:
                mCorner = 0;
                rightTopMeasure ();
                getRightTop ();
                break;
            case MODE_LEFT_BOTTOM:
                mCorner = 0;
                leftBottomMeasure ();
                getLeftBottom ();
                break;
            case MODE_RIGHT_BOTTOM:
                mCorner = 0;
                rightBottomMeasure ();
                getRightBottom ();
                break;
            case MODE_LEFT_TOP_FILL:
                leftTopMeasure ();
                getLeftTopFill ();
                if (mCorner != 0) {
                    canvas.drawPath (path, mPaint);
                    getLeftTop ();
                }
                break;
            case MODE_RIGHT_TOP_FILL:
                rightTopMeasure ();
                getRightTopFill ();
                if (mCorner != 0) {
                    canvas.drawPath (path, mPaint);
                    getRightTop ();
                }
                break;
            case MODE_LEFT_BOTTOM_FILL:
                leftBottomMeasure ();
                getLeftBottomFill ();
                if (mCorner != 0) {
                    canvas.drawPath (path, mPaint);
                    getLeftBottom ();
                }
                break;
            case MODE_RIGHT_BOTTOM_FILL:
                rightBottomMeasure ();
                getRightBottomFill ();
                if (mCorner != 0) {
                    canvas.drawPath (path, mPaint);
                    getRightBottom ();
                }
                break;
            default:
        }

        canvas.drawPath (path, mPaint);

        mPaint.setTextSize (mTextSize);
        mPaint.setTextAlign (Paint.Align.CENTER);
        mPaint.setColor (mTextColor);
        canvas.translate (mX, mY);
        canvas.rotate (mRotate);

        int baseLineY = -(int) (mPaint.descent ()+mPaint.ascent ())/2;//基线中间点的y轴计算公式
        canvas.drawText (mText, 0, baseLineY, mPaint);

    }

    private void rightBottomMeasure() {
        mRotate = -45;
        mY = mX = mWidth / 2 + mLength / 4;
    }

    private void leftBottomMeasure() {
        mRotate = 45;
        mX = mWidth / 2 - mLength / 4;
        mY = mHeight / 2 + mLength / 4;
    }

    private void rightTopMeasure() {
        mRotate = 45;
        mX = mWidth / 2 + mLength / 4;
        mY = mHeight / 2 - mLength / 4;
    }

    private void leftTopMeasure() {
        mRotate = -45;
        mY = mX = mWidth / 2 - mLength / 4;
    }

    //左上角铺满
    private void getLeftTopFill() {
        if (mCorner != 0) {
            path.addRoundRect (0, 0, mWidth / 2, mHeight / 2, new float[]{mCorner, mCorner, 0, 0, 0, 0, 0, 0}, Path.Direction.CW);
        } else {
            path.moveTo (0, 0);
            path.lineTo (mWidth, 0);
            path.lineTo (0, mHeight);
            path.close ();
        }
    }

    //左上角不铺满
    private void getLeftTop() {
        path.moveTo (mCorner != 0 ? mCorner : mWidth - mLength, 0);
        path.lineTo (mWidth, 0);
        path.lineTo (0, mHeight);
        path.lineTo (0, mCorner != 0 ? mCorner : mHeight - mLength);
        path.close ();
    }

    //左下角铺满
    private void getLeftBottomFill() {
        if (mCorner != 0) {
            path.addRoundRect (0, mHeight / 2, mWidth / 2, mHeight, new float[]{0, 0, 0, 0, 0, 0, mCorner, mCorner}, Path.Direction.CW);
        } else {
            path.moveTo (0, 0);
            path.lineTo (mWidth, mHeight);
            path.lineTo (0, mHeight);
            path.close ();
        }
    }


    //左下角不铺满
    private void getLeftBottom() {
        path.moveTo (0, 0);
        path.lineTo (mWidth, mHeight);
        path.lineTo (mCorner != 0 ? mCorner : mWidth - mLength, mHeight);
        path.lineTo (0, mCorner != 0 ? mHeight - mCorner : mLength);
        path.close ();
    }

    //右上角铺满
    private void getRightTopFill() {
        if (mCorner != 0) {
            path.addRoundRect (mWidth / 2, 0, mWidth, mHeight / 2, new float[]{0, 0, mCorner, mCorner, 0, 0, 0, 0}, Path.Direction.CW);
        } else {
            path.moveTo (0, 0);
            path.lineTo (mWidth, 0);
            path.lineTo (mWidth, mHeight);
            path.close ();
        }
    }

    //右上角不铺满
    private void getRightTop() {
        path.moveTo (0, 0);
        path.lineTo (mCorner != 0 ? mWidth - mCorner : mLength, 0);
        path.lineTo (mWidth, mCorner != 0 ? mCorner : mHeight - mLength);
        path.lineTo (mWidth, mHeight);
        path.close ();
    }

    //右下角铺满
    private void getRightBottomFill() {
        if (mCorner != 0) {
            path.addRoundRect (mWidth / 2, mHeight / 2, mWidth, mHeight, new float[]{0, 0, 0, 0, mCorner, mCorner, 0, 0}, Path.Direction.CW);
        } else {
            path.moveTo (mWidth, 0);
            path.lineTo (mWidth, mHeight);
            path.lineTo (0, mHeight);
            path.close ();
        }
    }

    //右下角不铺满
    private void getRightBottom() {
        path.moveTo (mWidth, 0);
        path.lineTo (mWidth, mCorner != 0 ? mHeight - mCorner : mLength);
        path.lineTo (mCorner != 0 ? mWidth - mCorner : mLength, mHeight);
        path.lineTo (0, mHeight);
        path.close ();
    }


    /**
     * @param sp 转换大小
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_SP, sp, getResources ().getDisplayMetrics ());
    }

    /**
     * @param dip 转换大小
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP, dip, getResources ().getDisplayMetrics ());
    }


}
