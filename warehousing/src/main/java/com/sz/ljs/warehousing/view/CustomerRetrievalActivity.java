package com.sz.ljs.warehousing.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.warehousing.R;
import com.sz.ljs.warehousing.model.CustomerModel;
import com.sz.ljs.warehousing.model.WareHouSingModel;
import com.sz.ljs.warehousing.presenter.WarehouPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//TODO 客户检索界面
public class CustomerRetrievalActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_seach;
    private ImageView iv_clean,iv_seach;
    private ListView lv_list;
    private List<CustomerModel.DataEntity> listData=new ArrayList<>();
    private WarehouPresenter mPresenter;
    private WaitingDialog mWaitingDialog;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_retrieval);
        initView();
        setListener();
    }
    private void initView(){
        mPresenter=new WarehouPresenter();
        mWaitingDialog=new WaitingDialog(this);
        et_seach=(EditText) findViewById(R.id.et_seach);
        iv_clean=(ImageView) findViewById(R.id.iv_clean);
        iv_seach=(ImageView) findViewById(R.id.iv_seach);
        lv_list=(ListView) findViewById(R.id.lv_list);
        adapter=new MyAdapter();
        lv_list.setAdapter(adapter);
    }

    private void setListener(){
        iv_clean.setOnClickListener(this);
        iv_seach.setOnClickListener(this);
        et_seach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    iv_clean.setVisibility(View.VISIBLE);
                }else {
                    iv_clean.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listData.size()>position){
                    Intent intent=new Intent();
                    intent.putExtra("id",listData.get(position).getCustomer_id());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.iv_clean){
            //TODO 清空
            et_seach.setText("");
        }else if(id==R.id.iv_seach){
            //TODO 搜索
            if(TextUtils.isEmpty(et_seach.getText().toString().trim())){
                Utils.showToast(getBaseActivity(),getResources().getString(R.string.str_qsrkhdm));
                return;
            }
            getCustomer();
        }
    }

    //TODO 根据客户名称查询客户资料
    private void getCustomer(){
        showWaiting(true);
        mPresenter.getCustomer(et_seach.getText().toString().trim())
                .compose(this.<CustomerModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CustomerModel>() {
                    @Override
                    public void accept(CustomerModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(CustomerRetrievalActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            if(null!=result.getData()&&result.getData().size()>0){
                                listData.clear();
                                listData.addAll(result.getData());
                                adapter.notifyDataSetChanged();
                                WareHouSingModel.getInstance().setCustomerList(listData);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    }


    public class MyAdapter extends BaseAdapter{

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
            ViewHodler hodler=null;
            if(null==convertView){
                hodler=new ViewHodler();
                convertView= LayoutInflater.from(CustomerRetrievalActivity.this).inflate(R.layout.item_customer,null);
                hodler.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
                hodler.tv_customer_code=(TextView)convertView.findViewById(R.id.tv_customer_code);
                convertView.setTag(hodler);
            }else {
                hodler=(ViewHodler)convertView.getTag();
            }
            if(null!=listData&&listData.size()>0){
                hodler.tv_name.setText(listData.get(position).getCustomer_shortname());
                hodler.tv_customer_code.setText(listData.get(position).getCustomer_code());
            }
            return convertView;
        }

        class ViewHodler{
            TextView tv_name,tv_customer_code;
        }
    }

    private void showWaiting(boolean isShow){
        if(null!=mWaitingDialog){
            mWaitingDialog.showDialog(isShow);
        }
    }
}
