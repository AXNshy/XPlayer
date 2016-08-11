package com.ken.android.CloudMusic;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.Date;

/**
 * Created by axnshy on 16/5/25.
 */
public class User implements Parcelable{


    public static User mUser;
    private Integer id;

    private String username;

    private String password;

    private String nickName;


    private String telephone;

    private String email;

    private Integer gender;

    private String avatarUri;

    private String registStart;

    private String qq;

    private Date birthday;

    private String address;

    public User(){
    }
    public User(String username,String password){
        this.username=username;
        this.password=password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public static void setmUser(User mUser) {
        User.mUser = mUser;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAvatarUri() {
        return avatarUri;
    }
    public String getEmail() {
        return email;
    }

    public String getQq() {
        return qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public static User getmUser() {
        return mUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            User user = new User();
            Bundle bundle = in.readBundle();
            user.id=bundle.getInt("id");
            user.username=bundle.getString("username");
            user.password=bundle.getString("password");
            user.address=bundle.getString("address");
            user.gender=bundle.getInt("gender");
            user.nickName=bundle.getString("nickName");
            user.telephone=bundle.getString("telephone");
            user.email=bundle.getString("email");
            user.registStart=bundle.getString("registStart");
            user.password=bundle.getString("password");
            return user;
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static boolean isUserExit(Context context){
        User user=MySharedPre.getCurrentUser(context);
        if(user!=null){
            return true;
        }
    return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        bundle.putString("password",password);
        dest.writeBundle(bundle);
    }

    /*public static boolean login(Context context,String username, String password) {
         url = Config.URL + "userName=" + username + "&password=" + password;
        if(HttpUtils.isServerConnection(context)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result=HttpUtils.doGet(url);
                    System.out.println("result:      "+result);
                    Message msg = Message.obtain();
                    //给Message中的obj属性赋值
                    msg.obj = result;
                    //发送消息给主线程
                    handler.sendMessage(msg);
                }
            }).start();




        }
        return false;
    }*/

}
