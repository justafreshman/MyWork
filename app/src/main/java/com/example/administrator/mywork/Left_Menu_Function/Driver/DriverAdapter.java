package com.example.administrator.mywork.Left_Menu_Function.Driver;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.databinding.ViewStubProxy;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.bumptech.glide.Glide;
import com.example.administrator.mywork.R;
import com.example.administrator.mywork.bean.driver;
import com.example.administrator.mywork.databinding.QueryDriverItemBinding;
import com.example.administrator.mywork.databinding.multiple;
import com.example.administrator.mywork.databinding.single;
import com.example.administrator.mywork.databinding.true_false;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 * 作者：wu
 */
public class DriverAdapter extends RecyclerView.Adapter<DriverHolder> {
    private List<driver> questionlist;
    private Context mContext;
    private String[] mMess;
    private ViewStubProxy mViewStubProxy;
    public final ObservableInt mSelect = new ObservableInt();
    int zero = 1;

    public DriverAdapter(List<driver> questionlist, Context context) {
        this.questionlist = questionlist;
        this.mContext = context;

    }

    @Override
    public DriverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_driver_item, parent, false);
        QueryDriverItemBinding qdi = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.query_driver_item,parent,false);
        qdi.setSelect(mSelect);
        return new DriverHolder(qdi.getRoot());
    }

    @Override
    public void onBindViewHolder(final DriverHolder holder, final int position) {
        holder.getBind().driverQuestion.setText(questionlist.get(position).getId() + " " + questionlist.get(position).getQuestion());
        if (!(questionlist.get(position).getUrl().equals(""))) {
            holder.getBind().picll.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(questionlist.get(position).getUrl())
                    .thumbnail(0.1f)
                    .error(R.drawable.ic_search_black_18dp)
                    .into(holder.getBind().driverPic);
        }else {
            zero+=1;
            holder.getBind().picll.setVisibility(View.GONE);
        }

        mMess = new String[4];
//        0 选择题 1 判断题 2 多选题
        mSelect.set(0);
        if (questionlist.get(position).getItem3().equals("") && questionlist.get(position).getItem4().equals("")) {
            mMess[0] = questionlist.get(position).getItem1();
            mMess[1] = questionlist.get(position).getItem2();
            mSelect.set(1);
        } else {
            mMess[0] = "A " + questionlist.get(position).getItem1();
            mMess[1] = "B " + questionlist.get(position).getItem2();
            mMess[2] = "C " + questionlist.get(position).getItem3();
            mMess[3] = "D " + questionlist.get(position).getItem4();
        }



        String answer = "";
        if (mSelect.get()==0) {
            String answercode = questionlist.get(position).getAnswer();
            if (answercode.equals("1")) {
                answer ="A ";
            } else if (answercode.equals("2")) {
                answer ="B ";
            } else if (answercode.equals("3")) {
                answer ="C ";
            } else if (answercode.equals("4")){
                answer ="D ";
            }else if (answercode.equals("7")){
                mSelect.set(2);
                answer ="AB ";
            }else if (answercode.equals("8")){
                mSelect.set(2);
                answer ="AC ";
            }else if (answercode.equals("9")){
                mSelect.set(2);
                answer ="AD ";
            }else if (answercode.equals("10")){
                mSelect.set(2);
                answer ="BC ";
            }else if (answercode.equals("11")){
                mSelect.set(2);
                answer ="BD ";
            }else if (answercode.equals("12")){
                mSelect.set(2);
                answer ="CD ";
            }else if (answercode.equals("13")){
                mSelect.set(2);
                answer ="ABC ";
            }else if (answercode.equals("14")){
                mSelect.set(2);
                answer ="ABD ";
            }else if (answercode.equals("15")){
                mSelect.set(2);
                answer ="ACD ";
            }else if (answercode.equals("16")){
                mSelect.set(2);
                answer ="BCD ";
            }else if (answercode.equals("17")){
                mSelect.set(2);
                answer ="ABCD ";
            }

        } else {
            if (questionlist.get(position).getAnswer().equals("1")) {
                answer = "正确";
            } else if (questionlist.get(position).getAnswer().equals("2")) {
                answer = "错误";
            }
        }

        holder.getBind().driverAnswer.setText(answer+questionlist.get(position).getExplains());
//          0 选择题 1 判断题 2 多选题
//        if(holder.getBind().whichChoice!=null){
//            mViewStubProxy = (ViewStubProxy)(Object)holder.getBind().whichChoice;
//        }
//        if(mSelect.get()==0){
////            mViewStubProxy.getViewStub().setLayoutResource(R.layout.driver_single_choice);
//        }else if (mSelect.get()==1){
////            holder.getBind().whichChoice.setLayoutResource(R.layout.driver_true_false_choice);
////            mViewStubProxy.getViewStub().setLayoutResource(R.layout.driver_true_false_choice);
//        }else {
////            holder.getBind().whichChoice.setLayoutResource(R.layout.driver_multiple_choice);
////            mViewStubProxy.getViewStub().setLayoutResource(R.layout.driver_multiple_choice);
//        }


//         TODO 放弃 暂时不会如何做了
//        ViewStub vs = holder.getBind().whichChoice.getViewStub();
//        if(vs!=null){
//            if(mSelect.get()==0){
//                Log.d("zero", "single");
//                vs.setLayoutResource(R.layout.driver_true_false_choice);
//
//            }else if(mSelect.get()==1){
//                Log.d("zero", "true_false");
//                vs.setLayoutResource(R.layout.driver_single_choice);
//            }else {
//                Log.d("zero","multiple");
//                vs.setLayoutResource(R.layout.driver_multiple_choice);
//            }
//            vs.inflate();
//        }

//        holder.getBind().whichChoice.setOnInflateListener(new ViewStub.OnInflateListener() {
//            @Override
//            public void onInflate(ViewStub stub, View inflated) {
//                if (mSelect.get()==0) {
//                    single binding = DataBindingUtil.bind(inflated);
//                    binding.driverAnswerone.setText(mMess[0]);
//                    binding.driverAnswertwo.setText(mMess[1]);
//                    binding.driverAnswerthree.setText(mMess[2]);
//                    binding.driverAnswerfour.setText(mMess[3]);
//                } else if (mSelect.get()==1) {
//                    multiple binding = DataBindingUtil.bind(inflated);
//                    binding.driverAnswerone.setText(mMess[0]);
//                    binding.driverAnswertwo.setText(mMess[1]);
//                    binding.driverAnswerthree.setText(mMess[2]);
//                    binding.driverAnswerfour.setText(mMess[3]);
//                }else {
//                    true_false binding = DataBindingUtil.bind(inflated);
//                    binding.driverAnswerone.setText(mMess[0]);
//                    binding.driverAnswertwo.setText(mMess[1]);
//                    binding.driverAnswerthree.setText(mMess[2]);
//                    binding.driverAnswerfour.setText(mMess[3]);
//                }
//
//            }
//        });


//        holder.getBind().whichChoice.inflate();
//        if (!mViewStubProxy.isInflated()) {
//            mViewStubProxy.getViewStub().inflate();
//           }


        holder.getBind().driverAnswerone.setText(mMess[0]);
        holder.getBind().driverAnswertwo.setText(mMess[1]);
        holder.getBind().driverAnswerthree.setText(mMess[2]);
        holder.getBind().driverAnswerfour.setText(mMess[3]);
        holder.getBind().sureAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getBind().driverAnswer.getVisibility() == View.VISIBLE) {
                    holder.getBind().driverAnswer.setVisibility(View.GONE);
                } else {
                    holder.getBind().driverAnswer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

//    public void setData(Binding bind){
//        bind.driverAnswerone.setText(mess[0]);
//        holder.getBind().driverAnswertwo.setText(mess[1]);
//        holder.getBind().driverAnswerthree.setText(mess[2]);
//        holder.getBind().driverAnswerfour.setText(mess[3]);
//        holder.getBind().sureAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.getBind().driverAnswer.getVisibility() == View.VISIBLE) {
//                    holder.getBind().driverAnswer.setVisibility(View.GONE);
//                } else {
//                    holder.getBind().driverAnswer.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return questionlist.size();
    }
}
