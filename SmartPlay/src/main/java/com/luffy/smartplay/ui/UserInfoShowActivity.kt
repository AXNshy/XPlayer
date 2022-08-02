package com.luffy.smartplay.ui

import android.view.View
import android.widget.ImageView
import com.luffy.smartplay.User
import com.luffy.smartplay.ui.base.BaseActivity

/**
 * Created by axnshy on 16/8/5.
 */
@ContentView(R.layout.personinfo)
class UserInfoShowActivity : BaseActivity() {
    private val avatar: CircleImageView? = null

    @ViewInject(R.id.iv_userInfo_return)
    private val returnImg: ImageView? = null

    @ViewInject(R.id.tv_personInfo_edit)
    private val editTx: TextView? = null

    @ViewInject(R.id.tv_personInfo_nickName)
    private val nickNameTx: TextView? = null

    @ViewInject(R.id.tv_personInfo_gender)
    private val genderTx: TextView? = null

    @ViewInject(R.id.tv_personInfo_age)
    private val ageTx: TextView? = null

    @ViewInject(R.id.tv_personInfo_birthday)
    private val birthdayTx: TextView? = null

    @ViewInject(R.id.tv_personInfo_address)
    private val addressTx: TextView? = null

    @ViewInject(R.id.tv_personInfo_email)
    private val emailTx: TextView? = null

    @ViewInject(R.id.tv_personInfo_QQ)
    private val qqTx: TextView? = null

    @ViewInject(R.id.tv_personInfo_telephone)
    private val telephoneTx: TextView? = null
    @Event([R.id.iv_setAvatar, R.id.tv_personInfo_edit, R.id.iv_userInfo_return])
    protected fun initOnClickListener(v: View) {
        when (v.id) {
            R.id.iv_userInfo_return -> onBackPressed()
            R.id.tv_personInfo_edit ->
                var  intent
                : Intent
                ?
                = Intent()
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUI()
    }

    private fun updateUI() {
        if (User.Companion.mUser != null) {
            nickNameTx.setText(User.Companion.mUser.getNickName())
            genderTx.setText(User.Companion.mUser.getGender().toString() + "")
            ageTx.setText("22")
            birthdayTx.setText(User.Companion.mUser.getBirthday().toString() + "")
            addressTx.setText(User.Companion.mUser.getAddress())
            emailTx.setText(User.Companion.mUser.getEmail())
            qqTx.setText(User.Companion.mUser.getQq())
            telephoneTx.setText(User.Companion.mUser.getTelephone())
        }
    }
}