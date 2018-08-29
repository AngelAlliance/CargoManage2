package com.ljs.examinegoods.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ljs.examinegoods.R;
import com.ljs.examinegoods.model.DetectionByModel;

import java.util.ArrayList;
import java.util.List;

public class InspectionItemAdapter extends BaseAdapter{
    private List<DetectionByModel.DataBean> listData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    public interface SelsectRadioButtonCallBack{
        void callBack(int position,String select);
    }

    private SelsectRadioButtonCallBack callBack;

    public void setCallBack(SelsectRadioButtonCallBack listener){
        callBack=listener;
    }
    public InspectionItemAdapter(Context context, List<DetectionByModel.DataBean> list){
        mContext=context;
        listData=list;
        inflater=LayoutInflater.from(mContext);
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHodler hodler=null;
        if(null==convertView){
            hodler=new ViewHodler();
            convertView=inflater.inflate(R.layout.item_inspection,null);
            hodler.ll_content=(LinearLayout) convertView.findViewById(R.id.ll_contents);
            hodler.tv_goods_name=(TextView) convertView.findViewById(R.id.tv_goods_name);
            hodler.rg_goods=(RadioGroup) convertView.findViewById(R.id.rg_goods);
            hodler.yes_goods=(RadioButton) convertView.findViewById(R.id.yes_goods);
            hodler.no_goods=(RadioButton) convertView.findViewById(R.id.no_goods);
            convertView.setTag(hodler);
        }else {
            hodler=(ViewHodler) convertView.getTag();
        }
        if(null!=listData&&listData.size()>0){
            hodler.tv_goods_name.setText(listData.get(position).getDetection_cn_name());
            if(true==listData.get(position).isChaYi()){
                hodler.ll_content.setBackgroundResource(R.drawable.login_edittext2_bg);
            }else {
                hodler.ll_content.setBackgroundResource(R.drawable.login_edittext_bg);
            }
            if("N".equals(listData.get(position).getSelect_value())){
                hodler.no_goods.setChecked(true);
                hodler.yes_goods.setChecked(false);
            }else if("Y".equals(listData.get(position).getSelect_value())){
                hodler.yes_goods.setChecked(true);
                hodler.no_goods.setChecked(false);
            }
            hodler.yes_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.i("传出去的下标","position="+position);
                    if(null!=callBack){
                        callBack.callBack(position,"Y");
                    }
                }
            });
            hodler.no_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.i("传出去的下标","position="+position);
                    if(null!=callBack){
                        callBack.callBack(position,"N");
                    }
                }
            });
        }

//        hodler.rg_goods.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                if(checkedId==R.id.yes_goods){
//
//                }else if(checkedId==R.id.no_goods){
//                    if(null!=callBack){
//                        callBack.callBack(position,"N");
//                    }
//                }
//            }
//        });
        return convertView;
    }

   class ViewHodler{
        LinearLayout ll_content;
        TextView tv_goods_name;
        RadioGroup rg_goods;
        RadioButton yes_goods,no_goods;
   }
}
