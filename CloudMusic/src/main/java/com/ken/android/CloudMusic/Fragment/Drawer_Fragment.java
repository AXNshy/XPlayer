package com.ken.android.CloudMusic.Fragment;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ken.android.CloudMusic.Activity.LoginActivity;
import com.ken.android.CloudMusic.Activity.User_InfoShowActivity;
import com.ken.android.CloudMusic.MySharedPre;
import com.ken.android.CloudMusic.R;
import com.ken.android.CloudMusic.User;

import java.util.ArrayList;

/**
 * Created by axnshy on 16/4/18.
 */
public class Drawer_Fragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mDrawerListView;
    private ImageView mDrawerImg;
    private TextView view_username;
    private ArrayList<String> mDrawerList;
    private ListAdapter mDrawerListAdapter;
    private RelativeLayout mDrawerTop;

    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.home_drawer,container,false);
        initDrawer();
        setView();
        return mView;
    }

    private void initDrawer() {
        if(User.mUser!=null){
            view_username.setText(User.mUser.getUsername());
        }
        else {
            view_username.setText("当前无用户登录");
            mDrawerImg.setImageResource(R.drawable.music);
        }
    }

    public void setView(){
//        mDrawerTop.setBackgroundColor(Color.parseColor("#4374f5"));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:{
               Intent intent=new Intent();
                intent.putExtra("user_info",User.mUser);
                intent.setComponent(new ComponentName(view.getContext(), User_InfoShowActivity.class));
                startActivity(intent);
                break;
            }
            case 1:{
                MySharedPre.unRegisterUser(view.getContext());
                User.mUser.setUsername("");
                User.mUser.setPassword("");
                Intent intent =new Intent(view.getContext(),LoginActivity.class);
                startActivity(intent);
                break;
            }
            case 2:{
               System.exit(0);
            }
        }
    }
}
