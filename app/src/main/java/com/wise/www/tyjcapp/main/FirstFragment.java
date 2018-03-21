package com.wise.www.tyjcapp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.BR;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.adapter.XAdapter;
import com.wise.www.tyjcapp.adapter.XViewHolder;
import com.wise.www.tyjcapp.bean.SystemCaseBean;
import com.wise.www.tyjcapp.databinding.FragmentFirstBinding;
import com.wise.www.tyjcapp.databinding.ItemFirstFragmentBinding;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {
    FragmentFirstBinding fragmentBinding;
    private List<SystemCaseBean> list;

    public static FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        fragmentBinding.title.setText(R.string.str_system_case);
        initListView();
        return fragmentBinding.getRoot();
    }

    XAdapter<SystemCaseBean, ItemFirstFragmentBinding> xAdapter = new XAdapter.SimpleAdapter<SystemCaseBean, ItemFirstFragmentBinding>(0, R.layout.item_first_fragment) {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(XViewHolder<SystemCaseBean, ItemFirstFragmentBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.getBinding().textName.setText(getItemData(position).getName());
            holder.getBinding().textValue.setText(getItemData(position).getValue());
            holder.getBinding().textValue.setTextColor(Color.rgb(255, 198, 0));
            holder.getBinding().waveLoadingView.setProgressValue(getItemData(position).getPercent());
            holder.getBinding().waveLoadingView.setCenterTitle(getItemData(position).getPercent() + "%");
            holder.getBinding().waveLoadingView.setCenterTitleColor(Color.WHITE);
            holder.getBinding().waveLoadingView.setCenterTitleSize(12f);
            holder.getBinding().waveLoadingView.setBorderColor(Color.parseColor("#62FFC600"));
            holder.getBinding().waveLoadingView.setBorderWidth(0.5f);
            holder.getBinding().waveLoadingView.setWaveColor(Color.rgb(255, 198, 0));
            holder.getBinding().waveLoadingView.setWaveBgColor(Color.parseColor("#62FFC600"));


        }
    };

    /**
     *
     */
    private void initListView() {

        createData();
        GridLayoutManager layoutmanager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        fragmentBinding.contentCase.setLayoutManager(layoutmanager);
        fragmentBinding.contentCase.setAdapter(xAdapter);
        fragmentBinding.contentCase.addItemDecoration(new RVItemDecoration(4, 20));
        xAdapter.setList(list);
    }

    private void createData() {
        list = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            SystemCaseBean systemCaseBean = new SystemCaseBean();
            systemCaseBean.setName("网银系统" + i);
            systemCaseBean.setValue(2000 + i + "");
            systemCaseBean.setPercent(20 + i + "");
            list.add(systemCaseBean);
        }
    }


    class RVItemDecoration extends RecyclerView.ItemDecoration {
        public RVItemDecoration(int space, int mDividerHeight) {
            this.space = space;
            this.mDividerHeight = mDividerHeight;
            this.paint = new Paint();
            this.paint1 = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(getResources().getColor(R.color.color_6f5));
            paint1.setAntiAlias(true);
            paint1.setColor(getResources().getColor(R.color.color_e6));
        }

        private int space;
        private int mDividerHeight;
        private Paint paint, paint1;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.right = space;
            }
            outRect.top = mDividerHeight;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++) {////描画  每条分割线
                View view = parent.getChildAt(i);
                // view.getTop() 所描述的是距离，不是点
                float dividerTop = view.getTop() - mDividerHeight;
                float dividerLeft = parent.getPaddingLeft();
                float dividerBottom = view.getTop();
                float dividerRight = parent.getWidth() - parent.getPaddingRight();
                c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, paint);

            }
            for (int i = 0; i < childCount; i++) {//描画 每个 item右边的竖条分割线
                View view = parent.getChildAt(i);
                if (parent.getChildLayoutPosition(view) % 2 == 0) {
                    float dividerRight = view.getRight() + space;
                    float dividerLeft = view.getRight() - view.getLeft();
                    c.drawRect(dividerLeft, view.getTop(), dividerRight, view.getBottom(), paint1);
                }

            }
        }
    }

}
