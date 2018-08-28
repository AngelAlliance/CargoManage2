package com.sz.ljs.common.view;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.common.R;


/**
 * 2017/7/26
 * wangxiaoer
 * 功能描述：自定义仿IOS弹窗
 **/
public class AlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private boolean edtVisible = false;
    private EditText edt_;
    private LinearLayout ll_edt_;

    public AlertDialog(Context mContext) {
        this(mContext, false);
    }

    public AlertDialog(Context context, boolean edtVisible) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        this.edtVisible = edtVisible;
    }

    public AlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.alertdialog_view, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        edt_ = (EditText) view.findViewById(R.id.edt_);
        ll_edt_ = (LinearLayout) view.findViewById(R.id.ll_edt);
        if (edtVisible) {
            ll_edt_.setVisibility(View.VISIBLE);
            edt_.setVisibility(View.VISIBLE);
        } else {
            ll_edt_.setVisibility(View.GONE);
            edt_.setVisibility(View.GONE);
        }
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        return this;
    }

    public AlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setVisibility(View.GONE);
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertDialog setPositiveButton(String text,
                                         final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText(context.getString(R.string.str_yes));
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(edt_.getText().toString());
                if (null != listener) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertDialog setNegativeButton(String text,
                                         final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText(context.getString(R.string.str_cancel));
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertDialog setNegativeButtonVisibility() {
        showNegBtn = true;
        btn_neg.setText(context.getString(R.string.str_cancel));
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }


    /**
     * 设置字体颜色
     *
     * @param color
     */
    public AlertDialog setButtonColor(int color) {
        btn_pos.setTextColor(color);
        btn_neg.setTextColor(color);
        return this;
    }


    public void show() {
        setLayout();
        dialog.show();
        showInputMethod();
    }

    private void showInputMethod(){
        if(edt_.getVisibility() == View.VISIBLE) {
            dialog.getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        edt_.requestFocus();
                        imm.showSoftInput(edt_, 0);
                    }
                }
            }, 100);
        }
    }

    public void dissmiss() {
        dialog.cancel();
    }

    //设置编辑框值
    public AlertDialog setEditValue(String value){
        if(null != edt_){
            edt_.setText(value);
            edt_.selectAll();
        }
        return this;
    }

    //设置编辑框提示信息
    public AlertDialog setEditHint(String value){
        if(null != edt_){
            edt_.setHint(value);
        }
        return this;
    }

    public AlertDialog setEditHint(int value){
        if(null != edt_){
            edt_.setHint(value);
        }
        return this;
    }

    public AlertDialog setEditHeightAndMaxLength(int Height,int MaxLength){
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) edt_.getLayoutParams();
        linearParams.height=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Height, context.getResources().getDisplayMetrics()));
        edt_.setLayoutParams(linearParams);
        edt_.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MaxLength) });
        edt_.setBackgroundResource(R.drawable.shape_feedback_bg);
        return this;
    }

    //TODO 设置显示消息的文字对其方式，默认剧中
    public AlertDialog setMsgTextGravity(int gravity){
        if(null != txt_msg){
            txt_msg.setGravity(gravity);
        }
        return this;
    }
}
