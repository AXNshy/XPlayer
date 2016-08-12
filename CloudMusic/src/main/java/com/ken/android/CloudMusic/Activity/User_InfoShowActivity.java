package com.ken.android.CloudMusic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ken.android.CloudMusic.R;
import com.ken.android.CloudMusic.User;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by axnshy on 16/8/5.
 */
@ContentView(R.layout.personinfo)
public class User_InfoShowActivity extends BaseActivity{

    @ViewInject(R.id.iv_setAvatar)
    private CircleImageView avatar;
    @ViewInject(R.id.iv_userInfo_return)
    private ImageView returnImg;
    @ViewInject(R.id.tv_personInfo_edit)
    private TextView editTx;
    @ViewInject(R.id.tv_personInfo_nickName)
    private TextView nickNameTx;
    @ViewInject(R.id.tv_personInfo_gender)
    private TextView genderTx;
    @ViewInject(R.id.tv_personInfo_age)
    private TextView ageTx;
    @ViewInject(R.id.tv_personInfo_birthday)
    private TextView birthdayTx;
    @ViewInject(R.id.tv_personInfo_address)
    private TextView addressTx;
    @ViewInject(R.id.tv_personInfo_email)
    private TextView emailTx;
    @ViewInject(R.id.tv_personInfo_QQ)
    private TextView qqTx;
    @ViewInject(R.id.tv_personInfo_telephone)
    private TextView telephoneTx;

    @Event({R.id.iv_setAvatar, R.id.tv_personInfo_edit,R.id.iv_userInfo_return})
    protected void initOnClickListener(View v) {
        switch (v.getId()) {
            case R.id.iv_userInfo_return:
                onBackPressed();
                break;
            case R.id.tv_personInfo_edit:
                Intent intent = new Intent();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
    }

    private void updateUI() {
        if (User.mUser != null) {
            nickNameTx.setText(User.mUser.getNickName());
            genderTx.setText(User.mUser.getGender()+"");
            ageTx.setText("22");
            birthdayTx.setText(User.mUser.getBirthday()+"");
            addressTx.setText(User.mUser.getAddress());
            emailTx.setText(User.mUser.getEmail());
            qqTx.setText(User.mUser.getQq());
            telephoneTx.setText(User.mUser.getTelephone());
        }
    }



}
