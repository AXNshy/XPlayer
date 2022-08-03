package com.luffy.smartplay.ui

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.databinding.PersoninfoBinding
import com.luffy.smartplay.ui.base.BaseActivity
import com.luffy.smartplay.ui.viewmodel.UserInfoViewModel

/**
 * Created by axnshy on 16/8/5.
 */
class UserInfoShowActivity : BaseActivity<PersoninfoBinding,UserInfoViewModel>() {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUI()
    }

    private fun updateUI() {
//        if (User.Companion.mUser != null) {
//            nickNameTx.setText(User.Companion.mUser.getNickName())
//            genderTx.setText(User.Companion.mUser.getGender().toString() + "")
//            ageTx.setText("22")
//            birthdayTx.setText(User.Companion.mUser.getBirthday().toString() + "")
//            addressTx.setText(User.Companion.mUser.getAddress())
//            emailTx.setText(User.Companion.mUser.getEmail())
//            qqTx.setText(User.Companion.mUser.getQq())
//            telephoneTx.setText(User.Companion.mUser.getTelephone())
//        }
    }
    override val viewModel: UserInfoViewModel by lazy { ViewModelProvider(this)[UserInfoViewModel::class.java] }

    override fun bindViewBinding(): PersoninfoBinding {
        return PersoninfoBinding.inflate(layoutInflater)
    }
}