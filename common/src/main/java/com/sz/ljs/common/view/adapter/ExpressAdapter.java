package com.sz.ljs.common.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sz.ljs.common.R;
import com.sz.ljs.common.model.ExpressModel;

import java.util.ArrayList;
import java.util.List;

public class ExpressAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExpressModel> listData = new ArrayList<ExpressModel>();
    private LayoutInflater inflater;
    ChildCheckedImgOnclick childCheckedImgOnclick;

    public interface ChildCheckedImgOnclick {
        void onClick( int childPosition);
    }
    public void setChildCheckedImgOnclick(ChildCheckedImgOnclick listener) {
        childCheckedImgOnclick = listener;
    }
    public ExpressAdapter(Context context, List<ExpressModel> list) {
        mContext = context;
        listData = list;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ExpressModel> list) {
        if (null != listData) {
            listData.clear();
        }
        listData = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            hodler.tv_packageNum.setVisibility(View.GONE);
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
        hodler.ll_isOpen.setVisibility(View.GONE);
        hodler.iv_isOpen.setVisibility(View.GONE);
        hodler.ll_ischecked.setVisibility(View.VISIBLE);
        if (null != listData && listData.size() > 0) {
            if ("false" == listData.get(position).getIsSelect()) {
                //TODO 表示没有被勾选
                hodler.iv_ischecked.setImageResource(R.mipmap.fb_b);
            } else  if ("true" == listData.get(position).getIsSelect()){
                hodler.iv_ischecked.setImageResource(R.mipmap.fb_g);
            }
//            if (TextUtils.isEmpty(listData.get(position).getPackageNumber())) {
//                //TODO 没有包编号，证明这些数据全是没有打包的子单,这个时候就不需要显示包编号一栏
//                hodler.tv_packageNum.setVisibility(View.GONE);
//            } else {
//                //TODO 有包编号，这里也不显示
//                hodler.tv_packageNum.setVisibility(View.VISIBLE);
//                hodler.tv_packageNum.setText("");
//            }

            if (TextUtils.isEmpty(listData.get(position).getHolding())) {
                //TODO 没有中转状态这一项，则直接隐藏掉
                hodler.tv_zhongzhuanzhuangtai.setVisibility(View.GONE);
            } else {
                hodler.tv_zhongzhuanzhuangtai.setVisibility(View.VISIBLE);
                hodler.tv_zhongzhuanzhuangtai.setText(listData.get(position).getHolding());
            }
            hodler.tv_yundanhao.setText("" + listData.get(position).getShipper_hawbcode());
            hodler.tv_zidantiaoma.setText("" + listData.get(position).getChild_number());
            hodler.tv_jianshu.setText("" + listData.get(position).getShipper_pieces());
            hodler.tv_shizhong.setText(listData.get(position).getOutvolume_grossweight() + "KG");
            hodler.tv_changkuaigao.setText(listData.get(position).getOutvolume_length() + "*"
                    + listData.get(position).getOutvolume_width() + "*"
                    + listData.get(position).getOutvolume_length());
           

        }
        hodler.ll_ischecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=childCheckedImgOnclick){
                    childCheckedImgOnclick.onClick(position);
                }
            }
        });
        return convertView;
    }

    class ViewHodler {
        LinearLayout ll_isOpen, ll_ischecked;
        ImageView iv_isOpen, iv_ischecked;
        TextView tv_kongbai, tv_gx, tv_packageNum, tv_zhongzhuanzhuangtai, tv_yundanhao, tv_zidantiaoma, tv_jianshu, tv_shizhong, tv_changkuaigao;
    }
}
