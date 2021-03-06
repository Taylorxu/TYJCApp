package com.wise.www.basestone.view.view;

import com.wise.www.basestone.view.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian Villella on 15-08-24.
 */

public class GreedoLayoutSizeCalculator {
    public interface SizeCalculatorDelegate {
        double aspectRatioForIndex(int index);
    }

    private static final int DEFAULT_MAX_ROW_HEIGHT = 600;
    private int mMaxRowHeight = DEFAULT_MAX_ROW_HEIGHT;

    private static final int INVALID_CONTENT_WIDTH = -1;
    private int mContentWidth = INVALID_CONTENT_WIDTH;

    // When in fixed height mode and the item's width is less than this percentage, don't try to
    // fit the item, overflow it to the next row and grow the existing items.
    private static final double VALID_ITEM_SLACK_THRESHOLD = 2.0 / 3.0;

    private boolean mIsFixedHeight = false;

    private int totalHeight;

    private int lastRowCount = 0;
    private int lastRowHeight = 0;

    private SizeCalculatorDelegate mSizeCalculatorDelegate;

    private List<Size> mSizeForChildAtPosition;
    private List<Integer> mFirstChildPositionForRow;
    private List<Integer> mRowForChildPosition;


    int horizontalSpace, verticalSpace;

    public void setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
    }

    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    public void setSpace(int space) {
        this.verticalSpace = space;
        this.horizontalSpace = space;
    }


    public GreedoLayoutSizeCalculator(SizeCalculatorDelegate sizeCalculatorDelegate) {
        mSizeCalculatorDelegate = sizeCalculatorDelegate;

        mSizeForChildAtPosition = new ArrayList<>();
        mFirstChildPositionForRow = new ArrayList<>();
        mRowForChildPosition = new ArrayList<>();
    }

    public void setContentWidth(int contentWidth) {
        if (mContentWidth != contentWidth) {
            mContentWidth = contentWidth;
            reset();
        }
    }

    public void setMaxRowHeight(int maxRowHeight) {
        if (mMaxRowHeight != maxRowHeight) {
            mMaxRowHeight = maxRowHeight;
            reset();
        }
    }

    public void setFixedHeight(boolean fixedHeight) {
        if (mIsFixedHeight != fixedHeight) {
            mIsFixedHeight = fixedHeight;
            reset();
        }
    }

    public Size sizeForChildAtPosition(int position) {
        if (position >= mSizeForChildAtPosition.size()) {
            computeChildSizesUpToPosition(position);
        }
        return mSizeForChildAtPosition.get(position);
    }

    public int getFirstChildPositionForRow(int row) {
        if (row >= mFirstChildPositionForRow.size()) {
            computeFirstChildPositionsUpToRow(row);
        }
        return row < mFirstChildPositionForRow.size() ? mFirstChildPositionForRow.get(row) : -1;
    }

    public int getRowForChildPosition(int position) {
        if (position >= mRowForChildPosition.size()) {
            computeChildSizesUpToPosition(position);
        }
        return position < mRowForChildPosition.size() ? mRowForChildPosition.get(position) : -1;

    }

    public int getLastRowCount() {
        return lastRowCount;
    }

    public int getLastRowHeight() {
        return lastRowHeight;
    }

    public int getTotalHeight(int position) {
        if (position >= mSizeForChildAtPosition.size()) {
            computeChildSizesUpToPosition(position);
        }
        return totalHeight;
    }


    public void reset() {
        mSizeForChildAtPosition.clear();
        mFirstChildPositionForRow.clear();
        mRowForChildPosition.clear();
        lastRowCount = 0;
        lastRowHeight = 0;
        totalHeight = 0;
    }

    private void computeFirstChildPositionsUpToRow(int row) {
        // TODO: Rewrite this? Looks dangerous but in reality should be fine. I'd like something
        //       less alarming though.
        while (row >= mFirstChildPositionForRow.size()) {
            computeChildSizesUpToPosition(mSizeForChildAtPosition.size() + 1);
        }
    }

    private void computeChildSizesUpToPosition(int lastPosition) {
        if (mContentWidth == INVALID_CONTENT_WIDTH) {
            throw new RuntimeException("Invalid content width. Did you forget to set it?");
        }

        if (mSizeCalculatorDelegate == null) {
            throw new RuntimeException("Size calculator delegate is missing. Did you forget to set it?");
        }

        if (lastPosition >= 0) {
            calculateSize(lastPosition);
            return;
        }

        int firstUncomputedChildPosition = mSizeForChildAtPosition.size();
        int row = mRowForChildPosition.size() > 0
                ? mRowForChildPosition.get(mRowForChildPosition.size() - 1) + 1 : 0;

        double currentRowAspectRatio = 0.0;
        List<Double> itemAspectRatios = new ArrayList<>();
        int currentRowHeight = mIsFixedHeight ? mMaxRowHeight : Integer.MAX_VALUE;

        int currentRowWidth = 0;
        int pos = firstUncomputedChildPosition;

        while (pos < lastPosition || (mIsFixedHeight ? currentRowWidth <= mContentWidth : currentRowHeight > mMaxRowHeight)) {
            double posAspectRatio = mSizeCalculatorDelegate.aspectRatioForIndex(pos);
            currentRowAspectRatio += posAspectRatio;
            boolean isRowFull;
            if (posAspectRatio == 0) {
                isRowFull = true;
                lastRowCount = itemAspectRatios.size();
                lastRowHeight = currentRowHeight;
            } else {
                itemAspectRatios.add(posAspectRatio);
                currentRowWidth = calculateWidth(currentRowHeight, currentRowAspectRatio);
                if (!mIsFixedHeight) {
                    currentRowHeight = calculateHeight(mContentWidth, currentRowAspectRatio);
                }
                isRowFull = mIsFixedHeight ? currentRowWidth > mContentWidth : currentRowHeight <= mMaxRowHeight;
            }


            if (isRowFull) {
                int rowChildCount = itemAspectRatios.size();
                mFirstChildPositionForRow.add(pos - rowChildCount + (currentRowHeight > mMaxRowHeight ? 0 : 1));

                int[] itemSlacks = new int[rowChildCount];
                if (mIsFixedHeight) {
                    itemSlacks = distributeRowSlack(currentRowWidth, rowChildCount, itemAspectRatios);

                    if (!hasValidItemSlacks(itemSlacks, itemAspectRatios)) {
                        int lastItemWidth = calculateWidth(currentRowHeight,
                                itemAspectRatios.get(itemAspectRatios.size() - 1));
                        currentRowWidth -= lastItemWidth;
                        rowChildCount -= 1;
                        itemAspectRatios.remove(itemAspectRatios.size() - 1);

                        itemSlacks = distributeRowSlack(currentRowWidth, rowChildCount, itemAspectRatios);
                    }
                }

                int availableSpace = mContentWidth;
                for (int i = 0; i < rowChildCount; i++) {
                    int itemWidth = calculateWidth(currentRowHeight, itemAspectRatios.get(i)) - itemSlacks[i];
                    itemWidth = Math.min(availableSpace, itemWidth);

                    mSizeForChildAtPosition.add(new Size(itemWidth, currentRowHeight));
                    mRowForChildPosition.add(row);
                    availableSpace -= itemWidth;
                }

                itemAspectRatios.clear();
                currentRowAspectRatio = 0.0;
                totalHeight += currentRowHeight;
                currentRowHeight = 0;
                row++;
            }
            pos++;
        }
    }


    public void calculateSize(int position) {

        int current = mSizeForChildAtPosition.size();
        int row = mRowForChildPosition.size() > 0
                ? mRowForChildPosition.get(mRowForChildPosition.size() - 1) + 1 : 0;
        double currentRowAspectRatio = 0.0;
        boolean isFullRow = false;
        List<Double> rowAspectRatios = new ArrayList<>();
        while (current < position || !isFullRow) {
            double currentAspectRatio = mSizeCalculatorDelegate.aspectRatioForIndex(current);
            if (currentAspectRatio > 0) {
                rowAspectRatios.add(currentAspectRatio);
            }

            currentRowAspectRatio += currentAspectRatio;
            int maxHeight = mMaxRowHeight;

            double currentWidth = calculateWidth(maxHeight, currentRowAspectRatio) + horizontalSpace * (rowAspectRatios.size() - 1);
            if (currentWidth > mContentWidth || currentAspectRatio == 0) {
                isFullRow = true;
                int availableSpace = mContentWidth - horizontalSpace * (rowAspectRatios.size() - 1);
                int currentHeight = calculateHeight(availableSpace, currentRowAspectRatio);
                mFirstChildPositionForRow.add(current - rowAspectRatios.size() + (currentHeight > mMaxRowHeight ? 0 : 1));
                for (int i = 0; i < rowAspectRatios.size(); i++) {
                    int itemWidth = calculateWidth(currentHeight, rowAspectRatios.get(i));
                    itemWidth = Math.min(availableSpace, itemWidth);
                    availableSpace -= itemWidth;
                    mSizeForChildAtPosition.add(new Size(itemWidth, currentHeight));
                    mRowForChildPosition.add(row);
                }

                lastRowCount = rowAspectRatios.size();
                lastRowHeight = currentHeight;
                totalHeight += currentHeight;
                rowAspectRatios.clear();
                currentRowAspectRatio = 0.0;
                row++;
            } else {
                isFullRow = false;
            }
            current++;
        }
    }

    private int[] distributeRowSlack(int rowWidth, int rowChildCount, List<Double> itemAspectRatios) {
        return distributeRowSlack(rowWidth - mContentWidth, rowWidth, rowChildCount, itemAspectRatios);
    }

    private int[] distributeRowSlack(int rowSlack, int rowWidth, int rowChildCount, List<Double> itemAspectRatios) {
        int itemSlacks[] = new int[rowChildCount];

        for (int i = 0; i < rowChildCount; i++) {
            double itemWidth = mMaxRowHeight * itemAspectRatios.get(i);
            itemSlacks[i] = (int) (rowSlack * (itemWidth / rowWidth));
        }

        return itemSlacks;
    }

    private boolean hasValidItemSlacks(int[] itemSlacks, List<Double> itemAspectRatios) {
        for (int i = 0; i < itemSlacks.length; i++) {
            int itemWidth = (int) (itemAspectRatios.get(i) * mMaxRowHeight);
            if (!isValidItemSlack(itemSlacks[i], itemWidth)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidItemSlack(int itemSlack, int itemWidth) {
        return (itemWidth - itemSlack) / (double) itemWidth > VALID_ITEM_SLACK_THRESHOLD;
    }

    private int calculateWidth(int itemHeight, double aspectRatio) {
        return (int) Math.ceil(itemHeight * aspectRatio);
    }

    private int calculateHeight(int itemWidth, double aspectRatio) {
        return (int) Math.ceil(itemWidth / aspectRatio);
    }
}