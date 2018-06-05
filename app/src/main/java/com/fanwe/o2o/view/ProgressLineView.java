package com.fanwe.o2o.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.View;
import com.fanwe.o2o.R;

/**
 * 从左到右加载的百分比进度条 Created by heyucan on 2017/1/14.
 */

public class ProgressLineView extends View {
  private static final int DEFAULT_COLOR = 0xFFCCCCCC;
  private static final int DEFAULT_PER_PERCENT = 2; //0-10即0-10%以内,不然重绘次数过少,无法看出动画效果
  private Paint paint;
  private int lineColor;
  private int perPercent;

  private int viewHeight;
  private int viewWidth;
  private int drawCount = 0;

  private float deltaX = 0.0f;
  private float finalX = 0.0f;
  private float startX = 0.0f;
  private float stopX = 0.0f;

  public ProgressLineView(Context context) {
    this(context, null);
  }

  public ProgressLineView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ProgressLineView(Context context, AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray ta =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressLineView, 0, 0);
    lineColor = ta.getColor(R.styleable.ProgressLineView_plvLineColor, DEFAULT_COLOR);
    setBackgroundColor(getResources().getColor(R.color.white));
    perPercent = ta.getInteger(R.styleable.ProgressLineView_plvPerPercent, DEFAULT_PER_PERCENT);
    if (perPercent < 0 || perPercent > 10) {
      perPercent = DEFAULT_PER_PERCENT;
    }
    ta.recycle();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    updateX();
    if (paint == null || viewHeight == 0) {
      viewWidth = getWidth();
      viewHeight = getHeight();
      setPaint(lineColor);
    }
    if (stopX <= finalX && finalX != 0 && stopX != 0) {
      canvas.drawLine(0, viewHeight / 2, getStopX(), viewHeight / 2, paint);
      invalidate();
    } else {
      canvas.drawLine(0, viewHeight / 2, setFinalX(), viewHeight / 2, paint);
    }
    ++drawCount;
  }

  private void updateX() {
    if (finalX == 0) {
      //startX =0;
      stopX = 0;
    } else {
      if (drawCount > 0) {
        //startX = stopX;
        stopX += deltaX;
      }
      //stopX += deltaX;
    }
  }

  private float getStartX() {
    return startX;
  }

  private float getStopX() {
    return stopX;
  }

  private float setFinalX() {
    return finalX;
  }

  public void setPaint(@ColorInt int colorInt) {
    if (paint == null) {
      paint = new Paint();
      paint.setAntiAlias(true);
      paint.setStrokeWidth(viewHeight);
    }
    paint.setColor(colorInt);
  }

  public void setLineColorInt(@ColorInt int colorInt) {
    lineColor = colorInt;
  }

  public int getColorInt(@ColorRes int color) {
    int colorInt = Color.parseColor(String.valueOf(getResources().getColor(color)));
    return colorInt;
  }

  public void setFinalX(final float s1, final float s2) {
    try {
      this.post(new Runnable() {
        @Override public void run() {
          float part = s1;
          float total = s2;
          viewWidth = getWidth();
          viewHeight = getHeight();
          //setPaint(lineColor);
          if (Math.abs(part) <= Math.abs(total) && total != 0 && viewWidth > 0) {
            finalX = Math.abs(part / total) * viewWidth;
            deltaX = finalX * perPercent * 0.01f;
            stopX = deltaX;
          } else {
            finalX = 0;
            stopX = 0;
          }
          invalidate();
        }
      });
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }
}
