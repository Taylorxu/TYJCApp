package com.wise.www.tyjcapp.main.helperClass;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wise.www.tyjcapp.R;

/**
 * Created by Administrator on 2018/3/21.
 */
public class RVItemDecoration extends RecyclerView.ItemDecoration {
    public RVItemDecoration(int space,int color) {
        this.space = space;
        this.paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
    }

    private int space;
    private Paint paint;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {////描画  每条分割线
            View view = parent.getChildAt(i);
            // view.getTop() 所描述的是距离，不是点
            float dividerTop = view.getTop() - space;
            float dividerLeft = parent.getPaddingLeft();
            float dividerBottom = view.getTop();
            float dividerRight = parent.getWidth() - parent.getPaddingRight();
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, paint);

        }
    }
}