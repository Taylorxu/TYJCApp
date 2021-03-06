package com.wise.www.tyjcapp.main.ortherPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.Converters;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.wise.www.basestone.view.adapter.XAdapter;
import com.wise.www.basestone.view.adapter.XViewHolder;
import com.wise.www.basestone.view.helper.EEMsgToastHelper;
import com.wise.www.basestone.view.network.FlatMapResponse;
import com.wise.www.basestone.view.network.FlatMapTopRes;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.basestone.view.util.LogUtils;
import com.wise.www.basestone.view.view.PagingRecyclerView;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.bean.AlarmSystemBean;
import com.wise.www.tyjcapp.bean.OneSystemBean;
import com.wise.www.tyjcapp.bean.WrapOnSystemBean;
import com.wise.www.tyjcapp.databinding.ActivityBankDetailDataBinding;
import com.wise.www.tyjcapp.databinding.ItemOneBankBinding;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BankDetailDataActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static String BANKNAMEKEY = "BANKNAME";
    ActivityBankDetailDataBinding binding;
    private String tradebankName, tradeBankCode, tradeSysCode;

    public static void start(Context context, Map<String, String> stringMap) {
        Intent starter = new Intent(context, BankDetailDataActivity.class);
        starter.putExtra(BANKNAMEKEY, (Serializable) stringMap);
        context.startActivity(starter);
    }

    private PieChart[] pieCharts;
    XAdapter<OneSystemBean, ItemOneBankBinding> adapter = new XAdapter.SimpleAdapter<OneSystemBean, ItemOneBankBinding>(0, R.layout.item_one_bank) {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(XViewHolder<OneSystemBean, ItemOneBankBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            OneSystemBean systemBean = getItemData(position);
            holder.getBinding().textDateHeader.setText(systemBean.getDateTime());
            holder.getBinding().textTotal.setText(formatNumber(systemBean.getTradeVolume()));
            holder.getBinding().tvDynamic.setText(formatNumber(systemBean.getTradeDynamicVolume()));
            holder.getBinding().tvStatic.setText(formatNumber(systemBean.getTradeStaticVolume()));
            pieCharts = new PieChart[]{holder.getBinding().pieChartEmergency, holder.getBinding().pieChartUrgency, holder.getBinding().pieChartNormal};
            setPieChartData(systemBean);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_detail_data);
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(this);
        getExtra();
        binding.contentData.setLayoutManager(new LinearLayoutManager(this));
        binding.contentData.setAdapter(adapter);
    }


    private void getExtra() {
        Map<String, String> stringMap = (Map<String, String>) getIntent().getSerializableExtra(BANKNAMEKEY);
        tradebankName = stringMap.get("BankName");
        tradeSysCode = stringMap.get("TradeSysCode");
        tradeBankCode = stringMap.get("TradeBankCode");
        binding.textBankSearch.setText(tradebankName);
        binding.title.setText(stringMap.get("SysName"));
        getData(tradeBankCode, tradeSysCode);
    }

    private void getData(String tradeBankCode, String tradeSysCode) {
        ApiService.Creator.get().oneSystemStatusServlet(tradeSysCode, tradeBankCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<WrapOnSystemBean>())
                .subscribe(new Subscriber<WrapOnSystemBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        binding.refreshLayout.setRefreshing(false);
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WrapOnSystemBean bean) {
                        if (null != bean.getCurrent() || null != bean.getHistory()) {
                            showEmpty(false);
                            List<OneSystemBean> adapterList = new ArrayList<>();
                            adapterList.add(bean.getCurrent());
                            adapterList.add(bean.getHistory());
                            adapter.setList(adapterList);
                        } else {
                            Toast.makeText(getBaseContext(), bean.getReason(), Toast.LENGTH_SHORT).show();
                            showEmpty(true);
                        }
                        binding.refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void showEmpty(boolean isVisible) {
        binding.tvEmpty.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        binding.tvEmpty.setText(isVisible ? getResources().getString(R.string.str_empty_data) : "");
        binding.contentData.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    private void setPieChartData(OneSystemBean data) {
        initPieCHartView();
        for (int i = 0; i < pieCharts.length; i++) {
            ArrayList<PieEntry> entries = new ArrayList<>();
            if (i == 0) {
                entries.add(new PieEntry(Float.parseFloat(data.getTradeSucRate())));
                entries.add(new PieEntry(100 - Float.parseFloat(data.getTradeSucRate())));
                pieCharts[i].setCenterText(generateCenterSpannableText(i, data.getTradeSucRate()));
            } else if (i == 1) {
                entries.add(new PieEntry(Float.parseFloat(data.getTradeStaticSucRate())));
                entries.add(new PieEntry(100 - Float.parseFloat(data.getTradeStaticSucRate())));
                pieCharts[i].setCenterText(generateCenterSpannableText(i, data.getTradeStaticSucRate()));
            } else {
                entries.add(new PieEntry(Float.parseFloat(data.getTradeDynamicSucRate())));
                entries.add(new PieEntry(100 - Float.parseFloat(data.getTradeDynamicSucRate())));
                pieCharts[i].setCenterText(generateCenterSpannableText(i, data.getTradeDynamicSucRate()));
            }

            PieDataSet dataSet = new PieDataSet(entries, "PieData" + i);
            dataSet.setDrawIcons(false);
            dataSet.setSliceSpace(1f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(mColors[i]);

            PieData pieData = new PieData(dataSet);
            pieData.setDrawValues(false);//不在环形上显示数据

            pieCharts[i].setData(pieData);
            pieCharts[i].invalidate();
        }
    }

    private void initPieCHartView() {
        for (int i = 0; i < pieCharts.length; i++) {
            pieCharts[i].setUsePercentValues(true);
            pieCharts[i].getDescription().setEnabled(false);
            pieCharts[i].getLegend().setEnabled(false);

            pieCharts[i].setDragDecelerationFrictionCoef(0.95f);

            pieCharts[i].setDrawHoleEnabled(true);//置空中间,否则是扇形图
            pieCharts[i].setHoleColor(Color.WHITE);
            pieCharts[i].setDrawCenterText(true);
            pieCharts[i].setCenterTextSize(12f);


            pieCharts[i].setTransparentCircleColor(Color.WHITE);
            pieCharts[i].setTransparentCircleAlpha(110);

            pieCharts[i].setHoleRadius(58f);
            pieCharts[i].setTransparentCircleRadius(61f);

            // 旋转
            pieCharts[i].setRotationAngle(0);
            pieCharts[i].setRotationEnabled(true);
            pieCharts[i].setHighlightPerTapEnabled(true);
            pieCharts[i].animateY(1400, Easing.EasingOption.EaseInOutQuad);

        }
    }

    @SuppressLint("Range")
    private int[] mtextColors = new int[]{
            Color.rgb(255, 90, 107),
            Color.rgb(255, 199, 66),
            Color.rgb(38, 222, 138)
    };

    @SuppressLint("Range")
    private int[][] mColors = new int[][]{
            {Color.rgb(255, 90, 107), Color.rgb(229, 229, 229)},
            {Color.rgb(255, 199, 66), Color.rgb(229, 229, 229)},
            {Color.rgb(38, 222, 138), Color.rgb(229, 229, 229)}
    };

    @SuppressLint("ResourceAsColor")
    private SpannableString generateCenterSpannableText(int p, String pieChartData) {
        SpannableString s = null;
        if (pieChartData.equals("100.00")) {
            pieChartData = "100";
        }
        s = new SpannableString(pieChartData + "%");
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(mtextColors[p]), 0, s.length(), 0);

        return s;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search_frame:
                Intent intent = new Intent(this, SearchPageActivity.class);
                startActivityForResult(intent, 9008);
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData(tradeBankCode, tradeSysCode);
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
            if (requestCode == 9008) {
                tradebankName = data.getStringExtra("TradeBankName");
                tradeBankCode = data.getStringExtra("TradeBankCode");
                binding.textBankSearch.setText(tradebankName);
                onRefresh();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String formatNumber(String number) {
        int where = 0;
        final StringBuffer finalNumber = new StringBuffer();
        List<String> formatNumber = new ArrayList<>();
        if (number.length() > 3) {
            char[] charArray = number.toCharArray();
            for (int i = charArray.length - 1; i >= 0; i--) {
                if (where != 0 && where % 3 == 0) {
                    formatNumber.add(",");
                }
                formatNumber.add(String.valueOf(charArray[i]));
                where++;
            }
        } else {
            return number;
        }
        Collections.reverse(formatNumber);
        formatNumber.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                finalNumber.append(s);
            }
        });
        return finalNumber.toString();
    }
}
