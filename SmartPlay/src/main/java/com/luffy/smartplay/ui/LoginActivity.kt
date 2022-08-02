package com.luffy.smartplay.ui

import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import com.luffy.smartplay.Config
import com.luffy.smartplay.User

/**
 * Created by axnshy on 16/5/25.
 */
class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var et_username: EditText? = null
    private var et_password: EditText? = null
    private var btn_login: Button? = null
    private var registerTx: TextView? = null
    private var username: String? = null
    private var password: String? = null
    private var context: Context? = null
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val loginState = msg.arg1
            println("msg.arg1     :     " + msg.arg1)
            //登录成功
            if (loginState == 1) {
                User.Companion.mUser = msg.obj as User
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                Toast.makeText(this@LoginActivity, "登录成功!", Toast.LENGTH_SHORT).show()
                MySharedPre.updateCurrentUser(
                    context,
                    User.Companion.mUser!!.getUsername(),
                    User.Companion.mUser!!.getPassword()
                )
                startActivity(intent)
                finish()
            }
            //登录失败
            if (loginState == 0) {
                Toast.makeText(this@LoginActivity, "登录失败,用户名或密码错误", Toast.LENGTH_SHORT).show()
                et_password.setText("")
            }
        }
    }

    protected fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDrawerBack))
        initView()
        initEvent()
        context = this
    }

    private fun initView() {
        et_username = findViewById(R.id.et_input_username) as EditText?
        et_password = findViewById(R.id.et_input_password) as EditText?
        btn_login = findViewById(R.id.btn_login) as Button?
        registerTx = findViewById(R.id.tv_register) as TextView?
    }

    private fun initEvent() {
        btn_login!!.setOnClickListener(this)
        registerTx.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.btn_login -> {
                username = et_username.getText().toString().trim { it <= ' ' }
                password = et_password.getText().toString()
                login(this, username, password)
            }
            R.id.tv_register -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun login(context: Context, username: String?, password: String?): Boolean {
        val url: String =
            Config.Companion.WEB_SERVER_LOGIN + "username=" + username + "&password=" + password
        if (HttpUtils.isServerConnection(context)) {
            Thread {
                val msg = Message.obtain()
                val result: String = HttpUtils.doGet(url)
                val loginState: String = JsonUtils.Companion.getLoginInfoFromJson(result)
                println("loginState     :     $loginState")
                if (loginState == "successful") {
                    msg.arg1 = 1
                    msg.obj = JsonUtils.Companion.parseJsonToUser_Basic(result)
                } else {
                    msg.arg1 = 0
                }
                //发送消息给主线程
                handler.sendMessage(msg)
            }.start()
        }
        return false
    }
}