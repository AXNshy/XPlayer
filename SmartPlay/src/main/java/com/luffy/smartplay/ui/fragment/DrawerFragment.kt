package com.luffy.smartplay.ui.fragment

import android.os.Bundle
import com.luffy.smartplay.R
import android.view.LayoutInflater
import android.view.ViewGroup
import com.luffy.smartplay.ui.adapter.DrawerListAdapter
import com.luffy.smartplay.MySharedPre
import android.content.ComponentName
import android.content.Intent
import com.luffy.smartplay.ui.LoginActivity
import com.luffy.smartplay.ui.UserInfoShowActivity
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.User
import com.luffy.smartplay.databinding.HomeDrawerBinding
import com.luffy.smartplay.ui.base.BaseFragment
import com.luffy.smartplay.ui.viewmodel.HomeDrawerViewModel
import java.util.ArrayList

/**
 * Created by axnshy on 16/4/18.
 */
class DrawerFragment : BaseFragment<HomeDrawerBinding,HomeDrawerViewModel>(), AdapterView.OnItemClickListener {
    private val mDrawerListView: ListView? = null
    private val mDrawerImg: ImageView? = null
    private val view_username: TextView? = null
    private var mDrawerList: ArrayList<String>? = null
    private var mDrawerListAdapter: ListAdapter? = null
    private val mDrawerTop: RelativeLayout? = null
    private var mView: View? = null
    var user:User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDrawer()
        initList()
        setView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return HomeDrawerBinding.inflate(inflater,container,false).root
    }

    private fun initDrawer() {
        if (user != null) {
            view_username!!.setText(user?.username)
        } else {
            view_username!!.text = "当前无用户登录"
            mDrawerImg!!.setImageResource(R.drawable.music)
        }
    }

    private fun initList() {
        mDrawerList = ArrayList()
        mDrawerList!!.add(0, "我的资料")
        mDrawerList!!.add(1, "注销帐号")
        mDrawerList!!.add(2, "出退")
        mDrawerListAdapter = DrawerListAdapter(mView!!.context, mDrawerList!!)
        //System.out.println(mDrawerListView);
        mDrawerListView!!.adapter = mDrawerListAdapter
        mDrawerListView.onItemClickListener = this
    }

    fun setView() {
//        mDrawerTop.setBackgroundColor(Color.parseColor("#4374f5"));
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        when (position) {
            0 -> {
                val intent = Intent()
                intent.component = ComponentName(view.context, UserInfoShowActivity::class.java)
                startActivity(intent)
            }
            1 -> {
                MySharedPre.unRegisterUser(view.context)
                val intent = Intent(view.context, LoginActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                System.exit(0)
            }
        }
    }

    override val viewBinding: HomeDrawerBinding by lazy { HomeDrawerBinding.inflate(layoutInflater,) }
    override val viewModel: HomeDrawerViewModel by lazy { ViewModelProvider(requireActivity())[HomeDrawerViewModel::class.java] }
}