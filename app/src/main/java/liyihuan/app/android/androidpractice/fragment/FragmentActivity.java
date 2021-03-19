package liyihuan.app.android.androidpractice.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.mrouter_annotation.MRouter;

@MRouter(path = "/app/FragmentActivity")
public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    HomeFragment homeFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;
    MineFragment mineFragment;

    LinearLayout tab_home;
    LinearLayout tab_two;
    LinearLayout tab_three;
    LinearLayout tab_mine;

    ImageView iv_tab_home;
    ImageView iv_tab_two;
    ImageView iv_tab_three;
    ImageView iv_tab_mine;

    TextView tv_tab_home;
    TextView tv_tab_two;
    TextView tv_tab_three;
    TextView tv_tab_mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initView();
        initFragment();

    }

    private void initFragment(){

        homeFragment = new HomeFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        mineFragment = new MineFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_frame,homeFragment,"HomeFragment").show(homeFragment);
        transaction.add(R.id.main_frame,twoFragment,"TwoFragment").hide(twoFragment);
        transaction.add(R.id.main_frame,threeFragment,"ThreeFragment").hide(threeFragment);
        transaction.add(R.id.main_frame,mineFragment,"MineFragment").hide(mineFragment);
        transaction.commitAllowingStateLoss();

    }

    private void initView(){
        tab_home = findViewById(R.id.tab_home);
        tab_two = findViewById(R.id.tab_two);
        tab_three = findViewById(R.id.tab_three);
        tab_mine = findViewById(R.id.tab_mine);

        iv_tab_home = findViewById(R.id.iv_tab_home);
        iv_tab_two = findViewById(R.id.iv_tab_two);
        iv_tab_three = findViewById(R.id.iv_tab_three);
        iv_tab_mine = findViewById(R.id.iv_tab_mine);

        tv_tab_home = findViewById(R.id.tv_tab_home);
        tv_tab_two = findViewById(R.id.tv_tab_two);
        tv_tab_three = findViewById(R.id.tv_tab_three);
        tv_tab_mine = findViewById(R.id.tv_tab_mine);

        tab_home.setOnClickListener(this);
        tab_two.setOnClickListener(this);
        tab_three.setOnClickListener(this);
        tab_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                changeFragment(R.id.tab_home);
                clearTabStatus();
                iv_tab_home.setImageResource(R.mipmap.tab_icon_lesson_selected);
                tv_tab_home.setTextColor(getColor(R.color.c_f26355));

                break;
            case R.id.tab_two:
                changeFragment(R.id.tab_two);
                clearTabStatus();
                iv_tab_two.setImageResource(R.mipmap.tab_icon_homework_selected);
                tv_tab_two.setTextColor(getColor(R.color.c_f26355));
                break;
            case R.id.tab_three:
                changeFragment(R.id.tab_three);
                clearTabStatus();
                iv_tab_three.setImageResource(R.mipmap.tab_icon_qa_selected);
                tv_tab_three.setTextColor(getColor(R.color.c_f26355));
                break;
            case R.id.tab_mine:
                changeFragment(R.id.tab_mine);
                clearTabStatus();
                iv_tab_mine.setImageResource(R.mipmap.tab_icon_mine_selected);
                tv_tab_mine.setTextColor(getColor(R.color.c_f26355));
                break;
        }
    }

    private void clearTabStatus() {
        iv_tab_home.setImageResource(R.mipmap.tab_icon_lesson_default);
        iv_tab_two.setImageResource(R.mipmap.tab_icon_homework_default);
        iv_tab_three.setImageResource(R.mipmap.tab_icon_qa_default);
        iv_tab_mine.setImageResource(R.mipmap.tab_icon_mine_default);

        tv_tab_home.setTextColor(getColor(R.color.default_color_9));
        tv_tab_two.setTextColor(getColor(R.color.default_color_9));
        tv_tab_three.setTextColor(getColor(R.color.default_color_9));
        tv_tab_mine.setTextColor(getColor(R.color.default_color_9));
    }

    private void changeFragment(int id){
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        hideFragments(transaction);

        switch (id){
            case R.id.tab_home:
                transaction.show(homeFragment);
                break;
            case R.id.tab_two:
                transaction.show(twoFragment);
                break;
            case R.id.tab_three:
                transaction.show(threeFragment);
                break;
            case R.id.tab_mine:
                transaction.show(mineFragment);
                break;
            default:
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        transaction.hide(homeFragment);
        transaction.hide(twoFragment);
        transaction.hide(threeFragment);
        transaction.hide(mineFragment);
    }


}