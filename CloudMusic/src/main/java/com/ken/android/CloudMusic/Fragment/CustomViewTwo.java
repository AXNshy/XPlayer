package com.ken.android.CloudMusic.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ken.android.CloudMusic.Activity.View.CircleProgress;
import com.ken.android.CloudMusic.R;


/**
 * Created by axnshy on 16/8/18.
 */
public class CustomViewTwo extends Fragment {

    private View view;

    private CircleProgress circleProgress;
    private Button start;

    float cur=0f;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom_view_two, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        start = (Button) view.findViewById(R.id.start);
        circleProgress = (CircleProgress) view.findViewById(R.id.progressbar);
//        circleProgress.setPosition(50);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur=cur+20;
                circleProgress.setPosition(cur);

            }
        });
    }
}
