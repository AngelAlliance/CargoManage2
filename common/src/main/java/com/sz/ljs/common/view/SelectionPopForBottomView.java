package com.sz.ljs.common.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.common.R;

import java.util.ArrayList;
import java.util.List;

//TODO 底部弹出选择空间
public class SelectionPopForBottomView extends BaseActivity {
    private static Context mContext;
    private LinearLayout ll_content,ll_kb;
    private TextView tv_titleView;
    private ListView lv_content;
    private static List<String> listData = new ArrayList<>();
    private ListAdapter adapter;
    private static String title;

    public interface ContentItemOnClickListener {
        void ItemOclick(int position);
    }

    public static ContentItemOnClickListener itemOnClickListener;

    public static void SelectionPopForBottomView(Context context, String Title, List<String> list, ContentItemOnClickListener listener) {
        mContext = context;
        title = Title;
        listData = list;
        itemOnClickListener = listener;
        BaseApplication.startActivity(SelectionPopForBottomView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_selection_pop_bottom);
        initView();
    }

    private void initView() {
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_kb = (LinearLayout) findViewById(R.id.ll_kb);
        tv_titleView = (TextView) findViewById(R.id.tv_titleView);
        lv_content = (ListView) findViewById(R.id.lv_content);
        adapter = new ListAdapter();
        lv_content.setAdapter(adapter);
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != itemOnClickListener) {
                    itemOnClickListener.ItemOclick(position);
                    finish();
                }
            }
        });
        tv_titleView.setText(title);
        ll_kb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodler) convertView.getTag();
            }
            if (null != listData && listData.size() > 0) {
                hodler.tv_name.setText(listData.get(position));

            }
            return convertView;
        }

        class ViewHodler {
            TextView tv_name;
        }

    }
}
