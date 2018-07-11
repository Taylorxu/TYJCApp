package com.wise.www.basestone.view.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XZG on 2018/2/9.
 */

public abstract class XAdapter<Data, Binding extends ViewDataBinding> extends RecyclerView.Adapter<XViewHolder<Data, Binding>> {
    List<Data> list;

    public XAdapter() {

    }

    @Override
    public XViewHolder<Data, Binding> onCreateViewHolder(ViewGroup parent, int viewType) {
        final XViewHolder<Data, Binding> viewHolder = new XViewHolder<>(LayoutInflater.from(parent.getContext()).inflate(holderLayout(viewType), parent, false));
        if (itemClickListener != null) {
            viewHolder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(viewHolder);
                }
            });
        }

        initHolder(viewHolder, viewType);
        return viewHolder;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    protected void initHolder(XViewHolder<Data, Binding> holder, int viewType) {
    }

    public abstract int holderLayout(int viewType);

    public void setList(List<Data> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public Data getItemData(int position) {
        return list.get(position);
    }

    public List<Data> getList() {
        return list;
    }

    public static class SimpleAdapter<Data, Binding extends ViewDataBinding> extends XAdapter<Data, Binding> {
        int holderLayout;
        int variableId;

        public SimpleAdapter(int variableId, int holderLayout) {
            this.variableId = variableId;
            this.holderLayout = holderLayout;
        }


        @Override
        public void onBindViewHolder(XViewHolder<Data, Binding> holder, int position) {
            holder.fill(variableId, list.get(position));
        }

        @Override
        public int holderLayout(int viewType) {
            return holderLayout;
        }
    }


    private OnItemClickListener<Data, Binding> itemClickListener;

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<Data, Binding extends ViewDataBinding> {
        void onItemClick(XViewHolder<Data, Binding> holder);
    }


    public void addItem(Data data) {
        if (this.list == null) this.list = new ArrayList<>();
        this.list.add(data);
        notifyItemInserted(this.list.size() - 1);
    }

    public void addItem(Data data, int position) {
        if (this.list == null) this.list = new ArrayList<>();
        this.list.add(position, data);
        notifyItemInserted(position);
    }

    public void addItems(List<Data> datas) {
        if (datas == null || datas.isEmpty()) return;
        if (this.list == null || this.list.isEmpty()) {
            setList(datas);
            return;
        }
        this.list.addAll(datas);
        notifyItemRangeChanged(this.list.size() - datas.size(), datas.size());
    }

    public void addItems(List<Data> datas, int position) {
        if (datas == null || datas.isEmpty()) return;
        if (this.list == null || this.list.isEmpty()) {
            setList(datas);
            return;
        }
        if (this.list.size() - 1 < position) {
            addItems(datas);
            return;
        }
        this.list.addAll(position, datas);
        notifyItemRangeChanged(position, datas.size());
    }

    public void remove(int position) {
        if (getItemCount() == 0 || position < 0 || position > getItemCount() - 1) return;
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(Data data) {
        if (getItemCount() == 0) return;
        remove(this.list.indexOf(data));
    }

    public void removeAll() {
        if (getItemCount() == 0) return;
        this.list.clear();
        notifyDataSetChanged();
    }
}
