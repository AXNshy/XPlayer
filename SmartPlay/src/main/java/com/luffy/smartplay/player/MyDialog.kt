package com.luffy.smartplay.player

import com.luffy.smartplay.R
import android.app.Dialog
import android.content.*
import android.view.*
import android.widget.*
import java.util.ArrayList

class MyDialog : Dialog, AdapterView.OnItemClickListener {
    private var mListView: ListView? = null
    private var mAdapter: ArrayAdapter<*>? = null
    private var mList: ArrayList<String?>? = null
    private val okBtn: Button? = null
    private val cancleBtn: Button? = null
    private var buttonNum = 2

    //接口对象
    private val mOnOKBtnClickListemer: OnBtnClickListemer? = null

    constructor(context: Context?, buttonNum: Int) : super(context!!, R.style.dialog) {
        this.buttonNum = buttonNum
        val window = this.window
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setContentView(R.layout.my_dailog_layout)
        initView(window)
    }

    //构造函数
    constructor(context: Context?) : super(context!!, R.style.dialog) {
        val window = this.window
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setContentView(R.layout.my_dailog_layout)
        initView(window)
    }

    private fun initView(window: Window?) {
        //  okBtn = (Button) window.findViewById(R.id.ok_btn);
        // cancleBtn = (Button) window.findViewById(R.id.cancle_btn);
        if (buttonNum == 1) {
            cancleBtn!!.visibility = View.GONE
        }
        mListView = window!!.findViewById<View>(R.id.id_dialog_list) as ListView
        mListView!!.onItemClickListener = this
        mList = ArrayList()
        mList!!.add(0, "我的收藏")
        mList!!.add(1, "东方幻想")
        mList!!.add(2, "动漫收藏")
//        mAdapter = ArrayAdapter<Any?>(window.context, android.R.layout.simple_list_item_1, mList!!)
        mListView!!.adapter = mAdapter
    }

    fun showDiglog() {
        show()
    }

    fun setBtnText(leftText: String?, rightText: String?) {
        okBtn!!.text = leftText
        cancleBtn!!.text = rightText
    }

    fun setBtnText(leftText: String?) {
        okBtn!!.text = leftText
    }

    fun setBtnClick(onBtnClickListemer: OnBtnClickListemer) {
        okBtn!!.setOnClickListener {
            onBtnClickListemer.OnOKBtnClick() //接口对象的方法，需要在实例化MyDialog是实现该回调方法
        }
        cancleBtn!!.setOnClickListener {
            dismiss()
            onBtnClickListemer.OnCancleBtnClick()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {}
}