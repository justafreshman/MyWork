package com.example.administrator.mywork.Left_Menu_Function;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.administrator.mywork.Base.BaseActivity;
import com.example.administrator.mywork.Data.Data;
import com.example.administrator.mywork.HttpMethod.HttpMethods;
import com.example.administrator.mywork.Interface.SubscriberOnNextListener;
import com.example.administrator.mywork.Left_Menu_Function.Postal.PostalAdapter;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.Until.ToastUntil;
import com.example.administrator.mywork.bean.postalBycodeEntity;
import com.example.administrator.mywork.bean.postalofCity;
import com.example.administrator.mywork.databinding.QueryPostalBinding;
import com.example.administrator.mywork.databinding.RecycleButtomBinding;
import com.example.administrator.mywork.deal_Progress.ProgressSubscriber;
import com.example.administrator.mywork.view.SearchView;
import com.example.administrator.mywork.view.add_head_foot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/7/2.
 * 作者：wu
 */



//TODO  错误代码还没有解决   还有重构的问题   代码很乱 心里非常糟糕
public class Query_Postal extends BaseActivity {

    private QueryPostalBinding mQpb;
    private PostalAdapter mAdapter;
    private ProgressSubscriber<postalBycodeEntity> mSubscriber;
    private ProgressSubscriber<List<postalofCity>> sSubscriber;
    private ProgressSubscriber<postalBycodeEntity> dSubscriber;
    private int whichselect = 0;
    private RecycleButtomBinding mRbb;
    private OptionsPickerView mPvOptions1;
    private String pid;
    private String cid;
    private String did;
    private int pagenum = 1;
    private LinearLayoutManager mLayoutManager;
//    是否更新
    private Boolean isUpdata = false;
    private List<postalBycodeEntity.ListEntity> postallist = new ArrayList<>();
    private String mSrerchtext;
    private add_head_foot mAhf;
    private String mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQpb = DataBindingUtil.setContentView(this, R.layout.query_postal);
        mLayoutManager = new LinearLayoutManager(Query_Postal.this);
        mQpb.postalMess.setLayoutManager(mLayoutManager);
        mRbb = DataBindingUtil.inflate(getLayoutInflater(), R.layout.recycle_buttom, mQpb.postalMess, false);

        sSubscriber = new ProgressSubscriber<>(new SubscriberOnNextListener<List<postalofCity>>() {
            @Override
            public void onNext(List<postalofCity> postalofCities) {
                setCityData(postalofCities);
            }
        }, this);

        Data.HTTP_METHODS.getPostalcity(sSubscriber);

//        初始化下拉数据
        initspacerSelectData();
//        初始化下拉更多
        initloadmore(mQpb.postalMess);

        mQpb.searchPostal.searchThing.setHintText("请输入邮政编码");
        mQpb.searchPostal.searchThing.AddEditTextListen(new SearchView.OnEditTextListen() {
            @Override
            public void searchThing() {
                mSrerchtext = mQpb.searchPostal.searchThing.getSearchText();
                if(!(mSrerchtext.equals(""))){
                    if(mSubscriber!=null){
                        if(mSubscriber.isUnsubscribed()){
                            mSubscriber.unsubscribe();
                        }
                        mSubscriber = NewSubscriber();
                    }else {
                        mSubscriber = NewSubscriber();
                    }
                    Data.HTTP_METHODS.getPostalByCode(mSubscriber,mSrerchtext,pagenum+"");
                }else {
                    ToastUntil.showmess(Query_Postal.this,"请输入编码");
                }
            }
        });
    }

    public ProgressSubscriber<postalBycodeEntity> NewSubscriber(){
       ProgressSubscriber<postalBycodeEntity> mSubscriber = new ProgressSubscriber<>(new SubscriberOnNextListener<postalBycodeEntity>() {
            @Override
            public void onNext(postalBycodeEntity postalBycodeEntity) {
                if(postalBycodeEntity.getList().size()<10) isUpdata=true;

                setData(postalBycodeEntity);
            }
        }, this);
        return mSubscriber;
    }

//    显示选择地址
    public void show(View v){
        mPvOptions1.show();
    }

//    进行查询
    public void query(View v){
        if(dSubscriber!=null){
            if(dSubscriber.isUnsubscribed()){
                dSubscriber.unsubscribe();
            }
            dSubscriber = NewSubscriber();
        }else {
            dSubscriber = NewSubscriber();
        }
        if(!(pid==null&&cid==null&&did==null)){
            HttpMethods.getInstance().getPostalByCity(dSubscriber, pid, cid, did);
        }else {
            ToastUntil.showmess(Query_Postal.this,"请先选择地址");
        }
    }

    private void setCityData(List<postalofCity> postalofCities) {
        final Map<String, String> province = new HashMap<>();
        final Map<String, String> city = new HashMap<>();
        final Map<String, String> county = new HashMap<>();
        final ArrayList<String> prov = new ArrayList<>();
        final ArrayList<ArrayList<String>> ci = new ArrayList<>();
        final ArrayList<ArrayList<ArrayList<String>>> cou = new ArrayList<>();

        for(int x = 0;x < postalofCities.size();x++){
            postalofCity pro = postalofCities.get(x);
            prov.add(pro.getProvince());
            ArrayList<String> list = new ArrayList<>();
            ArrayList<ArrayList<String>> list2 = new ArrayList<>();
            province.put(pro.getProvince(), pro.getId());
            for(int y = 0; y < pro.getCity().size();y++){
                postalofCity.CityEntity ce = pro.getCity().get(y);
                list.add(ce.getCity());
                ArrayList<String> list3 = new ArrayList<>();
                list2.add(list3);
                city.put(ce.getCity(),ce.getId());
                for(int z = 0;z < ce.getDistrict().size();z++){
                    postalofCity.CityEntity.DistrictEntity de = ce.getDistrict().get(z);
                    list3.add(de.getDistrict());

                    county.put(de.getDistrict(),de.getId());
                }
            }
            ci.add(list);
            cou.add(list2);
        }

        //选项选择器
        mPvOptions1 = new OptionsPickerView(Query_Postal.this);
        //三级联动效果
        mPvOptions1.setPicker(prov, ci, cou, true);
        mPvOptions1.setTitle("选择城市");
        mPvOptions1.setCyclic(false, true, true);
        mPvOptions1.setSelectOptions(1, 1, 1);
        mPvOptions1.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String pro = prov.get(options1);
                String cit = ci.get(options1).get(option2);
                String dis = cou.get(options1).get(option2).get(options3);
                Log.d("zero", pro + cit + dis);
                pid = province.get(pro);
                cid = city.get(cit);
                did = county.get(dis);
                String mess = pro + "-" + cit + "-" + dis;
                mQpb.showCity.setText(mess);
            }
        });
    }

    private void setData(postalBycodeEntity postalBycodeEntity) {
//        setHasFixedSize()方法用来使RecyclerView保持固定的大小，该信息被用于自身的优化。
        mQpb.postalMess.setHasFixedSize(true);
        if(postallist.size()!=postalBycodeEntity.getList().size()&&pagenum==1){
            postallist.addAll(postalBycodeEntity.getList());
        }
        if(mAdapter==null){
            mAdapter = new PostalAdapter(postallist);
            mAhf = new add_head_foot(mAdapter);
            mAhf.addFooterView(mRbb.getRoot());
            mQpb.postalMess.setAdapter(mAhf);
        }else {
            Log.d("zero","wtf");
            mAhf.removeFooterView(mRbb.getRoot());
            postallist.addAll(postalBycodeEntity.getList());
            mAdapter.notifyDataSetChanged();
        }

        mQpb.postalMess.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 30, 0, 0);
            }
        });
    }


    public void initspacerSelectData() {
        String[] strings = getResources().getStringArray(R.array.choose);
//        绑定数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        mQpb.spacerSelect.setAdapter(adapter);
        mQpb.spacerSelect.setSelection(0);
        mQpb.spacerSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(whichselect == position)) {
//                    TODO 返回来的数据成功后才清除
                    pagenum = 1;
                    postallist.clear();
                    whichselect = position;
                }


                if (position == 1) {
                    mQpb.searchPostal.searchThing.setVisibility(View.GONE);
                    mQpb.selectcity.setVisibility(View.VISIBLE);
                } else {
                    mQpb.searchPostal.searchThing.setVisibility(View.VISIBLE);
                    mQpb.selectcity.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //    初始化加载更多
    private void initloadmore(RecyclerView recyclerView){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //大于0表示，正在向下滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0 表示停止或向上滚动
                    isSlidingToLast = false;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
                int totalItem = mLayoutManager.getItemCount();
                // 判断是否滚动到底部，并且是向下滚动

                if (lastItem == (totalItem - 1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isUpdata) {
                        if(whichselect==0) mSubscriber = whichloadmore(mSubscriber);

                        if(whichselect==1) dSubscriber = whichloadmore(dSubscriber);
                        pagenum += 1;
                        if (whichselect == 0) {
                            Log.d("zero",pagenum+""+mSrerchtext);
                            HttpMethods.getInstance().getPostalByCode(mSubscriber, mSrerchtext, pagenum + "");
                        } else {
                            if (!(pid == null && cid == null && did == null)) {
                                HttpMethods.getInstance().getPostalByCity(dSubscriber, pid, cid, did);
                            }
                        }
                    } else {
                        ToastUntil.showmess(Query_Postal.this, "没有更多啦");
                    }
                }
            }
        });
    }


    public ProgressSubscriber<postalBycodeEntity> whichloadmore(ProgressSubscriber<postalBycodeEntity> Subscriber){
        if(Subscriber.isUnsubscribed()){
            Subscriber.unsubscribe();
            Subscriber = NewSubscriber();
        }else {
            Subscriber = NewSubscriber();
        }
        return Subscriber;
    }
}
