package com.ken.android.CloudMusic.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ken.android.CloudMusic.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by axnshy on 16/8/18.
 */
@ContentView(R.layout.fragment_custom_view_three)
public class CustomViewThree extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        x.view().inject(this,inflater,container);
        View view = inflater.inflate(R.layout.fragment_custom_view_three,container,false);

        return view;
    }
}
