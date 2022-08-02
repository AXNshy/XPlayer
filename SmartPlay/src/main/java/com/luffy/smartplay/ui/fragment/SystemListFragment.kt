package com.luffy.smartplay.ui.fragment

import android.app.Fragment
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ListView
import com.luffy.smartplay.player.Service
import org.xutils.view.annotation.ContentView
import java.util.ArrayList

/**
 * Created by axnshy on 16/8/12.
 */
@ContentView(R.layout.custom_list_fragment)
class SystemListFragment : Fragment(), AdapterView.OnItemClickListener {
    private var activity: MusicListActivity? = null
    private var view: View? = null
    private var mList: ArrayList<MusicInfo>? = null
    private var mAdapter: MyAdapter? = null
    private var mListView: ListView? = null
    private var mService: PlayerService? = null
    private var ListsList: List<ListsInfo>? = null
    private val listId = 0
    var mListener: OnItemClickListener? = null
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    mList = msg.obj as ArrayList<MusicInfo>
                    mAdapter = MyAdapter(view!!.context, activity, mList)
                    println("mAdapter$mAdapter")
                    mListView!!.adapter = mAdapter
                }
            }
        }
    }

    // 定义ServiceConnection
    private val conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // 通过定义的Binder来获取Service实例来供使用
            mService = (service as MyBinder).getService()
            Log.w(PlayerService.Companion.LOG_TAG, "Activity onServiceConnected")
            Log.w("TAG", "Service   -------------------------   $mService")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mService = null
            // 当Service被意外销毁时
            Log.w(PlayerService.Companion.LOG_TAG, "Activity onServiceDisconnected")
        }
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle
    ): View? {
        view = inflater.inflate(R.layout.custom_list_fragment, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        val bundle: Bundle = arguments
        ListsList = bundle.getParcelableArrayList("ListsList")
        initView()
        initList()
        return view
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initList() {
        // mList=new ArrayList<>();
        Log.e("TAG", "Service   --------------------------    $mService")
        Thread {
            val msg = Message.obtain()
            msg.what = 0
            msg.obj = MusicInfoDao.Companion.getAllMusic(view!!.context)
            handler.sendMessage(msg)
        }.start()
    }

    override fun onStart() {
        super.onStart()
        // bindService
        val intent = Intent(view!!.context, PlayerService::class.java)
        view!!.context.bindService(intent, conn, Context.BIND_AUTO_CREATE)
        Log.w(PlayerService.Companion.LOG_TAG, "Activity bindService")
    }

    override fun onPause() {
        super.onPause()
        // 进行unbind
        view!!.context.unbindService(conn)
        Log.w(PlayerService.Companion.LOG_TAG, "Activity unbindService")
    }

    private fun initView() {
        mListView = view!!.findViewById<View>(R.id.lv_fragment_musicList) as ListView
        mListView!!.setOnItemClickListener(this)
    }

    override fun onItemClick(parent: AdapterView<*>?, mview: View, position: Int, id: Long) {
        //判断Service是否存在
        if (!Service.isMyServiceRunning(view!!.context, PlayerService::class.java)) {
            val serviceintent = Intent(view!!.context, PlayerService::class.java)
            view!!.context.startService(serviceintent)
            Log.e("TAGS", "MediaPlayerService does not existed")
        }
        mService.setPlayerList(mList)
        mService.play(mList!![position])
        //mListener.updateToolbar(mService.getMyList().get(position).musicName);
        val intent = Intent(view!!.context, MusicPlayingActivity::class.java)
        startActivity(intent)
    }

    /*
    * 接口回调,在父Activity中实现该方法,在fragment中想要回调的地方调用mLister的方法
    * */
    interface OnItemClickListener {
        fun updateToolbar(string: String?)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (mListener == null) mListener = activity
        this.activity = activity as MusicListActivity
    }
}