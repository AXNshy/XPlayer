package com.ken.android.CloudMusic.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ken.android.CloudMusic.Activity.View.DynamicHistogremView;
import com.ken.android.CloudMusic.R;


import java.util.ArrayList;

/**
 * Created by axnshy on 16/8/18.
 */
public class CustomViewOne extends Fragment {

    private DynamicHistogremView mBarGraph;
    private ArrayList<Float> respectTarget;
    private ArrayList<String> respName;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom_view_one, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        respectTarget = new ArrayList<Float>();
        respName = new ArrayList<String>();
        respectTarget.add(35.0f);
        respectTarget.add(20.0f);
        respectTarget.add(18.0f);
        respectTarget.add(15.0f);
        respectTarget.add(10.0f);
        respectTarget.add(8.0f);
        respectTarget.add(5.0f);
        respName.add("滴滴");
        respName.add("小米");
        respName.add("京东");
        respName.add("美团");
        respName.add("魅族");
        respName.add("酷派");
        respName.add("携程");
        mBarGraph = (DynamicHistogremView) view.findViewById(R.id.bargraph);
        mBarGraph.setRespectTargetNum(respectTarget);
        mBarGraph.setRespectName(respName);
        mBarGraph.setTotalBarNum(7);
        mBarGraph.setMax(40);
        mBarGraph.setBarWidth(50);
        mBarGraph.setVerticalLineNum(4);
        mBarGraph.setUnit("亿元");
    }
}
