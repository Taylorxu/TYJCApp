package com.wise.www.tyjcapp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.basestone.view.network.FlatMapResponse;
import com.wise.www.basestone.view.network.FlatMapTopRes;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.tyjcapp.BR;
import com.wise.www.tyjcapp.R;
import com.wise.www.basestone.view.adapter.XAdapter;
import com.wise.www.basestone.view.adapter.XViewHolder;
import com.wise.www.tyjcapp.bean.SystemCaseBean;
import com.wise.www.tyjcapp.bean.SystemWorkingCaseBean;
import com.wise.www.tyjcapp.databinding.FragmentFirstBinding;
import com.wise.www.tyjcapp.databinding.ItemFirstFragmentBinding;
import com.wise.www.tyjcapp.login.LoginActivity;
import com.wise.www.tyjcapp.main.ortherPage.BankDetailDataActivity;
import com.wise.www.tyjcapp.main.ortherPage.SearchPageActivity;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.wise.www.tyjcapp.main.MainActivity.PARAMKEY;


public class FirstFragment extends Fragment implements View.OnClickListener {
    FragmentFirstBinding fragmentBinding;
    List<SystemWorkingCaseBean> caseBeanList;

    public static FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        caseBeanList = getActivity().getIntent().getParcelableArrayListExtra(PARAMKEY);
        if (caseBeanList == null) {
            getBaseData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        fragmentBinding.title.setText(R.string.str_system_case);
        fragmentBinding.btnSearchFrame.setOnClickListener(this);
        setradebankName();
        xAdapter.setItemClickListener(itemClickListener);
        initListView();
        return fragmentBinding.getRoot();
    }

    XAdapter<SystemWorkingCaseBean, ItemFirstFragmentBinding> xAdapter = new XAdapter.SimpleAdapter<SystemWorkingCaseBean, ItemFirstFragmentBinding>(0, R.layout.item_first_fragment) {


        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(XViewHolder<SystemWorkingCaseBean, ItemFirstFragmentBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            SystemWorkingCaseBean bean = getItemData(position);
            holder.getBinding().setData2(bean);
            holder.getBinding().textName.setText(bean.getTradeSysName());
            holder.getBinding().textValue.setText(String.valueOf(bean.getTradeSysVolume()));
            if (bean.getTradeSysColour().indexOf("#") > -1) {
                holder.getBinding().linearLayoutRoot.setBackgroundColor(Color.parseColor(bean.getTradeSysColour().toUpperCase()));
                int startColor = Color.parseColor(bean.getTradeSysColour().toUpperCase());
                int endColor = Color.parseColor(bean.getTradeSysColour().toUpperCase().replace("#", "#7F"));
                int colors[] = {startColor, endColor};
                GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.getBinding().linearLayoutRoot.setBackgroundDrawable(bg);
                } else {
                    holder.getBinding().linearLayoutRoot.setBackground(bg);
                }
            }

            int prograss = Math.round(Float.parseFloat(bean.getTradeSysSucRate()));
            holder.getBinding().waveLoadingView.setProgressValue(prograss);
            holder.getBinding().waveLoadingView.setCenterTitle(bean.getTradeSysSucRate() + "%");
            holder.getBinding().waveLoadingView.setCenterTitleColor(Color.WHITE);
            holder.getBinding().waveLoadingView.setCenterTitleSize(12f);
            if (bean.getTradeSysColour().indexOf("#") > -1)
                holder.getBinding().waveLoadingView.setBorderColor(Color.parseColor(bean.getTradeSysColour().toUpperCase()));
            holder.getBinding().waveLoadingView.setBorderWidth(0.5f);
            if (bean.getTradeSysColour().indexOf("#") > -1)
                holder.getBinding().waveLoadingView.setWaveColor(Color.parseColor(bean.getTradeSysColour().toUpperCase()));
            /*if (bean.getTradeSysColour().indexOf("#") > -1)
                holder.getBinding().waveLoadingView.setWaveBgColor(Color.parseColor(bean.getTradeSysColour().toUpperCase()));*/


        }
    };
    XAdapter.OnItemClickListener<SystemWorkingCaseBean, ItemFirstFragmentBinding> itemClickListener = new XAdapter.OnItemClickListener<SystemWorkingCaseBean, ItemFirstFragmentBinding>() {
        @Override
        public void onItemClick(XViewHolder<SystemWorkingCaseBean, ItemFirstFragmentBinding> holder) {
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("TradeBankCode", tradeBankCode);
            stringMap.put("TradeSysCode", holder.getBinding().getData2().getTradeSysCode());
            stringMap.put("SysName", holder.getBinding().getData2().getTradeSysName());
            stringMap.put("BankName", tradebankName);
            BankDetailDataActivity.start(getContext(), stringMap);
        }
    };

    private void setradebankName() {
        fragmentBinding.textBankSearch.setText(tradebankName);
    }

    /**
     *
     */
    private void initListView() {
        createData();
        GridLayoutManager layoutmanager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        fragmentBinding.contentCase.setLayoutManager(layoutmanager);
        fragmentBinding.contentCase.setAdapter(xAdapter);
        fragmentBinding.contentCase.addItemDecoration(new RVItemDecoration(4, 20));

    }

    /**
     * 默认-1 全部。 当在搜索界面选好银行后，跳转此界面 根据此条件查询
     */
    String tradeBankCode = "-1";
    String tradebankName = "全部";


    private void createData() {
        ApiService.Creator.get().systemStatusServlet(tradeBankCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<SystemWorkingCaseBean>>>())
                .flatMap(new FlatMapTopRes<List<SystemWorkingCaseBean>>())
                .subscribe(new Subscriber<List<SystemWorkingCaseBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<SystemWorkingCaseBean> list) {
                        for (SystemWorkingCaseBean bank : list) {
                            for (SystemWorkingCaseBean beanSys : caseBeanList) {
                                if (bank.getTradeSysCode().equals(beanSys.getTradeSysCode())) {
                                    beanSys.setTradeSysVolume(bank.getTradeSysVolume());
                                    beanSys.setTradeSysSucRate(bank.getTradeSysSucRate());
                                }
                            }

                        }
                        xAdapter.setList(caseBeanList);
                    }
                });
    }


    private void getBaseData() {
        ApiService.Creator.get().dictDataServlet("BusinessSystem")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<SystemWorkingCaseBean>>>())
                .flatMap(new FlatMapTopRes<List<SystemWorkingCaseBean>>())
                .subscribe(new Subscriber<List<SystemWorkingCaseBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<SystemWorkingCaseBean> list) {
                        caseBeanList = list;
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_frame:
                Intent intent = new Intent(getActivity(), SearchPageActivity.class);
                startActivityForResult(intent, 9009);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 9009) {
                tradebankName = data.getStringExtra("TradeBankName");
                tradeBankCode = data.getStringExtra("TradeBankCode");
                setradebankName();
                createData();
            }
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
