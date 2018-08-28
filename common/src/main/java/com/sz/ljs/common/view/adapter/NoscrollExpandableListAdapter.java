package com.sz.ljs.common.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.common.R;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.view.NoscrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class NoscrollExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ExpressPackageModel> listData = new ArrayList<ExpressPackageModel>();
    private LayoutInflater inflater;
    private int type=0; //0代表不需要显示中转状态，1表示根据判断来显示

    public interface GroupCheckedImgOnclick {
        void onClick(int groupPosition);
    }

    public interface ChildCheckedImgOnclick {
        void onClick(int groupPosition, int childPosition);
    }

    GroupCheckedImgOnclick groupCheckedImgOnclick;
    ChildCheckedImgOnclick childCheckedImgOnclick;

    public void setGroupCheckedImgOnclick(GroupCheckedImgOnclick listener) {
        groupCheckedImgOnclick = listener;
    }

    public void setChildCheckedImgOnclick(ChildCheckedImgOnclick listener) {
        childCheckedImgOnclick = listener;
    }

    public NoscrollExpandableListAdapter(Context context, List<ExpressPackageModel> list,int type) {
        mContext = context;
        listData = list;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ExpressPackageModel> list) {
        if (null != this.listData) {
            this.listData.clear();
        }
        this.listData = list;
        notifyDataSetChanged();
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return listData.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        return null == listData.get(groupPosition).getCn_list() ? 0 : listData.get(groupPosition).getCn_list().size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(groupPosition);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null == listData.get(groupPosition).getCn_list() ? null : listData.get(groupPosition).getCn_list().get(childPosition);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (null == convertView) {
            hodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.item_four_sides_slid_list, null);
            hodler.ll_zge = (LinearLayout) convertView.findViewById(R.id.ll_zge);
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
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        hodler.ll_isOpen.setVisibility(View.VISIBLE);
        hodler.ll_ischecked.setVisibility(View.VISIBLE);
        if (null != listData && listData.size() > 0) {
            if ("false" == listData.get(groupPosition).getIsSelect()) {
                //TODO 表示没有被勾选
                hodler.ll_zge.setBackgroundResource(R.color.secondary_color_transparent);
                hodler.iv_ischecked.setImageResource(R.mipmap.fb_b);
            } else  if ("true" == listData.get(groupPosition).getIsSelect()){
                hodler.ll_zge.setBackgroundResource(R.color.secondary_color_ff0000);
                hodler.iv_ischecked.setImageResource(R.mipmap.fb_g);
            }
            hodler.tv_packageNum.setText("" + listData.get(groupPosition).getBag_lable_code());
            hodler.tv_zhongzhuanzhuangtai.setVisibility(View.GONE);
            hodler.tv_yundanhao.setText("");
            hodler.tv_zidantiaoma.setText("");
            hodler.tv_jianshu.setText("" + listData.get(groupPosition).getBag_pieces());
            hodler.tv_shizhong.setText(listData.get(groupPosition).getBag_weight() + "KG");
            hodler.tv_changkuaigao.setText(listData.get(groupPosition).getLength() + "*"
                    + listData.get(groupPosition).getWidth() + "*"
                    + listData.get(groupPosition).getHeight());
        }
        hodler.ll_ischecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=groupCheckedImgOnclick){
                    groupCheckedImgOnclick.onClick(groupPosition);
                }
            }
        });
        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHodler hodler = null;
        if (null == convertView) {
            hodler = new ChildViewHodler();
            convertView = inflater.inflate(R.layout.item_four_sides_slid_list, null);
            hodler.ll_zge = (LinearLayout) convertView.findViewById(R.id.ll_zge);
            hodler.ll_isOpen = (LinearLayout) convertView.findViewById(R.id.ll_isOpen);
            hodler.ll_ischecked = (LinearLayout) convertView.findViewById(R.id.ll_ischecked);
            hodler.iv_isOpen = (ImageView) convertView.findViewById(R.id.iv_isOpen);
            hodler.iv_ischecked = (ImageView) convertView.findViewById(R.id.iv_ischecked);
            hodler.tv_kongbai = (TextView) convertView.findViewById(R.id.tv_kongbai);
            hodler.tv_kongbai.setVisibility(View.GONE);
            hodler.tv_gx = (TextView) convertView.findViewById(R.id.tv_gx);
            hodler.tv_gx.setVisibility(View.GONE);
            hodler.tv_packageNum = (TextView) convertView.findViewById(R.id.tv_packageNum);
            hodler.tv_packageNum.setVisibility(View.VISIBLE);
            hodler.tv_zhongzhuanzhuangtai = (TextView) convertView.findViewById(R.id.tv_zhongzhuanzhuangtai);
            hodler.tv_yundanhao = (TextView) convertView.findViewById(R.id.tv_yundanhao);
            hodler.tv_zidantiaoma = (TextView) convertView.findViewById(R.id.tv_zidantiaoma);
            hodler.tv_jianshu = (TextView) convertView.findViewById(R.id.tv_jianshu);
            hodler.tv_shizhong = (TextView) convertView.findViewById(R.id.tv_shizhong);
            hodler.tv_changkuaigao = (TextView) convertView.findViewById(R.id.tv_changkuaigao);
            convertView.setTag(hodler);
        } else {
            hodler = (ChildViewHodler) convertView.getTag();
        }
        hodler.ll_isOpen.setVisibility(View.VISIBLE);
        hodler.iv_isOpen.setVisibility(View.GONE);
        hodler.ll_ischecked.setVisibility(View.VISIBLE);
        if (null != listData.get(groupPosition).getCn_list() && listData.get(groupPosition).getCn_list().size() > 0) {
            if ("false" == listData.get(groupPosition).getCn_list().get(childPosition).getIsSelect()) {
                //TODO 表示没有被勾选
                hodler.ll_zge.setBackgroundResource(R.color.secondary_color_transparent);
                hodler.iv_ischecked.setImageResource(R.mipmap.fb_b);
            } else if ("true" == listData.get(groupPosition).getCn_list().get(childPosition).getIsSelect()){
                hodler.ll_zge.setBackgroundResource(R.color.secondary_color_ff0000);
                hodler.iv_ischecked.setImageResource(R.mipmap.fb_g);
            }
            hodler.tv_packageNum.setText("");

            if(1==type){
                if (TextUtils.isEmpty(listData.get(groupPosition).getCn_list().get(childPosition).getOrder_status())) {
                    //TODO 没有中转状态这一项，则直接隐藏掉
                    hodler.tv_zhongzhuanzhuangtai.setVisibility(View.GONE);
                } else {
                    hodler.tv_zhongzhuanzhuangtai.setVisibility(View.VISIBLE);
                    hodler.tv_zhongzhuanzhuangtai.setText(listData.get(groupPosition).getCn_list().get(childPosition).getOrder_status());
                }
            }else {
                //TODO 没有中转状态这一项，则直接隐藏掉
                hodler.tv_zhongzhuanzhuangtai.setVisibility(View.GONE);
            }
            hodler.tv_yundanhao.setText("" + listData.get(groupPosition).getCn_list().get(childPosition).getShipper_hawbcode());
            hodler.tv_zidantiaoma.setText("" + listData.get(groupPosition).getCn_list().get(childPosition).getChild_number());
            hodler.tv_jianshu.setText("" + listData.get(groupPosition).getCn_list().get(childPosition).getShipper_pieces());
            hodler.tv_shizhong.setText(listData.get(groupPosition).getCn_list().get(childPosition).getOutvolume_grossweight() + "KG");
            hodler.tv_changkuaigao.setText(listData.get(groupPosition).getCn_list().get(childPosition).getOutvolume_length() + "*"
                    + listData.get(groupPosition).getCn_list().get(childPosition).getOutvolume_width() + "*"
                    + listData.get(groupPosition).getCn_list().get(childPosition).getOutvolume_length());
        }
        hodler.ll_ischecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=groupCheckedImgOnclick){
                    childCheckedImgOnclick.onClick(groupPosition,childPosition);
                }
            }
        });
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHodler {
        LinearLayout ll_isOpen, ll_ischecked,ll_zge;
        ImageView iv_isOpen, iv_ischecked;
        TextView tv_kongbai, tv_gx, tv_packageNum, tv_zhongzhuanzhuangtai, tv_yundanhao, tv_zidantiaoma, tv_jianshu, tv_shizhong, tv_changkuaigao;
    }

    class ChildViewHodler {
        LinearLayout ll_isOpen, ll_ischecked,ll_zge;
        ImageView iv_isOpen, iv_ischecked;
        TextView tv_kongbai, tv_gx, tv_packageNum, tv_zhongzhuanzhuangtai, tv_yundanhao, tv_zidantiaoma, tv_jianshu, tv_shizhong, tv_changkuaigao;
    }
}
