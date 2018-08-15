package com.sz.ljs.common.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.ljs.common.R;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;

import java.util.ArrayList;
import java.util.List;

//TODO 左右滑动list头部菜单适配器
public class FourSidesSlidListTitleAdapter extends BaseAdapter {

    private Context mContext;
    private List<FourSidesSlidListTitileModel> listData = new ArrayList<>();
    private LayoutInflater inflater;

    public FourSidesSlidListTitleAdapter(Context context, List<FourSidesSlidListTitileModel> list) {
        mContext = context;
        listData = list;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<FourSidesSlidListTitileModel> list) {
        if (null != this.listData) {
            this.listData.clear();
        }
        this.listData = list;
        Log.i("这里的listData值大小","size="+listData.size());
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
            convertView = inflater.inflate(R.layout.item_four_sides_slid_list, parent, false);
            hodler.tv_kongbai = (TextView) convertView.findViewById(R.id.tv_kongbai);
            hodler.tv_gx = (TextView) convertView.findViewById(R.id.tv_gx);
            hodler.tv_packageNum = (TextView) convertView.findViewById(R.id.tv_packageNum);
            hodler.tv_zhongzhuanzhuangtai = (TextView) convertView.findViewById(R.id.tv_zhongzhuanzhuangtai);
            hodler.tv_yundanhao = (TextView) convertView.findViewById(R.id.tv_yundanhao);
            hodler.tv_zidantiaoma = (TextView) convertView.findViewById(R.id.tv_zidantiaoma);
            hodler.tv_jianshu = (TextView) convertView.findViewById(R.id.tv_jianshu);
            hodler.tv_shizhong = (TextView) convertView.findViewById(R.id.tv_shizhong);
            hodler.tv_changkuaigao = (TextView) convertView.findViewById(R.id.tv_changkuaigao);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        if (null != listData && listData.size() > 0) {
            Log.i("设置值是否进来了","进来了");
            hodler.tv_gx.setText(listData.get(position).getChecked());
            hodler.tv_packageNum.setText(listData.get(position).getPackageNumber());
            if (TextUtils.isEmpty(listData.get(position).getTransitState())) {
                hodler.tv_zhongzhuanzhuangtai.setVisibility(View.GONE);
            } else {
                hodler.tv_zhongzhuanzhuangtai.setVisibility(View.VISIBLE);
                hodler.tv_zhongzhuanzhuangtai.setText(listData.get(position).getTransitState());
            }
            hodler.tv_yundanhao.setText(listData.get(position).getWaybillNumber());
            hodler.tv_zidantiaoma.setText(listData.get(position).getBulletsBarcode());
            hodler.tv_jianshu.setText(listData.get(position).getNumber());
            hodler.tv_shizhong.setText(listData.get(position).getWeight());
            hodler.tv_changkuaigao.setText(listData.get(position).getVolume());

        }
        return convertView;
    }


    class ViewHodler {
        TextView tv_kongbai, tv_gx, tv_packageNum, tv_zhongzhuanzhuangtai, tv_yundanhao, tv_zidantiaoma, tv_jianshu, tv_shizhong, tv_changkuaigao;
    }
}
