package com.wise.www.basestone.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by XZG on 2018/2/9.
 */

public class XViewHolder<Data, Binding extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener{
    Binding binding;
    OnClickListener<Data, Binding> clickListener;

    public XViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    //为每个item中的BR 添加数据
    public void fill(int variableId, Data data) {
        binding.setVariable(variableId, data);
    }

    public Binding getBinding() {
        return binding;
    }

    /**
     * initHolder 时候可以set监听
     * @param clickListener
     */
    public void setClickListener(OnClickListener<Data, Binding> clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnClickListener<Data, Binding extends ViewDataBinding> {
        void onClick(XViewHolder<Data, Binding> holder, View v);
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null)
            clickListener.onClick(this, v);
    }

}
