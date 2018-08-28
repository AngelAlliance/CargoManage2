package com.sz.ljs.packgoods.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.packgoods.R;

//TODO 称重输入dialog
public class BagWeightDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private View view;
    private TextView tv_titleView, tv_pack_code, tv_pack_zhongliang;
    private EditText et_dianzizhongliang, et_chang, et_kuan, et_gao;
    private Button bt_quxiao, bt_queding;

    public interface CallBackListener {
        void Result(String dzcWeight, String chang, String kuan, String gao);
    }

    private CallBackListener listener;

    public interface CallBackQuXiao{
        void Onclick();
    }

    private CallBackQuXiao backQuXiao;

    public BagWeightDialog(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public BagWeightDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
    }

    public BagWeightDialog creatDialog() {
        view = LayoutInflater.from(mContext).inflate(
                R.layout.view_bagweight_dialg, null);
        tv_titleView = (TextView) view.findViewById(R.id.tv_titleView);
        tv_pack_code = (TextView) view.findViewById(R.id.tv_pack_code);
        tv_pack_zhongliang = (TextView) view.findViewById(R.id.tv_pack_zhongliang);
        et_dianzizhongliang = (EditText) view.findViewById(R.id.et_dianzizhongliang);
        et_chang = (EditText) view.findViewById(R.id.et_chang);
        et_kuan = (EditText) view.findViewById(R.id.et_kuan);
        et_gao = (EditText) view.findViewById(R.id.et_gao);
        bt_quxiao = (Button) view.findViewById(R.id.bt_quxiao);
        bt_queding = (Button) view.findViewById(R.id.bt_queding);
        bt_quxiao.setOnClickListener(this);
        bt_queding.setOnClickListener(this);
        super.show();
        return this;
    }

    //TODO 设置包编号
    public BagWeightDialog setPackCode(String code) {
        if (!TextUtils.isEmpty(code)) {
            tv_pack_code.setText(code);
        }
        return this;
    }

    //TODO 设置包重量
    public BagWeightDialog setPackWeight(String weight) {
        if (!TextUtils.isEmpty(weight)) {
            tv_pack_zhongliang.setText(weight);
        }
        return this;
    }

    public BagWeightDialog setCallBackListener(CallBackListener listener) {
        this.listener = listener;
        return this;
    }
    public BagWeightDialog setCallBackQuXiao(CallBackQuXiao listener) {
        this.backQuXiao = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_queding) {
            if (TextUtils.isEmpty(et_dianzizhongliang.getText().toString().trim())) {
                Toast.makeText(mContext, "请输入电子秤重量", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(et_chang.getText().toString().trim())) {
                Toast.makeText(mContext, "请输入长度", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(et_kuan.getText().toString().trim())) {
                Toast.makeText(mContext, "请输入宽度", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(et_gao.getText().toString().trim())) {
                Toast.makeText(mContext, "请输入高度", Toast.LENGTH_LONG).show();
                return;
            }
            if (null != listener) {
                listener.Result(et_dianzizhongliang.getText().toString().trim(), et_chang.getText().toString().trim()
                        , et_kuan.getText().toString().trim(), et_gao.getText().toString().trim());
            }
            dismiss();
        } else if (id == R.id.bt_quxiao) {
            if(null!=backQuXiao){
                backQuXiao.Onclick();
            }
            dismiss();
        }
    }


    //TODO 显示对话框
    public void show() {
        getWindow()
                .clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        if (view != null) {
            setContentView(view);
        }
    }
}
