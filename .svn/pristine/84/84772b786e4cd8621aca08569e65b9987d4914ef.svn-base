package com.wise.www.tyjcapp.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.wise.www.basestone.view.helper.EEMsgToastHelper;
import com.wise.www.basestone.view.network.FlatMapResponse;
import com.wise.www.basestone.view.network.FlatMapTopRes;
import com.wise.www.basestone.view.network.Notification;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.basestone.view.network.RxBus;
import com.wise.www.basestone.view.util.LogUtils;
import com.wise.www.tyjcapp.R;
import com.wise.www.basestone.view.adapter.XAdapter;
import com.wise.www.basestone.view.adapter.XViewHolder;
import com.wise.www.tyjcapp.bean.MemberTradeBean;
import com.wise.www.tyjcapp.bean.OneSystemBean;
import com.wise.www.tyjcapp.bean.SystemCaseBean;
import com.wise.www.tyjcapp.bean.SystemWorkingCaseBean;
import com.wise.www.tyjcapp.databinding.FragmentFirstBinding;
import com.wise.www.tyjcapp.databinding.ItemFirstFragmentBinding;
import com.wise.www.tyjcapp.main.helperClass.RVItemDecoration;
import com.wise.www.tyjcapp.main.ortherPage.BankDetailDataActivity;
import com.wise.www.tyjcapp.main.ortherPage.SearchPageActivity;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.wise.www.tyjcapp.main.MainActivity.PARAMKEY;


public class FirstFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    FragmentFirstBinding fragmentBinding;
    List<SystemWorkingCaseBean> caseBeanList;
    /**
     * 默认-1 全部。 当在搜索界面选好银行后，跳转此界面 根据此条件查询
     */
    String tradeBankCode = "-1";
    String tradebankName = "全部";
    Subscription notification;

    public static FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        caseBeanList = getActivity().getIntent().getParcelableArrayListExtra(PARAMKEY);
        //第一次登录，没有写入服务器地址  写service address-------> 登录--------MainPage---->getBaseData
        if (caseBeanList == null) {
            getBaseData();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notification = Notification.register(action1);
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        fragmentBinding.title.setText(R.string.str_system_case);
        fragmentBinding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        fragmentBinding.refreshLayout.setOnRefreshListener(this);
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

            initPieCHartView(bean, holder.getBinding().pieChartView);

        }
    };

    private void initPieCHartView(SystemWorkingCaseBean bean, PieChart pieCharts) {
        pieCharts.setTouchEnabled(false);
        pieCharts.setUsePercentValues(true);
        pieCharts.getDescription().setEnabled(false);
        pieCharts.getLegend().setEnabled(false);
        pieCharts.setDragDecelerationFrictionCoef(0.95f);
        pieCharts.setDrawHoleEnabled(true);//置空中间,否则是扇形图
        pieCharts.setHoleColor(Color.WHITE);
        pieCharts.setDrawCenterText(true);
        pieCharts.setCenterTextSize(12f);
        pieCharts.setTransparentCircleColor(Color.WHITE);
        pieCharts.setTransparentCircleAlpha(110);
        pieCharts.setHoleRadius(58f);
        pieCharts.setTransparentCircleRadius(61f);
        pieCharts.setRotationAngle(0);
        pieCharts.setRotationEnabled(false);
        pieCharts.setHighlightPerTapEnabled(false);
        pieCharts.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        setPieChartData(bean, pieCharts);
    }

    private void setPieChartData(SystemWorkingCaseBean data, PieChart pieChart) {
        float prograss = Float.parseFloat(data.getTradeSysSucRate());
        String centerTitle = data.getTradeSysSucRate();
        int pieChartColor = Color.parseColor(data.getTradeSysColour().toUpperCase());
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(prograss));
        entries.add(new PieEntry(100 - prograss));
        pieChart.setCenterText(generateCenterSpannableText(centerTitle));

        PieDataSet dataSet = new PieDataSet(entries, "PieData");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(pieChartColor, Color.rgb(229, 229, 229));

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(false);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    /**
     * item 跳转到BankDetailDataActivity 每个系统下的某个银行（或全部）详细环比
     * TradeBankCode、TradeSysCode 查询参数
     * SysName title
     * BankName 界面银行名称
     */
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

    /**
     * 银行搜索跳转TextView 展示
     */
    private void setradebankName() {
        fragmentBinding.textBankSearch.setText(tradebankName);
    }

    /**
     * 初始化 recycleVIew 、
     * 查询数据
     */
    private void initListView() {
        if (null != caseBeanList) createData();
        GridLayoutManager layoutmanager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        fragmentBinding.contentCase.setLayoutManager(layoutmanager);
        fragmentBinding.contentCase.setAdapter(xAdapter);
        fragmentBinding.contentCase.addItemDecoration(new RVItemDecoration(4, 20));

    }

    /**
     * 全部 银行 tradeBankCode=-1 ，
     * 根据 tradeBankCode 参数查询出交易量的数据，在和系统数据集合进行合并
     */
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
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        fragmentBinding.refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<SystemWorkingCaseBean> list) {
                        if (null == caseBeanList) return;
                        for (SystemWorkingCaseBean bank : list) {
                            for (SystemWorkingCaseBean beanSys : caseBeanList) {
                                if (bank.getTradeSysCode().equals(beanSys.getTradeSysCode())) {
                                    beanSys.setTradeSysVolume(bank.getTradeSysVolume());
                                    beanSys.setTradeSysSucRate(bank.getTradeSysSucRate());
                                }
                            }

                        }
                        xAdapter.setList(caseBeanList);
                        fragmentBinding.refreshLayout.setRefreshing(false);

                    }
                });
    }

    /**
     * 获取系统数据
     */
    private void getBaseData() {
        ApiService.Creator.get().dictDataServlet("BusinessSystem")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<SystemWorkingCaseBean>>>())
                .flatMap(new FlatMapTopRes<List<SystemWorkingCaseBean>>())
                .subscribe(new Subscriber<List<SystemWorkingCaseBean>>() {
                    @Override
                    public void onCompleted() {
                        if (null != caseBeanList) createData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        if (null == caseBeanList) setEmptyView(true);
                        fragmentBinding.refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<SystemWorkingCaseBean> list) {
                        setEmptyView(list.size() == 0 ? true : false);
                        caseBeanList = list;
                        fragmentBinding.refreshLayout.setRefreshing(false);
                    }
                });
    }

    /**
     * 跳转到银行筛选界面
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_frame:
                Intent intent = new Intent(getActivity(), SearchPageActivity.class);
                startActivityForResult(intent, 9009);
                break;
        }
    }

    /**
     * 从筛选界面返回来
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    @Override
    public void onRefresh() {
        getBaseData();

    }

    private void setEmptyView(boolean isEmpty) {
        if (isEmpty) {
            fragmentBinding.contentCase.setVisibility(View.GONE);
            fragmentBinding.tvEmptyView.setVisibility(View.VISIBLE);
            fragmentBinding.tvEmptyView.setText(getResources().getString(R.string.str_empty_data1));
        } else {
            fragmentBinding.tvEmptyView.setVisibility(View.GONE);
            fragmentBinding.contentCase.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 为recycleView 的每一个item 描绘 分割线
     */
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

    /**
     * 从top排行界面 itemclick ------>mainActivity{refreshByBankCode}-------->FirstFragment
     */
    Action1<Notification> action1 = new Action1<Notification>() {
        @Override
        public void call(Notification notification) {
            if (notification.getCode() == 001) {
                MemberTradeBean tradeBean = (MemberTradeBean) notification.getExtra();
                tradebankName = tradeBean.getTradeBankName();
                tradeBankCode = tradeBean.getTradeBankCode();
                setradebankName();
                fragmentBinding.refreshLayout.setRefreshing(true);
                createData();

            }
        }
    };


    @SuppressLint("ResourceAsColor")
    private SpannableString generateCenterSpannableText(String pieChartData) {
        pieChartData = pieChartData.equals("100.00") ? "100%" : pieChartData+"%";
//        String centerText = Float.valueOf(pieChartData) + "%";
        SpannableString s;
        s = new SpannableString(pieChartData);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);

        return s;
    }

}
