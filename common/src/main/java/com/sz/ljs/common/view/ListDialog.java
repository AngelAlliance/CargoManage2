package com.sz.ljs.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.ljs.common.R;
import com.sz.ljs.common.model.ListialogModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/8/22.
 */

public class ListDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private View view;
    private ListView lv_list;
    private TextView tv_titleView;
    private Button bt_queren;
    private ImageView iv_del;
    private LinearLayout ll_seach;
    private EditText et_seach;
    private ImageView iv_clean;
    private List<ListialogModel> listData = new ArrayList<>();
    private ListAdapter adapter;
    private int index = 0;
    private boolean isShowEdite = false;
    private List<ListialogModel> lists = new ArrayList<>(); //用来记录传入进来的数据


    public interface CallBackListener {
        void Result(int position, String name);
    }

    CallBackListener listener;

    public ListDialog(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public ListDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
    }

    public ListDialog creatDialog() {
        view = LayoutInflater.from(mContext).inflate(
                R.layout.view_listdialog, null);
        lv_list = (ListView) view.findViewById(R.id.lv_list);
        tv_titleView = (TextView) view.findViewById(R.id.tv_titleView);
        iv_del = (ImageView) view.findViewById(R.id.iv_del);
        bt_queren = (Button) view.findViewById(R.id.bt_queren);
        ll_seach = (LinearLayout) view.findViewById(R.id.ll_seach);
        et_seach = (EditText) view.findViewById(R.id.et_seach);
        iv_clean = (ImageView) view.findViewById(R.id.iv_clean);
        iv_del.setOnClickListener(this);
        bt_queren.setOnClickListener(this);
        iv_clean.setOnClickListener(this);
        adapter = new ListAdapter();
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                if (null != listData && listData.size() > 0) {
                    if (null != listener) {
                        listener.Result(index, listData.get(index).getName());
                    }
//                    for (int i = 0; i < listData.size(); i++) {
//                        listData.get(i).setChecked(false);
//                    }
//                    listData.get(position).setChecked(true);
//                    adapter.notifyDataSetChanged();
                }
            }
        });
        et_seach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    listData.clear();
                    listData.addAll(lists);
                    adapter.notifyDataSetChanged();
                } else {
                    if (s.length() >= 2) {
                        //TODO 开始检索
                        if (null != listData && listData.size() > 0 && null != lists && lists.size() > 0) {
                            listData.clear();
                            for (ListialogModel model : lists) {
                                if (model.getName().contains(s.toString().toUpperCase())
                                        || model.getId().contains(s.toString().toUpperCase())
                                        || model.getEn_name().contains(s.toString().toUpperCase())) {
                                    listData.add(model);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        super.show();
        return this;
    }


    public ListDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_titleView.setText(title);
        }
        return this;
    }

    public ListDialog setCallBackListener(CallBackListener listener) {
        this.listener = listener;
        return this;
    }

    public ListDialog setListData(List<ListialogModel> list) {
        List<ListialogModel> list1 = new ArrayList<>();
        list1.addAll(list);
        listData.clear();
        lists.clear();
        listData.addAll(list1);
        lists.addAll(list1);
        adapter.notifyDataSetChanged();
        return this;
    }

    //TODO 检索框是否显示
    public ListDialog setSeachEditTextShow(boolean isShow) {
        isShowEdite = isShow;
        if (false == isShow) {
            ll_seach.setVisibility(View.GONE);
        } else {
            ll_seach.setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * 显示对话框
     */
    public void show() {
        getWindow()
                .clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        if (view != null) {
            setContentView(view);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_del) {
            //TODO 关闭
            dismiss();
        } else if (id == R.id.bt_queren) {
            //TODO 确认
//            CallBack();
            dismiss();
        } else if (id == R.id.iv_clean) {
            //TODO 清空搜索输入框内容
            et_seach.setText("");
        }
    }

    private void CallBack() {
        if (null != listData && listData.size() > 0 && null != listener) {
            listener.Result(index, listData.get(index).getName());
        }
    }

    public class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler hodler = null;
            if (null == convertView) {
                hodler = new ViewHodler();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_selection_pop, null);
                hodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                hodler.iv_checked = (ImageView) convertView.findViewById(R.id.iv_checked);
                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodler) convertView.getTag();
            }
            if (null != listData && listData.size() > 0) {
                hodler.tv_name.setText(listData.get(position).getName());
                if (true == listData.get(position).isChecked()) {
                    hodler.iv_checked.setImageResource(R.mipmap.fb_g);
                } else {
                    hodler.iv_checked.setImageResource(R.mipmap.fb_b);
                }
            }
            return convertView;
        }

        class ViewHodler {
            ImageView iv_checked;
            TextView tv_name;
        }

    }
}
