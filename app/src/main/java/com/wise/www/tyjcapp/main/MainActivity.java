package com.wise.www.tyjcapp.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.bean.SystemCaseBean;
import com.wise.www.tyjcapp.bean.SystemWorkingCaseBean;
import com.wise.www.tyjcapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainAdapter mSectionsPagerAdapter;
    ActivityMainBinding mainBinding;
    static String PARAMKEY = "systemData";

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, MainActivity.class);
        if (bundle != null)
            starter.putParcelableArrayListExtra(PARAMKEY, bundle.getParcelableArrayList(PARAMKEY));
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBankSystemData(getIntent());
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mSectionsPagerAdapter = new MainAdapter(getSupportFragmentManager());
        mainBinding.container.setAdapter(mSectionsPagerAdapter);
        mainBinding.container.addOnPageChangeListener(onPageChangeListener);
        mainBinding.navigation.setOnCheckedChangeListener(OnNISListener);
    }

    /**
     * firstfragment 在oattach 是调用此方法获取数据
     *
     * @param intent
     */
    public List<SystemWorkingCaseBean> getBankSystemData(Intent intent) {
        return intent.getParcelableArrayListExtra(PARAMKEY);
    }

    RadioGroup.OnCheckedChangeListener OnNISListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.tab1:
                    mainBinding.container.setCurrentItem(0, false);
                    break;
                case R.id.tab2:
                    mainBinding.container.setCurrentItem(1, false);
                    break;
                case R.id.tab3:
                    mainBinding.container.setCurrentItem(2, false);
                    break;
                case R.id.tab4:
                    mainBinding.container.setCurrentItem(3, false);
                    break;
            }
        }
    };


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mainBinding.tab1.setChecked(true);
                    break;
                case 1:
                    mainBinding.tab2.setChecked(true);
                    break;
                case 2:
                    mainBinding.tab3.setChecked(true);
                    break;
                case 3:
                    mainBinding.tab4.setChecked(true);
                    break;
            }

        }
    };


}
