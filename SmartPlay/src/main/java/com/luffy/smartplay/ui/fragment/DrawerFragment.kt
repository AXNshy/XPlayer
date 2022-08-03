package com.luffy.smartplay.ui.fragment

import android.os.Bundle
import com.luffy.smartplay.R
import android.view.LayoutInflater
import android.view.ViewGroup
import com.luffy.smartplay.ui.adapter.DrawerListAdapter
import com.luffy.smartplay.AppSettings
import android.content.ComponentName
import android.content.Intent
import com.luffy.smartplay.ui.LoginActivity
import com.luffy.smartplay.ui.UserInfoShowActivity
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.luffy.smartplay.databinding.HomeBinding
import com.luffy.smartplay.db.bean.User
import com.luffy.smartplay.databinding.HomeDrawerBinding
import com.luffy.smartplay.ui.base.BaseFragment
import com.luffy.smartplay.ui.viewmodel.HomeDrawerViewModel
import com.luffy.smartplay.ui.viewmodel.HomeFragmentViewModel
import java.util.ArrayList

/**
 * Created by axnshy on 16/4/18.
 */
class DrawerFragment : BaseFragment<HomeDrawerBinding,HomeDrawerViewModel>(), AdapterView.OnItemClickListener {
    private val mDrawerListView: RecyclerView? = null
    private val mDrawerImg: ImageView? = null
    private val view_username: TextView? = null
    private var mDrawerList: MutableList<String>? = null
    private var mDrawerListAdapter: DrawerListAdapter? = null
    private val mDrawerTop: RelativeLayout? = null
    private var mView: View? = null
    var user: User? = null

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
    ): View {
        viewModel = ViewModelProvider(this)[HomeDrawerViewModel::class.java]
        viewBinding = HomeDrawerBinding.inflate(layoutInflater, container, false)
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
        mDrawerListAdapter = DrawerListAdapter(mDrawerList!!)
        //System.out.println(mDrawerListView);
        mDrawerListView!!.adapter = mDrawerListAdapter

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
                AppSettings.unRegisterUser()
                val intent = Intent(view.context, LoginActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                System.exit(0)
            }
        }
    }
}