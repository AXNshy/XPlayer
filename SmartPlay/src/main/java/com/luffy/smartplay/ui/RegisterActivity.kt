package com.luffy.smartplay.ui

import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.luffy.smartplay.Config
import com.luffy.smartplay.User

/**
 * Created by axnshy on 16/5/28.
 */
class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private var username: EditText? = null
    private var password: EditText? = null
    private var register: Button? = null
    private var userName: String? = null
    private var Password: String? = null
    private var registerUrl: String? = null
    protected fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDrawerBack))
        initView()
        initListener()
    }

    private fun initView() {
        username = findViewById(R.id.et_input_username) as EditText?
        password = findViewById(R.id.et_input_password) as EditText?
        register = findViewById(R.id.btn_register) as Button?
    }

    private fun initListener() {
        register!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.btn_register -> {
                userName = username.getText().toString()
                Password = password.getText().toString()
                registerUser(userName, Password)
            }
        }
    }

    private fun registerUser(UserName: String?, password: String?) {
        registerUrl =
            Config.Companion.WEB_SERVER_REGISTER + "userName=" + UserName + "&password=" + password
        if (HttpUtils.isServerConnection(this)) {
            Thread {
                val result: String = HttpUtils.doGet(registerUrl)
                println("result:      $result")
                val msg = Message.obtain()
                //给Message中的obj属性赋值
                msg.obj = result
                //发送消息给主线程
                handler.sendMessage(msg)
            }.start()
        }
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val result = msg.obj as String
            if (result == "ok") {
                Toast.makeText(this@RegisterActivity, "注册成功", Toast.LENGTH_SHORT).show()
                println("register succesful")
                MySharedPre.updateCurrentUser(this@RegisterActivity, userName, Password)
                User.Companion.mUser = User(userName, Password)
                val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@RegisterActivity, "注册失败", Toast.LENGTH_SHORT).show()
                username.setText("")
                password.setText("")
            }
        }
    }
}