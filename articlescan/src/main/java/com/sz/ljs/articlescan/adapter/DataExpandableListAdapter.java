package com.sz.ljs.articlescan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.articlescan.R;
import com.sz.ljs.articlescan.model.GsonSelectShipmentBagReceiveModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/5.
 */

public class DataExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<GsonSelectShipmentBagReceiveModel.DataBean> listData = new ArrayList<GsonSelectShipmentBagReceiveModel.DataBean>();
    private LayoutInflater inflater;

    public DataExpandableListAdapter(Context context, List<GsonSelectShipmentBagReceiveModel.DataBean> list) {
        mContext = context;
        listData = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return null == listData.get(groupPosition).getBagReceiveList() ? 0 : listData.get(groupPosition).getBagReceiveList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null == listData.get(groupPosition).getBagReceiveList() ? null : listData.get(groupPosition).getBagReceiveList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (null == convertView) {
            hodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.item_view, null);
            hodler.ll_isOpen = (LinearLayout) convertView.findViewById(R.id.ll_isOpen);
            hodler.ll_isOpen.setVisibility(View.VISIBLE);
            hodler.tv_packageNum = (TextView) convertView.findViewById(R.id.tv_packageNum);
            hodler.tv_fengongsi = (TextView) convertView.findViewById(R.id.tv_fengongsi);
            hodler.tv_fuwuqudao = (TextView) convertView.findViewById(R.id.tv_fuwuqudao);
            hodler.tv_jianshu = (TextView) convertView.findViewById(R.id.tv_jianshu);
            hodler.tv_piaoshu = (TextView) convertView.findViewById(R.id.tv_piaoshu);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        if (null != listData && listData.size() > 0) {
            if(!TextUtils.isEmpty(listData.get(groupPosition).getTra_batch_code())){
                hodler.tv_packageNum.setText(mContext.getResources().getString(R.string.str_bbh)+"："+listData.get(groupPosition).getTra_batch_code());
            }else {
                hodler.tv_packageNum.setText("");
            }
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHodler hodler=null;
        if(null==convertView){
            hodler=new ChildViewHodler();
            convertView=inflater.inflate(R.layout.item_view,null);
            hodler.ll_isOpen = (LinearLayout) convertView.findViewById(R.id.ll_isOpen);
            hodler.ll_isOpen.setVisibility(View.INVISIBLE);
            hodler.tv_packageNum = (TextView) convertView.findViewById(R.id.tv_packageNum);
            hodler.tv_fengongsi = (TextView) convertView.findViewById(R.id.tv_fengongsi);
            hodler.tv_fuwuqudao = (TextView) convertView.findViewById(R.id.tv_fuwuqudao);
            hodler.tv_jianshu = (TextView) convertView.findViewById(R.id.tv_jianshu);
            hodler.tv_piaoshu = (TextView) convertView.findViewById(R.id.tv_piaoshu);
            convertView.setTag(hodler);
        }else {
            hodler=(ChildViewHodler) convertView.getTag();
        }
        if(null!=listData&&null!=listData.get(groupPosition)
                &&null!=listData.get(groupPosition).getBagReceiveList()&&listData.get(groupPosition).getBagReceiveList().size()>0){
            hodler.tv_packageNum.setText(listData.get(groupPosition).getBagReceiveList().get(childPosition).getHawb_code());
            hodler.tv_fengongsi.setText(listData.get(groupPosition).getBagReceiveList().get(childPosition).getFrom_og_name());
            hodler.tv_fuwuqudao.setText(listData.get(groupPosition).getBagReceiveList().get(childPosition).getServer_channel_cnname());
            hodler.tv_jianshu.setText(listData.get(groupPosition).getBagReceiveList().get(childPosition).getBag_countpieces());
            hodler.tv_piaoshu.setText(listData.get(groupPosition).getBagReceiveList().get(childPosition).getBag_totalshipperpieces());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ViewHodler {
        LinearLayout ll_isOpen;
        TextView tv_packageNum, tv_fengongsi, tv_fuwuqudao, tv_jianshu, tv_piaoshu;
    }

    class ChildViewHodler {
        LinearLayout ll_isOpen;
        TextView tv_packageNum, tv_fengongsi, tv_fuwuqudao, tv_jianshu, tv_piaoshu;
    }
}
