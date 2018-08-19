package com.sz.ljs.warehousing.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.warehousing.R;
import com.sz.ljs.warehousing.model.SubnitModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加子单界面
 */
public class AddSubunitActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_addView;
    private TextView tv_zongjianshu, tv_zongshizhong, tv_zongcaiji;
    private Button btn_queren, btn_xinzeng;
    private List<SubnitModel> subnitList = new ArrayList<>();
    private int pice = 0;//件数
    private int zongshizhong = 0; //总实重
    private int zongcaiji = 0;  //总材积

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsubunit);
        initView();
        setListener();
    }

    private void initView() {
        ll_addView = (LinearLayout) findViewById(R.id.ll_addView);
        tv_zongjianshu = (TextView) findViewById(R.id.tv_zongjianshu);
        tv_zongshizhong = (TextView) findViewById(R.id.tv_zongshizhong);
        tv_zongcaiji = (TextView) findViewById(R.id.tv_zongcaiji);
        btn_queren = (Button) findViewById(R.id.btn_queren);
        btn_xinzeng = (Button) findViewById(R.id.btn_xinzeng);
        addView();
    }

    private void setListener() {
        btn_queren.setOnClickListener(this);
        btn_xinzeng.setOnClickListener(this);
        findViewById(R.id.btn_shanchu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_queren) {
            //TODO 确认
        } else if (id == R.id.btn_xinzeng) {
            //TODO 新增
            addView();
        } else if (id == R.id.btn_shanchu) {
            Log.i("点击删除按钮", "position=" + v.getTag());
            removeView(v);
        } else if (id == R.id.iv_scan) {
            handelScan(v);
            Log.i("点击扫描按钮", "position=" + v.getTag());
        }
    }

    //TODO 处理扫描
    private void handelScan(final View view){
        ScanView.ScanView(new ScanView.SacnCallBack() {
            @Override
            public void onResult(String result) {
                int index=(Integer) view.getTag();
                View view1=ll_addView.getChildAt(index);
                EditText et_zidanhao=(EditText) view1.findViewById(R.id.et_zidanhao);
            }
        });
    }

    //TODO 移除
    private void removeView(View view) {
        if (0 < ll_addView.getChildCount()) {
            int position = (Integer) view.getTag();
            pice--;
            if (null != subnitList && subnitList.size() == position + 1) {
                zongshizhong = zongshizhong - Integer.parseInt(subnitList.get(position).getSubnitWeight());
                zongcaiji = zongcaiji - Integer.parseInt(subnitList.get(position).getSubnitWolume());
                subnitList.remove(position);
            }
            tv_zongjianshu.setText("" + pice);
            tv_zongshizhong.setText("" + zongshizhong + "KG");
            tv_zongcaiji.setText("" + zongcaiji + "CM²");
            ll_addView.removeView(ll_addView.getChildAt((Integer) view.getTag()));
            if(0==ll_addView.getChildCount()){
                addView();
            }
        } else {
            subnitList.remove((Integer) view.getTag());
            pice = 0;
            zongshizhong = 0;
            zongcaiji = 0;
            tv_zongjianshu.setText("");
            tv_zongshizhong.setText("");
            tv_zongcaiji.setText("");
        }
    }

    //TODO 新增
    private void addView() {
        View view = LayoutInflater.from(AddSubunitActivity.this).inflate(R.layout.view_subnit, null);
        int position = ll_addView.getChildCount();
        Button button = (Button) view.findViewById(R.id.btn_shanchu);
        button.setTag(position);
        button.setOnClickListener(this);
        ImageView ic_scan = (ImageView) view.findViewById(R.id.iv_scan);
        ic_scan.setTag(position);
        ic_scan.setOnClickListener(this);
//        EditText et_zidanhao=(EditText) view.findViewById(R.id.et_zidanhao);
//        et_zidanhao.
        ll_addView.addView(view);
        pice++;
        tv_zongjianshu.setText("" + pice);
    }
}
