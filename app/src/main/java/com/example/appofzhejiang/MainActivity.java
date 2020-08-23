package com.example.appofzhejiang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.appofzhejiang.fragment3.TicketActivity;
import com.example.appofzhejiang.recyclerpage.RecyclerPageActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
=======
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
>>>>>>> 437de8f... 优化首页


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //UI Object
    private TextView tab_main; //首页按钮
    private TextView tab_flag; //景点按钮
    private TextView tab_shopping; //商城按钮
    private TextView tab_my; //我的按钮
    private ViewPager viewPager;
    //悬浮按钮
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    static {
        HeConfig.init("HE2008052029531704", "8fb20072276e48aa83d1200cce3653e0");
        //切换至开发版服务
        HeConfig.switchToDevService();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*****隐藏系统顶部栏*****/
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        tab_main.setSelected(true);//进去后选择第一项
        initFloatActionButton();//初始化悬浮按钮
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        tab_main = findViewById(R.id.tab_main);
        tab_flag = findViewById(R.id.tab_flag);
        tab_shopping = findViewById(R.id.tab_shopping);
        tab_my = findViewById(R.id.tab_my);

        tab_main.setOnClickListener(this);
        tab_flag.setOnClickListener(this);
        tab_shopping.setOnClickListener(this);
        tab_my.setOnClickListener(this);

        viewPager = findViewById(R.id.vPager);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(2); // 防止频繁的销毁视图
    }

    //重置所有文本的选中状态
    private void setSelected() {
        tab_main.setSelected(false);
        tab_flag.setSelected(false);
        tab_shopping.setSelected(false);
        tab_my.setSelected(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_main:
                viewPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.tab_flag:
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.tab_shopping:
                viewPager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.tab_my:
                viewPager.setCurrentItem(PAGE_FOUR);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_ONE:
                    setSelected();
                    tab_main.setSelected(true);
                    break;
                case PAGE_TWO:
                    setSelected();
                    tab_flag.setSelected(true);
                    break;
                case PAGE_THREE:
                    setSelected();
                    tab_shopping.setSelected(true);
                    break;
                case PAGE_FOUR:
                    setSelected();
                    tab_my.setSelected(true);
                    break;
            }
        }
    }

    //悬浮按钮配置
    public void initFloatActionButton() {
        floatingActionButton1 = findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);

        floatingActionButton1.setLabelText("攻略");
        floatingActionButton2.setLabelText("景点");
        floatingActionButton3.setLabelText("商城");
        floatingActionButton1.setImageResource(R.drawable.gonglue);
        floatingActionButton2.setImageResource(R.drawable.jingdian);
        floatingActionButton3.setImageResource(R.drawable.shangcheng);

        //跳转至攻略页面
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerPageActivity.class);
                startActivity(intent);
            }
        });

        //跳转至更多热门景点列表页面（暂无跳转 更多景点页面未写）
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, RecyclerPageActivity.class);
//                startActivity(intent);
            }
        });

        //跳转至商品列表页面(默认进入商品列表的门票页面)
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                intent.putExtra("num", "0");
                startActivity(intent);
            }
        });
    }

    //双击手机返回键退出 start
    private long firstTime = 0;//第一次返回按钮计时

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showShortToast("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    /**
     * 快捷显示short toast方法
     *
     * @param string
     */
    public void showShortToast(String string) {
        showShortToast(string, false);
    }

    /**
     * 快捷显示short toast方法
     *
     * @param string
     * @param isForceDismissProgressDialog
     */
    public void showShortToast(final String string, final boolean isForceDismissProgressDialog) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isForceDismissProgressDialog) {
                    dismissProgressDialog();
                }
                Toast.makeText(MainActivity.this, "" + string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 隐藏加载进度
     */
    public void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //把判断写在runOnUiThread外面导致有时dismiss无效，可能不同线程判断progressDialog.isShowing()结果不一致
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                if (progressDialog == null || progressDialog.isShowing() == false) {
                    Log.w("MainActivity", "dismissProgressDialog  progressDialog == null" +
                            " || progressDialog.isShowing() == false >> return;");
                    return;
                }
                progressDialog.dismiss();
            }
        });
    }
    //双击手机返回键退出 end
}