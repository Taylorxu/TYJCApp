package com.wise.www.basestone.view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wise.www.basestone.R;
import com.wise.www.basestone.view.adapter.XViewHolder;
import com.wise.www.basestone.view.util.LogUtils;

import java.util.List;

import static com.wise.www.basestone.view.view.PagingRecyclerView.State.LoadFail;
import static com.wise.www.basestone.view.view.PagingRecyclerView.State.Loading;
import static com.wise.www.basestone.view.view.PagingRecyclerView.State.NoMore;


/**
 * Created by Administrator on 2018/2/27.
 */

public class PagingRecyclerView extends RecyclerView {


    private static final int DEFAULT_EMPTY = R.layout.empty_view;
    private static final int DEFAULT_LOAD = R.layout.load_view;
    private int page = 1;
    private int emptyLayout = DEFAULT_EMPTY;
    private int bottomLayout = DEFAULT_LOAD;
    private boolean hasBottom = true;
    private Adapter adapter;
    private State state = Loading;
    private OnLoadMoreListener loadMoreListener;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (state == LoadFail)
                loadMore(page);
        }
    };
    private String emptyHint;
    private int emptyIcon;
    private OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (state != State.LoadSuccess || loadMoreListener == null) return;
            LayoutManager manager = getLayoutManager();
            if (manager == null) return;

            int count = adapter.getItemCount();
            int last = 0;
            if (manager instanceof LinearLayoutManager) {
                last = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
            } else if (manager instanceof GreedoLayoutManager) {
                last = ((GreedoLayoutManager) manager).findLastVisibleItemPosition();
            } else if (manager instanceof StaggeredGridLayoutManager) {
                int[] lasts;
                lasts = ((StaggeredGridLayoutManager) manager).findLastVisibleItemPositions(null);
                for (int i : lasts) {
                    if (last < i) last = i;
                }
            }
            if (count == last) {//当最后一条 滑到位置是列表数据集合的总数 才会加载
                loadMore(page);
            }
        }
    };

    public PagingRecyclerView(Context context) {
        super(context);
        addOnScrollListener(onScrollListener);
    }

    public PagingRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttr(attrs);
        addOnScrollListener(onScrollListener);
    }

    public PagingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getAttr(attrs);
        addOnScrollListener(onScrollListener);
    }

    private void getAttr(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.PagingRecyclerView);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int index = a.getIndex(i);
            if (index == R.styleable.PagingRecyclerView_emptyLayout) {
                emptyLayout = a.getResourceId(index, emptyLayout);
            } else if (index == R.styleable.PagingRecyclerView_bottomLayout) {
                bottomLayout = a.getResourceId(index, bottomLayout);
            } else if (index == R.styleable.PagingRecyclerView_hasBottom) {
                hasBottom = a.getBoolean(index, true);
            }
        }
        a.recycle();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        super.setAdapter(new BottomAdapter(adapter));
        loadMore(page);
    }

    public void setHasBottom(boolean hasBottom) {
        this.hasBottom = hasBottom;
    }

    public void setEmptyLayout(int emptyLayout) {
        this.emptyLayout = emptyLayout;
    }

    public void setEmptyHint(String emptyHint) {
        this.emptyHint = emptyHint;
    }

    public void setEmptyIcon(int emptyIcon) {
        this.emptyIcon = emptyIcon;
    }

    public int getPage() {
        return page;
    }

    @Override
    public Adapter getAdapter() {
        return adapter;
    }

    public OnLoadMoreListener getLoadMoreListener() {
        return loadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        if (this.state == state) return;
        post(new Runnable() {
            @Override
            public void run() {
                PagingRecyclerView.this.state = state;
                notifyStateChanged();
            }
        });
    }

    private void notifyStateChanged() {
        switch (this.state) {
            case Loading:
                LogUtils.e("正在加载：" + page);
                break;
            case LoadSuccess:
                page++;
                LogUtils.e("下一页：" + page);
                break;
            case LoadFail:
                LogUtils.e("加载失败：" + page);
                break;
            case NoMore:
                LogUtils.e("最后一页：" + page);
                break;
            case Refresh:
                loadMore(page = 1);
                break;
        }
        adapter.notifyItemChanged(adapter.getItemCount());
    }

    private void loadMore(int page) {
        setState(Loading);
        if (loadMoreListener != null)
            loadMoreListener.onLoadMore(page);
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int page);
    }

    public enum State {
        Loading, LoadSuccess, LoadFail, NoMore, Refresh
    }

    private final int Empty = Integer.MIN_VALUE;
    private final int Bottom = Integer.MIN_VALUE + 1;

    class BottomAdapter extends WrapperAdapter {


        BottomAdapter(Adapter wrapped) {
            super(wrapped);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case Empty:
                    return createLayoutByRes(parent, emptyLayout);
                case Bottom:
                    return createLayoutByRes(parent, bottomLayout);
                default:
                    return super.onCreateViewHolder(parent, viewType);
            }
        }

        private ViewHolder createLayoutByRes(ViewGroup parent, int layout) {
            final XViewHolder holder = new XViewHolder<>(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
            holder.getBinding().getRoot().setOnClickListener(onClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case Empty:
                    bindEmpty(holder);
                    break;
                case Bottom:
                    bindBottom(holder);
                    break;
                default:
                    super.onBindViewHolder(holder, position);
                    break;
            }
        }


        private void bindEmpty(ViewHolder holder) {
            holder.itemView.setEnabled(state == LoadFail);

            TextView hint = (TextView) holder.itemView.findViewById(R.id.hint);
            View extractArea = holder.itemView.findViewById(android.R.id.extractArea);
            if (extractArea != null) {
                extractArea.setVisibility(state == NoMore ? VISIBLE : GONE);
            }
            if (hint == null) return;
            int hintRes = 0;
            String hintStr = "";
            Drawable drawable = null;
            switch (state) {
                case Loading:
                    hintRes = R.string.hint_loading;
                    break;
                case LoadSuccess:
                    hintRes = R.string.hint_load_success;
                    break;
                case LoadFail:
                    drawable = getResources().getDrawable(R.mipmap.ic_fail);
                    hintRes = R.string.hint_load_fail;
                    break;
                case NoMore:
                    if (TextUtils.isEmpty(emptyHint)) {
                        hintRes = R.string.hint_empty;
                    } else {
                        hintStr = emptyHint;
                    }
                    if (emptyIcon != 0) {
                        drawable = getResources().getDrawable(emptyIcon);
                    }
                    break;
                case Refresh:
                    hintRes = R.string.hint_refresh;
                    break;
            }
            if (hintRes == 0) {
                hint.setText(hintStr);
            } else {
                hint.setText(hintRes);
            }
            hint.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }

        private void bindBottom(ViewHolder holder) {
            View progress = holder.itemView.findViewById(R.id.progress);
            if (progress != null) progress.setVisibility(state == Loading ? VISIBLE : GONE);

            TextView hint = (TextView) holder.itemView.findViewById(R.id.hint);
            if (hint == null) return;
            int hintRes = 0;
            switch (state) {
                case Loading:
                    hintRes = R.string.hint_loading;
                    break;
                case LoadSuccess:
                    hintRes = R.string.hint_load_success;
                    break;
                case LoadFail:
                    hintRes = R.string.hint_load_fail;
                    break;
                case NoMore:
                    hintRes = R.string.hint_no_more;
                    break;
                case Refresh:
                    hintRes = R.string.hint_refresh;
                    break;
            }
            hint.setText(hintRes);
        }

        @Override
        public int getItemCount() {
            return super.getItemCount() == 0 && emptyLayout != 0 ? 1 : super.getItemCount() + (hasBottom ? 1 : 0);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemCount() == 0 ? Empty : position < super.getItemCount() ? super.getItemViewType(position) : Bottom;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            setGridHeaderFooter(recyclerView.getLayoutManager());
        }

        private void setGridHeaderFooter(LayoutManager layoutManager) {
            if (getItemCount() == super.getItemCount()) return;
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                final GridLayoutManager.SpanSizeLookup sizeLookup = gridLayoutManager.getSpanSizeLookup();
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        boolean isShowFooter = (position == getItemCount() - 1);
                        if (isShowFooter) {
                            return gridLayoutManager.getSpanCount();
                        }
                        return sizeLookup.getSpanSize(position);
                    }
                });
            }
        }
    }

    class WrapperAdapter extends RecyclerView.Adapter {

        private final RecyclerView.Adapter wrapped;

        WrapperAdapter(Adapter wrapped) {
            super();
            this.wrapped = wrapped;
            this.wrapped.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                public void onChanged() {
                    notifyDataSetChanged();
                }

                public void onItemRangeChanged(int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                public void onItemRangeInserted(int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }

                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    notifyItemMoved(fromPosition, toPosition);
                }
            });
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return wrapped.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            wrapped.onBindViewHolder(holder, position);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position, List payloads) {
            if (holder.getItemViewType() == Empty || holder.getItemViewType() == Bottom) {
                super.onBindViewHolder(holder, position, payloads);
                return;
            }
            wrapped.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public int getItemCount() {
            return wrapped.getItemCount();
        }

        @Override
        public int getItemViewType(int position) {
            return wrapped.getItemViewType(position);
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            wrapped.setHasStableIds(hasStableIds);
        }

        @Override
        public long getItemId(int position) {
            return wrapped.getItemId(position);
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            wrapped.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
            return wrapped.onFailedToRecycleView(holder);
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            wrapped.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            wrapped.onViewDetachedFromWindow(holder);
        }

        @Override
        public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            wrapped.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            wrapped.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            wrapped.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            wrapped.onDetachedFromRecyclerView(recyclerView);
        }

        public RecyclerView.Adapter getWrappedAdapter() {
            return wrapped;
        }
    }
}
