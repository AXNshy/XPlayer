package com.luffy.smartplay.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.ui.adapter.MusicListAdapter
import com.luffy.smartplay.Config
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.R
import com.luffy.smartplay.databinding.ListFragmentBinding
import com.luffy.smartplay.ui.base.BaseFragment
import com.luffy.smartplay.ui.viewmodel.AlbumFragmentViewModel
import com.luffy.smartplay.ui.viewmodel.ArtWorkViewModel
import java.util.ArrayList

/**
 * Created by axnshy on 16/5/21.
 */
class AlbunFragment : BaseFragment<ListFragmentBinding, AlbumFragmentViewModel>(){
    private var mList: ArrayList<MusicData>? = null
    private var mAdapter: MusicListAdapter? = null
    private var mListView: ListView? = null
    private var ListsList: List<AlbumData>? = null
    private var listPositon = 0
    var mListener: OnItemClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listPositon = arguments?.getInt(Config.Companion.LIST)?:-1
    }

    override fun onResume() {
        super.onResume()
        initView()
        initList()
    }

    private fun initList() {
        // mList=new ArrayList<>();
        Log.e("TAG", "Service   -------------------------- ")

//        mAdapter = MyAdapter(view!!.context, activity, mList)
//        println("mAdapter$mAdapter")
//        mListView!!.adapter = mAdapter
    }

    private fun initView() {
//        if (ListsList!![listPositon].getBackgroundPath() != null) {
//            viewBinding.ivListAvator.setImageDrawable((ListsList!![listPositon].getBackgroundPath()))
//        } else {
            viewBinding.ivListAvator.setImageResource(R.drawable.h1)
//        }
//        viewBinding.tvListCount.setText(ListsList!![listPositon].getListCount().toString() + "")
//        viewBinding.tvListName.setText(ListsList!![listPositon].getListName())
//        viewBinding.tvListCreatorName.setText(ListsList!![listPositon].getUserId().toString() + "")
    }

//    override fun onItemClick(parent: AdapterView<*>?, mview: View, position: Int, id: Long) {
//        //判断Service是否存在
//        if (!Service.isMyServiceRunning(view!!.context, PlayerService::class.java)) {
//            val serviceintent = Intent(view!!.context, PlayerService::class.java)
//            view!!.context.startService(serviceintent)
//            Log.e("TAGS", "MediaPlayerService does not existed")
//        }
//        mService.play(mList!![position])
//        //mListener.updateToolbar(mService.getMyList().get(position).musicName);
//        val intent = Intent(view!!.context, MusicPlayingActivity::class.java)
//        startActivity(intent)
//    }

    /*
    * 接口回调,在父Activity中实现该方法,在fragment中想要回调的地方调用mLister的方法
    * */
    interface OnItemClickListener {
        fun updateToolbar(string: String?)
    }

    override fun bindViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ListFragmentBinding {
        return ListFragmentBinding.inflate(inflater,container,false)
    }


    override val viewModel: AlbumFragmentViewModel by lazy{ViewModelProvider(this)[AlbumFragmentViewModel::class.java]}
}