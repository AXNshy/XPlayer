package com.luffy.smartplay.utils

import com.luffy.smartplay.state.AccountState
import com.google.gson.Gson
import com.luffy.smartplay.User

/**
 * Created by axnshy on 2016/3/14.
 */
class JsonUtils {
    var login_info: String? = null

    companion object {
        /*
    * 从json数据中获取登录信息
    * */
        fun getLoginInfoFromJson(jsonString: String?): String? {
            val loginInfo = Gson().fromJson(jsonString, AccountState::class.java)
            val jsonUtils = JsonUtils()
            jsonUtils.login_info = loginInfo.login_state
            return jsonUtils.login_info
        }

        /**
         * 把Json数据转化成一个User_Basic类对象
         */
        fun parseJsonToUser_Basic(jsonString: String?): User? {
            return if (jsonString == null) {
                null
            } else Gson().fromJson(
                jsonString,
                AccountState::class.java
            ).user_basic
        }
    }
}