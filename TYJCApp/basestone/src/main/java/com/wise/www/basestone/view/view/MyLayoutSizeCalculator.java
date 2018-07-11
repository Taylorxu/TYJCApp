package com.wise.www.basestone.view.view;

import android.util.SparseArray;

import com.wise.www.basestone.view.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过宽高比计算每个item的宽高
 * Created by 王冰 on 2017/1/19.
 */

public class MyLayoutSizeCalculator {
    //默认的最大高度
    private static final int DEFAULT_MAX_ROW_HEIGHT = 600;

    private int maxRowHeight = DEFAULT_MAX_ROW_HEIGHT;
    // 宽高计算器代理
    private SizeCalculatorDelegate sizeDelegate;
    //用于存储所有的宽高
    private SparseArray<Size> layoutSize;
    //coord坐标
    private SparseArray<Size> coordinates;
    private int contentWidth;

    public MyLayoutSizeCalculator(SizeCalculatorDelegate sizeDelegate) {
        this.sizeDelegate = sizeDelegate;
        layoutSize = new SparseArray<>();
        coordinates = new SparseArray<>();
    }

    public void setMaxRowHeight(int maxRowHeight) {
        this.maxRowHeight = maxRowHeight;
    }

    public void reset() {
        layoutSize.clear();
        coordinates.clear();
    }

    public void setContentWidth(int contentWidth) {
        this.contentWidth = contentWidth;
    }

    public Size getSizeByPosition(int position) {
        if (layoutSize.size() <= position) {
            calculateLayoutSize(position);
        }
        return layoutSize.get(position);
    }

    public int getRowByPosition(int position) {
        if (coordinates.size() <= position) {
            calculateLayoutSize(position);
        }
        return coordinates.get(position).getHeight();
    }

    public int getRowFirstChildPosition(int row) {
        int position = 0;
        for (int i = 0; i < coordinates.size(); i++) {
            Size size = coordinates.get(position);
            if (size.getHeight() == row && size.getWidth() < position)
                position = size.getWidth();
        }
        return position;
    }

    public SparseArray<Size> getLayoutSize() {
        if (layoutSize.size() == 0) {
            calculateLayoutSize(sizeDelegate.getItemCount());
        }
        return layoutSize;
    }

    public void calculateLayoutSize(int lastPos) {
        if (sizeDelegate == null) {
            throw new RuntimeException("设置一个sizeDelegate?");
        }

        int unCalculatePos = layoutSize.size();
        int row = unCalculatePos == 0 ? 0 : (getRowByPosition(unCalculatePos - 1) + 1);

        int currentRowHeight = maxRowHeight+1;
        double currentRowAspectRatio = 0.0;
        int pos = unCalculatePos;

        List<Double> itemAspectRatios = new ArrayList<>();

        while (pos < lastPos || currentRowHeight > maxRowHeight) {
            double posAspectRatio = sizeDelegate.getSpectRatioByPosition(pos);
            currentRowAspectRatio += posAspectRatio;
            currentRowHeight = calculateHeight(contentWidth, currentRowAspectRatio);

            boolean isFullRow;
            if (posAspectRatio > 0) {
                itemAspectRatios.add(posAspectRatio);
                isFullRow = currentRowHeight < maxRowHeight;
            } else {
                isFullRow = true;
            }

            if (isFullRow) {
                int availableSpace = contentWidth;
                for (int i = 0; i < itemAspectRatios.size(); i++) {
                    int itemWidth = calculateWidth(currentRowHeight, itemAspectRatios.get(i));
                    itemWidth = Math.min(availableSpace, itemWidth);
                    availableSpace -= itemWidth;
                    layoutSize.put(layoutSize.size(), new Size(itemWidth, currentRowHeight));
                    coordinates.put(coordinates.size(), new Size(i, row));
                }
                itemAspectRatios.clear();
                currentRowAspectRatio = 0.0;
                currentRowHeight = 0;
                row++;
            }
            pos++;
        }

    }

    private int calculateWidth(int itemHeight, double aspectRatio) {
        return (int) Math.ceil(itemHeight * aspectRatio);
    }

    private int calculateHeight(int itemWidth, double aspectRatio) {
        return (int) Math.ceil(itemWidth / aspectRatio);
    }

    public interface SizeCalculatorDelegate {
        double getSpectRatioByPosition(int index);

        int getItemCount();
    }
}
