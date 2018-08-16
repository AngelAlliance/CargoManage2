package com.sz.ljs.common.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.ljs.common.R;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.view.NoscrollListView;

import java.util.ArrayList;
import java.util.List;


//TODO 左右滑动list数据适配器
public class FourSidesSlidListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExpressPackageModel> listData = new ArrayList<ExpressPackageModel>();
    private LayoutInflater inflater;

    public FourSidesSlidListAdapter(Context context, List<ExpressPackageModel> list) {
        mContext = context;
        listData = list;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ExpressPackageModel> list) {
        if (null != this.listData) {
            this.listData.clear();
        }
        this.listData = list;
        notifyDataSetChanged();
    }

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
            convertView = inflater.inflate(R.layout.item_four_sides_slid_list, null);
            hodler.ll_isOpen = (LinearLayout) convertView.findViewById(R.id.ll_isOpen);
            hodler.ll_ischecked = (LinearLayout) convertView.findViewById(R.id.ll_ischecked);
            hodler.iv_isOpen = (ImageView) convertView.findViewById(R.id.iv_isOpen);
            hodler.iv_ischecked = (ImageView) convertView.findViewById(R.id.iv_ischecked);
            hodler.tv_kongbai = (TextView) convertView.findViewById(R.id.tv_kongbai);
            hodler.tv_kongbai.setVisibility(View.GONE);
            hodler.tv_gx = (TextView) convertView.findViewById(R.id.tv_gx);
            hodler.tv_gx.setVisibility(View.GONE);
            hodler.tv_packageNum = (TextView) convertView.findViewById(R.id.tv_packageNum);
            hodler.tv_zhongzhuanzhuangtai = (TextView) convertView.findViewById(R.id.tv_zhongzhuanzhuangtai);
            hodler.tv_yundanhao = (TextView) convertView.findViewById(R.id.tv_yundanhao);
            hodler.tv_zidantiaoma = (TextView) convertView.findViewById(R.id.tv_zidantiaoma);
            hodler.tv_jianshu = (TextView) convertView.findViewById(R.id.tv_jianshu);
            hodler.tv_shizhong = (TextView) convertView.findViewById(R.id.tv_shizhong);
            hodler.tv_changkuaigao = (TextView) convertView.findViewById(R.id.tv_changkuaigao);
            hodler.listView = (NoscrollListView) convertView.findViewById(R.id.lv_data);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        hodler.ll_isOpen.setVisibility(View.VISIBLE);
        hodler.ll_ischecked.setVisibility(View.VISIBLE);
        if (null != listData && listData.size() > 0) {
            hodler.tv_packageNum.setText("" + listData.get(position).getPackageNumber());
            hodler.tv_zhongzhuanzhuangtai.setVisibility(View.GONE);
            hodler.tv_yundanhao.setText("");
            hodler.tv_zidantiaoma.setText("");
            hodler.tv_jianshu.setText("" + listData.get(position).getNumber());
            hodler.tv_shizhong.setText(listData.get(position).getWeight() + "KG");
            hodler.tv_changkuaigao.setText(listData.get(position).getLength() + "*"
                    + listData.get(position).getWidth() + "*"
                    + listData.get(position).getHeight());

            if (null != listData.get(position).getExpressModels()
                    && listData.get(position).getExpressModels().size() > 0
                    && true == listData.get(position).isOpen()) {
                Log.i("设置子apdapter", "进来了"+" ;position="+position);
                hodler.listView.setVisibility(View.VISIBLE);
                ExpressAdapter adapter = new ExpressAdapter(mContext, listData.get(position).getExpressModels(), position);
                hodler.listView.setAdapter(adapter);
                setListViewHeight(hodler.listView);
            } else {
                hodler.listView.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    class ViewHodler {
        LinearLayout ll_isOpen, ll_ischecked;
        ImageView iv_isOpen, iv_ischecked;
        TextView tv_kongbai, tv_gx, tv_packageNum, tv_zhongzhuanzhuangtai, tv_yundanhao, tv_zidantiaoma, tv_jianshu, tv_shizhong, tv_changkuaigao;
        NoscrollListView listView;
    }

    //为listview动态设置高度（有多少条目就显示多少条目）
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
